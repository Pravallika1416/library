package com.librabry.Library.Controller;

import com.librabry.Library.dto.BookRequest;
import com.librabry.Library.model.Book;
import com.librabry.Library.model.BookType;
import com.librabry.Library.model.FilterType;
import com.librabry.Library.model.Operator;
import com.librabry.Library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping("/addBook")
    public Book addBook(@RequestBody @Valid BookRequest bookRequest){
        //validations before the business logic??

        //call the business logic
        Book book=bookService.addBook(bookRequest);

        //return the accurate/required data
        return book;




    }
    @GetMapping("/filterBy")
    public List<Book> filter(@RequestParam("fileBy")FilterType filterType, @RequestParam("operator")Operator operator,
                               @RequestParam("value")String value){
        return bookService.filter(filterType,operator,value);

    }
}
