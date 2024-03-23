package com.tech.challenge.ecommerce.bag.api.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseBagResponseDTO {

    private List<ItemResponseDTO> items;
}
