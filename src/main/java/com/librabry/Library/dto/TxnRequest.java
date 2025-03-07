package com.librabry.Library.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TxnRequest {
    @NotBlank(message = "Phone Number Should not be Blank")
    private String phoneNumber;
    @NotBlank(message = "Book Number Should not be Blank")
    private String bookNo;

}
