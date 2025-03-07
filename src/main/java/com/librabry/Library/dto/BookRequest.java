package com.librabry.Library.dto;

import com.librabry.Library.model.Author;
import com.librabry.Library.model.Book;
import com.librabry.Library.model.BookType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookRequest {
    @NotBlank(message = "Book Number Should not be Blank")
    private String bookNo;
    @NotBlank(message = "Book Title Should not be Blank")
    private String bookTitle;
    @NotBlank(message = "AuthorName Should not be Blank")
    private String authorName;
    @NotBlank(message = "AthorEmail Should not be Blank")
    private String authorEmail;
    @NotNull(message = "Book Type Should not be Blank")
    private BookType bookType;
    @Positive(message = "Security Amount should be Positive")
    private Integer securityAmount;

    public Author toAuthor(){
        return Author.builder().authorEmail(this.authorEmail).authorName(this.authorName).build();
    }
    public Book toBook(){
        return Book.builder().bookNo(this.bookNo).title(this.bookTitle).securityAmount(this.securityAmount).bookType(this.bookType).build();
    }
}
