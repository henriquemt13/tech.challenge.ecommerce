package com.tech.challenge.ecommerce.payment.api.domain.mapper;

import com.tech.challenge.ecommerce.payment.api.domain.dto.response.ItemResponseDTO;
import com.tech.challenge.ecommerce.payment.api.domain.model.Item;
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
public class ItemMapperImpl implements ItemMapper {

    @Override
    public ItemResponseDTO of(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemResponseDTO.ItemResponseDTOBuilder itemResponseDTO = ItemResponseDTO.builder();

        itemResponseDTO.id( item.getId() );
        itemResponseDTO.name( item.getName() );
        itemResponseDTO.description( item.getDescription() );
        itemResponseDTO.price( item.getPrice() );

        return itemResponseDTO.build();
    }

    @Override
    public List<ItemResponseDTO> of(List<Item> item) {
        if ( item == null ) {
            return null;
        }

        List<ItemResponseDTO> list = new ArrayList<ItemResponseDTO>( item.size() );
        for ( Item item1 : item ) {
            list.add( of( item1 ) );
        }

        return list;
    }
}
