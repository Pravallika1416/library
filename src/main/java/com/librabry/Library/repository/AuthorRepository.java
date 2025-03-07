package com.librabry.Library.repository;

import com.librabry.Library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    // Queries
    //native Query
    @Query(value = "select * from author where author_email =:emailTemp" ,nativeQuery = true)
    Author getAuthorByEmail(String emailTemp);

}
