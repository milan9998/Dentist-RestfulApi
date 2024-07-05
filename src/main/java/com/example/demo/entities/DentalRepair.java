package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name="dental_repairs")
@Table(name="dental_repairs")
@Data
public class DentalRepair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="dentist_id")
    private Integer dentist_id;
    @Column(name="name_of_repair")
    private String name_of_repair;
    @Column(name="price")
    private Integer price;


}
