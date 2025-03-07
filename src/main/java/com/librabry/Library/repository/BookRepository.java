package com.librabry.Library.repository;

import com.librabry.Library.model.Book;
import com.librabry.Library.model.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findByTitle(String title);
    List<Book> findByTitleContaining(String title);
    List<Book> findByBookType(BookType bookType);
    List<Book> findByBookNo(String bookNo);
}
