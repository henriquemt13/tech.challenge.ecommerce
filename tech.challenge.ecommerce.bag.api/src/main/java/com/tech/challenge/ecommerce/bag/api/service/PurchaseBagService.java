package com.tech.challenge.ecommerce.bag.api.service;

import com.tech.challenge.ecommerce.bag.api.domain.model.PurchaseBag;
import com.tech.challenge.ecommerce.bag.api.exceptions.NotFoundException;
import com.tech.challenge.ecommerce.bag.api.repository.PurchaseBagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PurchaseBagService {

    private final PurchaseBagRepository repository;
    private final ItemService itemService;

    public PurchaseBag save(PurchaseBag purchaseBag) {
        var previousBag = findNotPayedByIdUser(purchaseBag.getIdUserData());
        if (!previousBag.isEmpty()) {
            return validateAndSaveItem(purchaseBag, previousBag);
        }
        log.info("Saving new Item to Purchase Bag to User ID {}", purchaseBag.getIdUserData());
        return repository.save(purchaseBag);
    }

    private PurchaseBag validateAndSaveItem(PurchaseBag newItem, List<PurchaseBag> currentAddedItems) {
        validateItem(newItem.getIdItem());
        for (PurchaseBag currentItem : currentAddedItems) {
            if (currentItem.getIdItem().equals(newItem.getIdItem())) {
                currentItem.setQuantity(currentItem.getQuantity() + newItem.getQuantity());
                return repository.save(currentItem);
            }
        }
        return repository.save(newItem);
    }

    private void validateItem(Long id) {
        var optItem = itemService.findById(id);
        if (optItem.isEmpty()) {
            throw new NotFoundException(format("Item ID %d not found", id));
        }
    }

    public void deleteByIdItemAndIdUser(Long idItem, Long idUser) {
        var bagItems = findNotPayedByIdUser(idUser);
        validateItem(idItem);
        if (bagItems.isEmpty()) {
            throw new NotFoundException(format("Purchase Bag not found  for User ID %d ", idUser));
        }
        for (PurchaseBag item : bagItems) {
            if (item.getIdItem().equals(idItem)) {
                log.info("Deleting Item from User ID {} Purchase Bag ", item.getIdUserData());
                removeItemFromBag(item);
                return;
            }
        }
        throw new NotFoundException(format("Item ID %d not found on the User ID %d Purchase Bag", idItem, idUser));

    }

    private void removeItemFromBag(PurchaseBag item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            repository.save(item);
            return;
        }
        repository.delete(item);
    }

    public List<PurchaseBag> findNotPayedByIdUser(Long id) {
        log.info("Searching for Purchase Bag from User ID {}", id);
        return repository.findByIdUserDataAndPayed(id, false);
    }

}
