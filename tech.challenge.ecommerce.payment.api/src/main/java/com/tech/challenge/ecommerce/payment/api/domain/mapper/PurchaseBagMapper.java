package com.tech.challenge.ecommerce.payment.api.domain.mapper;

import com.tech.challenge.ecommerce.payment.api.domain.dto.request.PurchaseBagRequestDTO;
import com.tech.challenge.ecommerce.payment.api.domain.dto.response.PurchaseBagResponseDTO;
import com.tech.challenge.ecommerce.payment.api.domain.model.PurchaseBag;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface PurchaseBagMapper {

    PurchaseBag of(PurchaseBagRequestDTO requestDTO);

    PurchaseBagResponseDTO of(PurchaseBag PurchaseBag);

    List<PurchaseBagResponseDTO> of(List<PurchaseBag> PurchaseBag);
}
