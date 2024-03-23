package com.tech.challenge.ecommerce.payment.api.domain.mapper;

import com.tech.challenge.ecommerce.payment.api.domain.dto.request.PurchaseBagRequestDTO;
import com.tech.challenge.ecommerce.payment.api.domain.dto.response.PurchaseBagResponseDTO;
import com.tech.challenge.ecommerce.payment.api.domain.model.PurchaseBag;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-23T11:10:04-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PurchaseBagMapperImpl implements PurchaseBagMapper {

    @Override
    public PurchaseBag of(PurchaseBagRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        PurchaseBag purchaseBag = new PurchaseBag();

        purchaseBag.setIdUserData( requestDTO.getIdUserData() );
        purchaseBag.setIdItem( requestDTO.getIdItem() );
        if ( requestDTO.getQuantity() != null ) {
            purchaseBag.setQuantity( Integer.parseInt( requestDTO.getQuantity() ) );
        }

        return purchaseBag;
    }

    @Override
    public PurchaseBagResponseDTO of(PurchaseBag PurchaseBag) {
        if ( PurchaseBag == null ) {
            return null;
        }

        PurchaseBagResponseDTO purchaseBagResponseDTO = new PurchaseBagResponseDTO();

        return purchaseBagResponseDTO;
    }

    @Override
    public List<PurchaseBagResponseDTO> of(List<PurchaseBag> PurchaseBag) {
        if ( PurchaseBag == null ) {
            return null;
        }

        List<PurchaseBagResponseDTO> list = new ArrayList<PurchaseBagResponseDTO>( PurchaseBag.size() );
        for ( PurchaseBag purchaseBag : PurchaseBag ) {
            list.add( of( purchaseBag ) );
        }

        return list;
    }
}
