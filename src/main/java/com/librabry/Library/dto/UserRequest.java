package com.librabry.Library.dto;

import com.librabry.Library.model.User;
import com.librabry.Library.model.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequest {
    private String userName;
    @NotBlank(message = "User Phone Number Should Not be Null")
    private String phoneNo;
    private String email;
    private String address;
    @NotBlank(message = "Pwd Number Should Not be Null")
    private String password;
    public User toUser(){
        return User.builder().
                name(this.userName).
                email(this.email).
                phoneNumber(this.phoneNo).
                address(this.address).
                password(this.password).
                userStatus(UserStatus.ACTIVE).

                build();
    }

}
