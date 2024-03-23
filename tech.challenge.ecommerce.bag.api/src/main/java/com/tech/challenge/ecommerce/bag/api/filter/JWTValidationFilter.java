package com.tech.challenge.ecommerce.bag.api.filter;

import com.tech.challenge.ecommerce.bag.api.connector.AuthConnector;
import com.tech.challenge.ecommerce.bag.api.exceptions.BadRequestException;
import com.tech.challenge.ecommerce.bag.api.service.UserDataService;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
@Component
public class JWTValidationFilter extends HttpFilter {

    private final AuthConnector authConnector;
    private final UserDataService userDataService;

    public static final String ATTRIBUTE_PREFIX = "Bearer ";
    public static final String ADMIN_USER = "x-admin-user";


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        var header = request.getHeader(AUTHORIZATION);
        try {
            if (!header.contains(ATTRIBUTE_PREFIX)) {
                throw new BadRequestException("Malformed Token Sent");
            }
            String attribute = header.replace(ATTRIBUTE_PREFIX, "");
            var authentication = authConnector.authentication(attribute);

            if (authentication.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new BadRequestException("Token Expired");
            }
            validateUserToken(request);
            chain.doFilter(request, response);

        } catch (NullPointerException e) {
            response.getWriter().write("Token should be sent");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (BadRequestException e) {
            response.getWriter().write(e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        } catch (FeignException.Unauthorized e) {
            response.getWriter().write("Session Expired");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

    }

    private void validateUserToken(HttpServletRequest request) {
        var userEmail = request.getParameter("email");
        if (userEmail != null) {
            userDataService.findByEmail(userEmail)
                    .orElseThrow(() -> new BadRequestException("Token was true, but the user was not found"));
        }
    }

}
