package com.example.demo.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private int id;
    @NotBlank
    private String first_name;
    @NotBlank
    private String last_name;
    @Email
    private String email;
    @NotBlank
    private String contact_number;
    private String password;


}
