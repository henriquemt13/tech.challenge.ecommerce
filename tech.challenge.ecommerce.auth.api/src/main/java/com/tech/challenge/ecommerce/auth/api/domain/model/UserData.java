package com.tech.challenge.ecommerce.auth.api.domain.model;

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
    @OneToOne
    @JoinColumn(name = "id_user_auth")
    private UserAuth userAuth;
    @Column(name = "email")
    private String email;
}
