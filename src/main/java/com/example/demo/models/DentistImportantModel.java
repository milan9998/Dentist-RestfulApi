package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DentistImportantModel {
    private int user_id;
    private int price;
    private String name_of_repair;
}
