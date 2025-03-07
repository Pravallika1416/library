package com.librabry.Library.service;

import com.librabry.Library.dto.BookRequest;
import com.librabry.Library.model.*;
import com.librabry.Library.repository.AuthorRepository;
import com.librabry.Library.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    public Book addBook(@Valid BookRequest bookRequest) {
        Author authorfromDb=authorRepository.getAuthorByEmail(bookRequest.getAuthorEmail());
        if(authorfromDb==null){
            //object of authorTbale
                authorfromDb=authorRepository.save(bookRequest.toAuthor());
            //save table in authorTable
        }
        Book book=bookRequest.toBook();
        book.setAuthor(authorfromDb);
        return bookRepository.save(book);

    }

    public List<Book> filter(FilterType filterType, Operator operator, String value) {
        switch (filterType){
            case BOOK_TITLE:
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByTitle(value);
                    case LIKE:
                        return bookRepository.findByTitleContaining(value);
                    default:
                        return new ArrayList<>();
                }
            case BOOK_TYPE:
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByBookType(BookType.valueOf(value));
                }
            case BOOK_NO:
                switch (operator){
                    case EQUALS :
                        return bookRepository.findByBookNo(value);
                }
            default:
                return new ArrayList<>();

        }

    }

    public void updatebookData(Book bookfromDB) {
        bookRepository.save(bookfromDB);
    }
}
