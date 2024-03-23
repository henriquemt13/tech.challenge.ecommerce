package com.tech.challenge.ecommerce.auth.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    public static final String ATTRIBUTE_PREFIX = "Bearer ";
    public static final String ADMIN_USER = "x-admin-user";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String attribute = request.getHeader(AUTHORIZATION);
        try {
            authenticate(attribute, response);
        } catch (TokenExpiredException e) {
            request.setAttribute("expired", e.getMessage());
        } finally {
            chain.doFilter(request, response);
        }
    }

    public void authenticate(String attribute, HttpServletResponse response) {
        if (attribute == null) {
            return;
        }
        String token = attribute.replace(ATTRIBUTE_PREFIX, "");
        var usernamePasswordAuthenticationToken = getAuthenticationToken(token, response);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token, HttpServletResponse response) {
        var auth = JWT
                .require(Algorithm.HMAC512(JWTAuthFilter.TOKEN_PASS))
                .build()
                .verify(token);
        String user = auth.getSubject();
        Claim hasAdminPermission = auth.getClaim(ADMIN_USER);
        if (user == null) {
            return null;
        }
        if (Boolean.TRUE.equals(hasAdminPermission.asBoolean())) {
            response.setHeader(ADMIN_USER, "true");
            return new UsernamePasswordAuthenticationToken(user, null,
                    List.of(new SimpleGrantedAuthority(ADMIN_USER)));
        }
        response.setHeader(ADMIN_USER, "false");
        return new UsernamePasswordAuthenticationToken(user, null, List.of());
    }
}
