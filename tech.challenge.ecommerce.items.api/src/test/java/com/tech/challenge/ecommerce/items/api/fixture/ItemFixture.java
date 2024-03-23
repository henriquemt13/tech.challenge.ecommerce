package com.tech.challenge.ecommerce.items.api.fixture;

import com.tech.challenge.ecommerce.items.api.domain.model.Item;
import com.tech.challenge.ecommerce.items.api.domain.model.TypeEnum;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class ItemFixture {
    public ItemFixture() {

    }

    public static Item buildItem() {
        return new Item(1L, "TESTE", "TESTE", TypeEnum.CLOTHING, BigDecimal.TEN,
                OffsetDateTime.of(2024, 1, 1, 0,
                0, 0, 0,  ZoneOffset.of("-03:00")),
                OffsetDateTime.of(2024, 1, 1, 0,
                0, 0, 0,  ZoneOffset.of("-03:00")));
    }

    public static List<Item> buildItems() {
        return List.of(buildItem());
    }
}
