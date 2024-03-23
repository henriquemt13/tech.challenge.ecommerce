package com.tech.challenge.ecommerce.payment.api.controller;

import com.tech.challenge.ecommerce.payment.api.domain.dto.response.PaymentResponseDTO;
import com.tech.challenge.ecommerce.payment.api.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/payment")
@AllArgsConstructor
@Validated
public class PaymentController {

    private final PaymentService service;
    @GetMapping
    @ApiResponse(description = "Payment Response", responseCode = "200")
    @Operation(summary = "Find Purchase Bag Resume By User Email", description = """
            # Busca Resumo da Sacola de Compras pelo Email do usuário
            ---
                      
            """)
    public ResponseEntity<PaymentResponseDTO> findPaymentResumeByUserEmail(@PathParam("email") @Valid String email) {

        return ResponseEntity.status(HttpStatus.OK).body(service.getPurchaseBagResume(email));
    }


    @PostMapping
    @ApiResponse(description = "Payment Response", responseCode = "200")
    @Operation(summary = "Pay Purchase Bag By User Email", description = """
            # Paga Sacola de Compras pelo Email do usuário
            ---
                      
            """)
    public ResponseEntity<Void> payPurchaseBag(@PathParam("email") @Valid String email) {
        service.payPurchaseBag(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
