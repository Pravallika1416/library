package com.librabry.Library.service;

import com.librabry.Library.dto.TxnRequest;
import com.librabry.Library.exception.TxnException;
import com.librabry.Library.model.Book;
import com.librabry.Library.model.Txn;
import com.librabry.Library.model.User;
import com.librabry.Library.repository.TxnRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestTxnService {
    @InjectMocks
    private TxnService txnService;
    //Call all dependent Classes
    @Mock
    private UserService userService;
    @Mock
    private BookService bookService;
    @Mock
    private TxnRepository txnRepository; //Mocks are nothing bt empty objects.


    @Before
    public void setup(){
//    txnService.setValidupto(14);
//    txnService.setFineperDay(2);

       // txnService=new TxnService();
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(txnService,"validupto",14);
        ReflectionTestUtils.setField(txnService,"fineperDay",2);


    }

    @Test
    public void testCalclateFine() throws ParseException {
        Date date=new SimpleDateFormat("yyyy-MM-DD").parse("2025-02-06");
        Txn txn=Txn.builder().createdOn(date).build();
        int calculatedAmount=txnService.calclatefine(txn,100);
        Assert.assertEquals(8,calculatedAmount);

    }
    @Test(expected = TxnException.class)
    public void testGetUserFromDB() throws TxnException {
        TxnRequest txnRequest=TxnRequest.builder().build();
        when(userService.getStudentByPhoneNumber(any())).thenReturn(null);

        txnService.getUserFromDB(txnRequest);

    }
   @Test
    public void testGetUserFromDBWhenNoException() throws TxnException {
        TxnRequest txnRequest=TxnRequest.builder().build();
        User user= User.builder().id(1).build();
        when(userService.getStudentByPhoneNumber(any())).thenReturn(user);

        User output=txnService.getUserFromDB(txnRequest);
        Assert.assertEquals(output.getId(),user.getId());

    }
    @Test
    public void testReturnBook() throws TxnException, ParseException {
        TxnRequest txnRequest=TxnRequest.builder().build();

        User user= User.builder().id(1).build();
        when(userService.getStudentByPhoneNumber(any())).thenReturn(user);
        List<Book> list=new ArrayList<>();
        list.add(Book.builder().id(1).bookNo("1").user(user).securityAmount(100).build());
        when(bookService.filter(any(),any(),any())).thenReturn(list);
        Date date=new SimpleDateFormat("yyyy-MM-DD").parse("2025-02-06");
        Txn txn=Txn.builder().id(1).user(user).book(list.get(0)).createdOn(date).build();
        when(txnRepository.findByUserPhoneNumberAndBookBookNoAndTxnStatus(any(),any(),any())).thenReturn(txn);
        int value=txnService.returnBook(txnRequest);
        Assert.assertEquals(8,value);
    }
}


//Method which basically tests another method
//Mock the response when it is depdent on another class
//Assertion is equal to expected