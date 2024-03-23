package com.tech.challenge.ecommerce.auth.api.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDTO {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String code;
    private String message;

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
