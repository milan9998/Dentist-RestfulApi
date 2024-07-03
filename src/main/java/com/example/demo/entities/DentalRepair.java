package com.example.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="dental_repairs")
@Data
public class DentalRepair {
    @Id
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
