package com.tech.challenge.ecommerce.items.api.domain.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ItemResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
