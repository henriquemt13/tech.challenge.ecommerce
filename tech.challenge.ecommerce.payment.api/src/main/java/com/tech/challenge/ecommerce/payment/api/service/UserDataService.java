package com.tech.challenge.ecommerce.payment.api.service;

import com.tech.challenge.ecommerce.payment.api.domain.model.UserData;
import com.tech.challenge.ecommerce.payment.api.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserDataService {

    private final UserDataRepository repository;

    public Optional<UserData> findByEmail(String email) {
        log.info("Searching for user by email {}", email);
        return repository.findByEmail(email);
    }
}
