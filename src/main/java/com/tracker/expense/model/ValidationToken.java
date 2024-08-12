package com.tracker.expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ValidationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    private Instant expiryDate;
    @OneToOne(fetch = FetchType.LAZY , orphanRemoval = true ,cascade = CascadeType.PERSIST)
    private Person person;
}
