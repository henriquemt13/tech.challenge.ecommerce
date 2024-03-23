package com.tech.challenge.ecommerce.items.api.repository;

import com.tech.challenge.ecommerce.items.api.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
