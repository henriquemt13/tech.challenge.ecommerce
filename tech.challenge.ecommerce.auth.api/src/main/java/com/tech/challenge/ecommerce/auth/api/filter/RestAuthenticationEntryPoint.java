package com.tech.challenge.ecommerce.auth.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.challenge.ecommerce.auth.api.domain.dto.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException)
            throws IOException {
        log.error(authenticationException.getLocalizedMessage());
        final String expired = (String) request.getAttribute("expired");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorDTO errorDTO = new ErrorDTO("401", "Authentication Failed");
        if (expired != null) {
            errorDTO.setMessage("Token Expired");
        }
        response.getOutputStream().println(objectMapper.writeValueAsString(errorDTO));
    }
}