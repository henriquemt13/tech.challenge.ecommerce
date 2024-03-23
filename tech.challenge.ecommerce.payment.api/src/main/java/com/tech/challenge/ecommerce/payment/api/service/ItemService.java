package com.tech.challenge.ecommerce.payment.api.service;

import com.tech.challenge.ecommerce.payment.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.payment.api.repository.ItemRepository;
import com.tech.challenge.ecommerce.payment.api.domain.model.Item;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ItemService {

    private final ItemRepository repository;

    public Optional<Item> findById(Long id) {
        log.info("Searching Item by Id {}", id);
        return repository.findById(id);
    }

}
