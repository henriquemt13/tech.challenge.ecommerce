package com.tech.challenge.ecommerce.items.api.service;

import com.tech.challenge.ecommerce.items.api.domain.model.Item;
import com.tech.challenge.ecommerce.items.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.items.api.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ItemService {

    private final ItemRepository repository;

    public Item save(Item item) {
        log.info("Saving new Item {}", item.getName());
        return repository.save(item);
    }

    public Item update(Item updateItem, Long id) {
        log.info("Updating Item by Id {}", id);
        var currentItem = findValidItem(id);
        currentItem.setType(updateItem.getType());
        currentItem.setName(updateItem.getName());
        currentItem.setDescription(updateItem.getDescription());
        currentItem.setPrice(updateItem.getPrice());
        return repository.save(currentItem);
    }

    public void delete(Long id) {
        log.info("Deleting Item by Id {}", id);
        repository.delete(findValidItem(id));
    }

    public Optional<Item> findById(Long id) {
        log.info("Searching Item by Id {}", id);
        return repository.findById(id);
    }

    public List<Item> findAll() {
        log.info("Searching All Items");
        return repository.findAll();
    }

    private Item findValidItem(Long id) {
        var optItem = repository.findById(id);
        if (optItem.isEmpty()) {
            throw new NotFoundException(format("Item ID %d not found", id));
        }
        return optItem.get();
    }
}
