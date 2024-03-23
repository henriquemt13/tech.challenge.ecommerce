package com.tech.challenge.ecommerce.auth.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.ecommerce.auth.api.domain.dto.JwtAuthFilterDTO;
import com.tech.challenge.ecommerce.auth.api.domain.dto.LoginRequestDTO;
import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuthDetail;
import com.tech.challenge.ecommerce.auth.api.exceptions.AuthenticationFailedException;
import com.tech.challenge.ecommerce.auth.api.service.UserDataService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public static String TOKEN_PASS;
    private final UserDataService userDataService;

    private static final Integer TOKEN_EXPIRATION = 7200000;

    private final ObjectMapper objectMapper;

    public JWTAuthFilter(JwtAuthFilterDTO dto, ObjectMapper objectMapper) {
        this.authenticationManager = dto.getAuthenticationManager();
        this.userDataService = dto.getUserDataService();
        this.objectMapper = objectMapper;
        TOKEN_PASS = dto.getTokenPass();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        try {
            LoginRequestDTO loginRequestDTO = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
            validateLoginRequest(loginRequestDTO);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(), loginRequestDTO.getPassword(), new ArrayList<>()));

        } catch (IOException e) {
            logger.error("Error while creating JWT", e);
            throw new AuthenticationFailedException();
        }
    }

    private void validateLoginRequest(LoginRequestDTO login) {
        String emptyFieldError = "Empty Username or Password";
        if (login == null) {
            throw new AuthenticationFailedException(emptyFieldError);
        }
        if (isCredentialInvalid(login.getPassword()) || isCredentialInvalid(login.getUsername())) {
                throw new AuthenticationFailedException(emptyFieldError);
        }
    }

    private boolean isCredentialInvalid(String credential) {
        return credential == null
                || credential.isBlank()
                || credential.isEmpty();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserAuthDetail userAuthDetail = (UserAuthDetail) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(userAuthDetail.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .withClaim("x-admin-user", isAdminUser(userAuthDetail.getUsername()))
                .sign(Algorithm.HMAC512(TOKEN_PASS));
        response.getWriter().write(token);
        response.getWriter().flush();
    }

    private boolean isAdminUser(String username) {
        var userOpt = userDataService.findByUserAuthUsername(username);
        if (userOpt.isEmpty()) {
            return false;
        }
        return userOpt.get().getUserAuth().getHasAdminPermission();
    }
}
