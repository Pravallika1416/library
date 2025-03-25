package com.librabry.Library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String password;

    private String authorities;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
