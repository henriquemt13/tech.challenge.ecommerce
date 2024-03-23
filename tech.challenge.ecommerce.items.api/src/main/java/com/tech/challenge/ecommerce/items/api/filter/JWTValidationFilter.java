package com.tech.challenge.ecommerce.items.api.filter;

import com.tech.challenge.ecommerce.items.api.connector.AuthConnector;
import com.tech.challenge.ecommerce.items.api.exceptions.BadRequestException;
import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Objects;

@AllArgsConstructor
@Component
public class JWTValidationFilter extends HttpFilter {

    private final AuthConnector authConnector;

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

            if (Objects.equals(Objects.requireNonNull(authentication.getHeaders().get(ADMIN_USER)).get(0),
                    "false")) {
                throw new BadRequestException("User has no permissions");
            }
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

}
