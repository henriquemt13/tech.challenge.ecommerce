package com.tech.challenge.ecommerce.payment.api.service;


import com.tech.challenge.ecommerce.payment.api.domain.dto.response.PaymentResponseDTO;
import com.tech.challenge.ecommerce.payment.api.domain.model.Item;
import com.tech.challenge.ecommerce.payment.api.domain.model.PurchaseBag;
import com.tech.challenge.ecommerce.payment.api.exceptions.BadRequestException;
import com.tech.challenge.ecommerce.payment.api.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

    private UserDataService userDataService;
    private PurchaseBagService purchaseBagService;
    private ItemService itemService;


    public void payPurchaseBag(String userEmail) {
        var userId = findUserId(userEmail);
        var bag = purchaseBagService.findNotPayedByIdUser(userId);
        if (bag.isEmpty()) {
            throw new BadRequestException(format("Empty Bag for User Email %s", userEmail));
        }
        log.info("Paying Purchase Bag for User {}", userEmail);
        for (PurchaseBag item : bag) {
            item.setPayed(true);
            purchaseBagService.save(item);
        }

    }

    public PaymentResponseDTO getPurchaseBagResume(String userEmail) {
        var userId = findUserId(userEmail);
        var bag = purchaseBagService.findNotPayedByIdUser(userId);
        List<Item> items = new ArrayList<>();
        if (bag.isEmpty()) {
            throw new BadRequestException(format("Empty Bag for User Email %s", userEmail));
        }
        log.info("Searching for Purchase Bag Resume for User {}", userEmail);
        for (PurchaseBag item : bag) {
            items.add(findItemById(item.getIdItem(), userEmail));
        }
        return buildPaymentResponse(items, bag);

    }

    private Long findUserId(String email) {
        var optUser = userDataService.findByEmail(email);
        if (optUser.isEmpty()) {
            throw new NotFoundException(format("User Email %s not found", email));
        }
        return optUser.get().getId();
    }

    private Item findItemById(Long idItem, String email) {
        var optItem = itemService.findById(idItem);
        if (optItem.isEmpty()) {
            throw new NotFoundException(format("Item not found from User %s Purchase Bag", email));
        }
        return optItem.get();
    }

    private PaymentResponseDTO buildPaymentResponse(List<Item> items, List<PurchaseBag> bag) {
        return PaymentResponseDTO
                .builder()
                .items(items)
                .totalValue(calculateTotalToPay(items, bag))
                .build();
    }

    private BigDecimal calculateTotalToPay(List<Item> items, List<PurchaseBag> bag) {
        BigDecimal valueToPay = BigDecimal.ZERO;
        for (Item item : items) {
            valueToPay = valueToPay.add(item.getPrice()
                    .multiply(BigDecimal.valueOf(findItemQuantity(item, bag))));
        }
        return valueToPay;
    }

    private Integer findItemQuantity(Item item, List<PurchaseBag> bag) {
        return bag.stream().filter(b -> b.getIdItem().equals(item.getId())).toList().get(0).getQuantity();
    }

}
