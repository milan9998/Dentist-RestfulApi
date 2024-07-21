package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name="confirmation_tokens_seq")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentist_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Dentist dentist;

    public ConfirmationToken(Dentist dentist) {
        this.dentist = dentist;
        this.createdDate = new Date();
        this.confirmationToken = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {

    }

}
