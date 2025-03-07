package com.librabry.Library.Controller;

import com.librabry.Library.model.Author;
import com.librabry.Library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/getAuthorData")
    public Author getAuthorData(@RequestParam("author_email") String email){
        return authorService.getAuthorData(email);

    }

}
