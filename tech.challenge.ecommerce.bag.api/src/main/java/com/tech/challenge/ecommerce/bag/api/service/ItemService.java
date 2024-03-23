package com.tech.challenge.ecommerce.bag.api.service;

import com.tech.challenge.ecommerce.bag.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.bag.api.repository.ItemRepository;
import com.tech.challenge.ecommerce.bag.api.domain.model.Item;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ItemService {

    private final ItemRepository repository;

    public Optional<Item> findById(Long id) {
        log.info("Searching Item by Id {}", id);
        return repository.findById(id);
    }

    public Item validateAndFindById(Long id) {
        var itemOpt = findById(id);
        if (itemOpt.isEmpty()) {
            throw new NotFoundException(format("Item ID %d not found", id));
        }
        return itemOpt.get();
    }

}
