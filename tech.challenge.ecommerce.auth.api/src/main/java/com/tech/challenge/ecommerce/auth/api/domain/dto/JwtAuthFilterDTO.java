package com.tech.challenge.ecommerce.auth.api.domain.dto;

import com.tech.challenge.ecommerce.auth.api.service.UserDataService;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;

@Data
@Builder
public class JwtAuthFilterDTO {

    private UserDataService userDataService;
    private String tokenPass;
    private AuthenticationManager authenticationManager;
}