package com.tech.challenge.ecommerce.payment.api.domain.mapper;

import com.tech.challenge.ecommerce.payment.api.domain.dto.response.ItemResponseDTO;
import com.tech.challenge.ecommerce.payment.api.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface ItemMapper {

    ItemResponseDTO of(Item item);

    List<ItemResponseDTO> of(List<Item> item);
}
