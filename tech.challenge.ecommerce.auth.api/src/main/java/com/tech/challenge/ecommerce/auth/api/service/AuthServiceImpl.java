package com.tech.challenge.ecommerce.auth.api.service;

import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.mapper.UserAuthMapper;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuthDetail;
import com.tech.challenge.ecommerce.auth.api.exceptions.BadRequestException;
import com.tech.challenge.ecommerce.auth.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.auth.api.repository.UserAuthRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl implements UserDetailsService {

    private final UserAuthRepository repository;
    private final UserDataService userDataService;
    private final UserAuthMapper mapper;

    public void createUser(SignupRequestDTO requestDTO) {
        if (isInvalidCredential(requestDTO.getUsername()) && isInvalidCredential(requestDTO.getPassword())) {
            throw new BadRequestException("Invalid Credentials, please make sure to send valid username and " +
                    "password values");
        }
        log.info("Creating new User {}", requestDTO.getEmail());
        var userAuth = save(mapper.of(requestDTO));
        userDataService.save(requestDTO, userAuth);
    }

    private boolean isInvalidCredential(String credential) {
        return credential == null || credential.isEmpty() || credential.isBlank();
    }

    private UserAuth save(UserAuth userAuth) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        userAuth.setPassword(encoder.encode(userAuth.getPassword()));
        return repository.save(userAuth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws NotFoundException {
        log.info("Searching for User {}", username);
        Optional<UserAuth> userAuth = repository.findByUsername(username);
        if (userAuth.isEmpty()) {
            throw new NotFoundException("Username or Password are Invalid");
        }
        return new UserAuthDetail(userAuth);
    }
}