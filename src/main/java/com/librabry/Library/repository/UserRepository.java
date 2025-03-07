package com.librabry.Library.repository;

import com.librabry.Library.model.User;
import com.librabry.Library.model.UserType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select * from user where : q" ,nativeQuery = true)
    List<User> findUserByNativeQuery(@Param("query") String q);

    User findByPhoneNumberAndUserType(String phoneNumber, UserType userType);

}