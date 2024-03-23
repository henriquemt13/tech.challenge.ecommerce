package com.tech.challenge.ecommerce.payment.api.repository;

import com.tech.challenge.ecommerce.payment.api.domain.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findByEmail(String email);
}
