package com.tech.challenge.ecommerce.items.api.domain.mapper;

import com.tech.challenge.ecommerce.items.api.domain.dto.request.ItemRequestDTO;
import com.tech.challenge.ecommerce.items.api.domain.dto.response.ItemResponseDTO;
import com.tech.challenge.ecommerce.items.api.domain.model.Item;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-22T22:00:08-0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ItemMapperImpl implements ItemMapper {

    @Override
    public Item of(ItemRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Item item = new Item();

        item.setName( requestDTO.getName() );
        item.setDescription( requestDTO.getDescription() );
        item.setType( requestDTO.getType() );
        item.setPrice( requestDTO.getPrice() );

        return item;
    }

    @Override
    public ItemResponseDTO of(Item item) {
        if ( item == null ) {
            return null;
        }

        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();

        itemResponseDTO.setId( item.getId() );
        itemResponseDTO.setName( item.getName() );
        itemResponseDTO.setDescription( item.getDescription() );
        itemResponseDTO.setPrice( item.getPrice() );
        itemResponseDTO.setCreatedAt( item.getCreatedAt() );
        itemResponseDTO.setUpdatedAt( item.getUpdatedAt() );

        return itemResponseDTO;
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
