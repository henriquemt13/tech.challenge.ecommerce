package com.tech.challenge.ecommerce.auth.api.controller;

import com.tech.challenge.ecommerce.auth.api.domain.dto.ResponseDTO;
import com.tech.challenge.ecommerce.auth.api.domain.dto.SignupRequestDTO;
import com.tech.challenge.ecommerce.auth.api.service.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/user")
@AllArgsConstructor
@Validated
public class AuthController {

    private final AuthServiceImpl service;

    @PostMapping(path = "/signup")
    public ResponseEntity<ResponseDTO<String>> createUser(@RequestBody @Valid SignupRequestDTO requestDTO) {

        service.createUser(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>("User created"));
    }

    @GetMapping("/auth")
    public ResponseEntity<Void> authenticate() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}