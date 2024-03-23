package com.tech.challenge.ecommerce.payment.api.domain.dto.response;

import com.tech.challenge.ecommerce.payment.api.domain.model.Item;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PaymentResponseDTO {

    private List<Item> items;
    private BigDecimal totalValue;

}
