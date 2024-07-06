package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="dentists_roles")
public class DentistRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="dentist_id")
    private Integer dentist_id;
    @Column(name="role_id")
    private Integer role_id;




    /*
    id int auto_increment primary key,
    dentist_id int,
    role_id int,
    */
}
