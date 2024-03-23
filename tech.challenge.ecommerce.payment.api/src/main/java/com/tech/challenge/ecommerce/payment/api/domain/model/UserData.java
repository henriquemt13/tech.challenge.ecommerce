package com.tech.challenge.ecommerce.payment.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_data")
@Data
public class UserData {

    @Id
    @SequenceGenerator(name = "user_data_seq",
            sequenceName = "user_data_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_data_seq")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "email")
    private String email;
}