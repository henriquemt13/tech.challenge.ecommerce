package com.tech.challenge.ecommerce.payment.api.service;

import com.tech.challenge.ecommerce.payment.api.domain.model.PurchaseBag;
import com.tech.challenge.ecommerce.payment.api.repository.PurchaseBagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PurchaseBagService {

    private final PurchaseBagRepository repository;

    public List<PurchaseBag> findNotPayedByIdUser(Long id) {
        log.info("Searching form Purchase Bag from User ID {}", id);
        return repository.findByIdUserDataAndPayed(id, false);
    }

    public PurchaseBag save(PurchaseBag purchaseBag) {
        log.info("Paying Item {} from User ID {} Purchase Bag", purchaseBag.getIdItem(), purchaseBag.getIdUserData());
        return repository.save(purchaseBag);
    }
}
