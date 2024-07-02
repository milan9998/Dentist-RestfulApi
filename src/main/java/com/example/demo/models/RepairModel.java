package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepairModel {
    private int id;
    private int user_id;
    private int dentist_id;
    private String name_of_repair;
    private int price;
}
