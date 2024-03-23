package com.tech.challenge.ecommerce.payment.api.connector;

import com.tech.challenge.ecommerce.payment.api.properties.AuthProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${auth.url}", configuration = AuthProperties.class, name = "AuthConnector")
public interface AuthConnector {

    @GetMapping("/v1/user/auth")
    ResponseEntity<Void> authentication(@RequestHeader("Authorization") String token);
}
