package com.company.ordersbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {

    private long id;
    @NotNull(message = "Username must be unique and cannot be null")
    @NotBlank(message = "Username must be unique and cannot be blank")
    @Size(min = 5, max = 50, message = "Username has to have minimum 5 signs")
    private String username;

    @NotNull(message="Password cannot be null")
    @NotBlank(message="Password cannot be blank")
    @Size(min = 5, max = 50, message = "Password has to have minimum 8 signs")
    private String password;
    private String role;
    @Email(message = "Not correct e-mail format")
    private String email;

}
