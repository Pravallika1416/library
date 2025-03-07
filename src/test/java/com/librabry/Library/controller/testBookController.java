package com.librabry.Library.controller;

import com.librabry.Library.Controller.BookController;
import com.librabry.Library.model.Book;
import com.librabry.Library.model.BookType;
import com.librabry.Library.service.BookService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.NotBlank;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.plugins.MockMaker;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ContextConfiguration(classes = {BookController.class})
public class testBookController {
    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;
    private MockMvc mvc;
    @Before
    public void setup(){
       // BookService bookService=new BookService();

        MockitoAnnotations.initMocks(this);
        mvc= MockMvcBuilders.standaloneSetup(bookController).build();
    }
    @Test
    public void testAddBook() throws Exception {



        JSONObject object=new JSONObject();
        object.put("bookNo","235");
        object.put("bookTitle","bookTitle");
        object.put("authorName","authorName");
        object.put("authorEmail","authorEmail");
        object.put("bookType","EDUCATIONAL");
        object.optInt("securityAmount;",100);

        RequestBuilder requestBuilder=post("/book/addBook")
                .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(object));
        mvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());

    }
}
//validations
// call your method as it is called from dispatcher  servlet
