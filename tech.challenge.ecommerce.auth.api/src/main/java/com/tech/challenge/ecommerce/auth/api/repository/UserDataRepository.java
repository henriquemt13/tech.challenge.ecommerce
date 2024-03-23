package com.tech.challenge.ecommerce.auth.api.repository;

import com.tech.challenge.ecommerce.auth.api.domain.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByUserAuth_Username(String username);

    Optional<UserData> findByEmail(String email);
}