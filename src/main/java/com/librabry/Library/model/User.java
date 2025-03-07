package com.librabry.Library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30)
    private String name;

    @Column(unique = true,length = 50)
    private String email;

    @Column(nullable = false,unique = true,length = 15)
    private String phoneNumber;

    private String address;

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "user") // dont include this data inside table but a relation exist
    private List<Book> bookList;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value={"user","book"})
    private List<Txn> txnList;

    @UpdateTimestamp
    private Date updatedOn;

    @Enumerated(value = EnumType.STRING) //Oedinal values get stored......0 or 1 if not specified value
    private UserType userType;

    @Enumerated
    private UserStatus userStatus;

}
