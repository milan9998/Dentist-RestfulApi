package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDentistModel {
    private String email;
    private String password;

}
