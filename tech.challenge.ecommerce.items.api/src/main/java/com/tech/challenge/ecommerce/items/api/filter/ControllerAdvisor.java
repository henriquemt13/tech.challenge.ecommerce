package com.tech.challenge.ecommerce.items.api.filter;

import com.tech.challenge.ecommerce.items.api.domain.dto.ErrorDTO;
import com.tech.challenge.ecommerce.items.api.domain.dto.ResponseDTO;
import com.tech.challenge.ecommerce.items.api.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO> handleBadRequestException(
            BadRequestException ex, WebRequest request) {

        return ResponseEntity.badRequest()
                .body(new ResponseDTO(
                        new ErrorDTO(HttpStatus.BAD_REQUEST.toString(), ex.getMessage())));
    }
}