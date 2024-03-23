package com.tech.challenge.ecommerce.auth.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = false)
public class JWTProperties {

    private String token;
}