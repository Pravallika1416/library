package com.librabry.Library.service;

import com.librabry.Library.model.Author;
import com.librabry.Library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    public Author getAuthorData(String email) {
        return authorRepository.getAuthorByEmail(email);
    }
}
