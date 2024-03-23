package com.tech.challenge.ecommerce.payment.api.domain.dto.request;

import com.tech.challenge.ecommerce.payment.api.domain.model.TypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseBagRequestDTO {

    private Long idUserData;
    @NotNull(message = "Please enter item Id")
    private Long idItem;
    @NotNull(message = "Please enter item description")
    @NotEmpty(message = "Please enter item description")
    private String quantity;
}
