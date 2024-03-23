package com.tech.challenge.ecommerce.items.api.domain.dto.request;

import com.tech.challenge.ecommerce.items.api.domain.model.TypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemRequestDTO {

    @NotNull(message = "Please enter item name")
    @NotEmpty(message = "Please enter item name")
    private String name;
    @NotNull(message = "Please enter item description")
    @NotEmpty(message = "Please enter item description")
    private String description;
    @NotNull(message = "Please enter item type")
    private TypeEnum type;
    @NotNull(message = "Please enter item price")
    private BigDecimal price;
}
