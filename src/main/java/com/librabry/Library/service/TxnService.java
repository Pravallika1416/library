package com.librabry.Library.service;

import com.librabry.Library.dto.TxnRequest;
import com.librabry.Library.exception.TxnException;
import com.librabry.Library.model.*;
import com.librabry.Library.repository.TxnRepository;
import com.librabry.Library.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService {
    @Autowired
    private TxnRepository txnRepository;

//    @Autowired    Its not a standarad practice of using User Repository inside another Services
//    private UserRepository userRepository;
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Value("${book.valid.upto}")

    private int validupto;
    @Value("${book.fine.amont.per.day}")

    private int fineperDay;

    public User getUserFromDB(TxnRequest txnRequest) throws TxnException
    {
        User userfromDB=userService.getStudentByPhoneNumber(txnRequest.getPhoneNumber());
        if(userfromDB==null){
            throw new TxnException("Student Doesn't belong to my Library");
        }
        return userfromDB;
    }
    public Book getBookfromDB(TxnRequest txnRequest) throws TxnException {
        List<Book> books=bookService.filter(FilterType.BOOK_NO, Operator.EQUALS,txnRequest.getBookNo());
        if(books.isEmpty()){
            throw new TxnException("Book Doesn't belong to my Library");
        }
        Book bookfromDB=books.get(0);
        return bookfromDB;
    }
    //Propagation to be used is :
    @Transactional(rollbackOn = {TxnException.class})
    public String createTxn(User userfromDB,Book bookfromDB){
        String txnID= UUID.randomUUID().toString();
        Txn txn=Txn.builder().txnId(txnID).user(userfromDB).
                book(bookfromDB).txnStatus(TxnStatus.ISSUED).build();
        txnRepository.save(txn);
        bookfromDB.setUser(userfromDB);

        bookService.updatebookData(bookfromDB);
        return txnID;
    }
//    @Transactional(rollbackOn = {TxnException.class})/// To maintain ntegrity and to involve compile time exceptions RoollBackON
    public String create(TxnRequest txnRequest) throws TxnException {
        //stdent is valid or not
       // User user=
       User userfromDB=getUserFromDB(txnRequest);
       Book bookfromDB=getBookfromDB(txnRequest);
           // System.out.println(bookfromDB.toString());
            if(bookfromDB.getUser()!=null){
                throw new TxnException("Book Already Assigned");
         }
return createTxn(userfromDB,bookfromDB);
    }
    @Transactional(rollbackOn = {TxnException.class})
    public int returnBook(TxnRequest txnRequest) throws TxnException {
        User userfromDB=getUserFromDB(txnRequest);
        Book bookfromDB=getBookfromDB(txnRequest);
        if(bookfromDB.getUser()!=userfromDB){
            throw new TxnException("This is Not the User to which Book is Assigned");
        }
        Txn txn= txnRepository.findByUserPhoneNumberAndBookBookNoAndTxnStatus(txnRequest.getPhoneNumber(),txnRequest.getBookNo(),TxnStatus.ISSUED);
    int fine=calclatefine(txn,bookfromDB.getSecurityAmount());
    if(fine==bookfromDB.getSecurityAmount()){
        txn.setTxnStatus(TxnStatus.RETURNED);
    }
    else{
        txn.setTxnStatus(TxnStatus.FINE);
    }
    txn.setFineAmount(fine);
    bookfromDB.setUser(null);
    bookService.updatebookData(bookfromDB);
    return fine;
    }

    public int calclatefine(Txn txn,int securityAmount) {
        long issueDate=txn.getCreatedOn().getTime();
        long returnDate=System.currentTimeMillis();
        long timeDiff=returnDate-issueDate;
        int daysPassed= (int)TimeUnit.DAYS.convert(timeDiff,TimeUnit.MILLISECONDS);
        if(daysPassed>Integer.valueOf(validupto)){
            int fineAmount=(daysPassed-Integer.valueOf(validupto))*Integer.valueOf(fineperDay);
            return securityAmount-fineAmount;
        }
        return securityAmount;
    }

}
