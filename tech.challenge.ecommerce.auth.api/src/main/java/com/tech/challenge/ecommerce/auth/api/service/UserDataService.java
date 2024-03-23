package com.tech.challenge.ecommerce.auth.api.service;

import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.mapper.UserDataMapper;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserData;
import com.tech.challenge.ecommerce.auth.api.exceptions.BadRequestException;
import com.tech.challenge.ecommerce.auth.api.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserDataService {

    private final UserDataRepository repository;
    private final UserDataMapper mapper;

    public UserData save(SignupRequestDTO signupRequestDTO, UserAuth userAuth) {
        if (findByEmail(signupRequestDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email Already Taken");
        }
        log.info("Saving new User Data for sent email {}", signupRequestDTO.getEmail());
        return repository.save(mapper.of(signupRequestDTO, userAuth));
    }

    private Optional<UserData> findByEmail(String email) {
        log.info("Searching for user by email {}", email);
        return repository.findByEmail(email);
    }

    public Optional<UserData> findByUserAuthUsername(String username) {
        log.info("Searching for user auth {}", username);
        return repository.findByUserAuth_Username(username);
    }

}
