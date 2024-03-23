package com.tech.challenge.ecommerce.bag.api.filter;

import com.tech.challenge.ecommerce.bag.api.exceptions.BadRequestException;
import com.tech.challenge.ecommerce.bag.api.domain.dto.ErrorDTO;
import com.tech.challenge.ecommerce.bag.api.domain.dto.ResponseDTO;
import com.tech.challenge.ecommerce.bag.api.exceptions.NotFoundException;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFoundException(
            NotFoundException ex, WebRequest request) {

        return ResponseEntity.badRequest()
                .body(new ResponseDTO(
                        new ErrorDTO(HttpStatus.NOT_FOUND.toString(), ex.getMessage())));
    }
}