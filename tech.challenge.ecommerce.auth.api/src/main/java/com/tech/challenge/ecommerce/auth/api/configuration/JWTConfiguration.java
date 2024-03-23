package com.tech.challenge.ecommerce.auth.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.ecommerce.auth.api.domain.dto.JwtAuthFilterDTO;
import com.tech.challenge.ecommerce.auth.api.filter.JWTAuthFilter;
import com.tech.challenge.ecommerce.auth.api.filter.JWTValidationFilter;
import com.tech.challenge.ecommerce.auth.api.filter.RestAuthenticationEntryPoint;
import com.tech.challenge.ecommerce.auth.api.properties.JWTProperties;
import com.tech.challenge.ecommerce.auth.api.service.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JWTConfiguration extends AbstractHttpConfigurer<JWTConfiguration, HttpSecurity> {

    private final JWTProperties jwtProperties;
    private final UserDataService userDataService;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        final AuthenticationManager authenticationManager = http.getSharedObject(
                AuthenticationManager.class);

        JwtAuthFilterDTO jwtAuthFilterDTO = JwtAuthFilterDTO.builder()
                .tokenPass(jwtProperties.getToken())
                .userDataService(userDataService)
                .authenticationManager(authenticationManager)
                .build();

        http.securityMatcher("/**")
                .addFilter(new JWTAuthFilter(jwtAuthFilterDTO, objectMapper))
                .addFilter(new JWTValidationFilter(authenticationManager))
                .httpBasic(httpSecurityHttpBasicConfigurer ->
                        httpSecurityHttpBasicConfigurer
                                .authenticationEntryPoint(new RestAuthenticationEntryPoint(objectMapper)));
    }
}
