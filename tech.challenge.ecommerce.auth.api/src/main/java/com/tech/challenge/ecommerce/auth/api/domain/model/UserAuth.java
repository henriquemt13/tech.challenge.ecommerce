package com.tech.challenge.ecommerce.auth.api.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_auth")
@Data
@NoArgsConstructor
public class UserAuth {

    @Id
    @SequenceGenerator(name = "user_auth_seq",
            sequenceName = "user_auth_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_auth_seq")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "username")
    @NotNull(message = "Please enter username")
    private String username;
    @Column(name = "password")
    @NotNull(message = "Please enter password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "has_root_permission")
    private Boolean hasAdminPermission;

    public UserAuth(String username, String password, boolean hasAdminPermission) {
        this.username = username;
        this.password = password;
        this.hasAdminPermission = hasAdminPermission;
    }
}