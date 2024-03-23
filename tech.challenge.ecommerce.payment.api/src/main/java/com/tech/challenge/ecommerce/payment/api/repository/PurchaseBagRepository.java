package com.tech.challenge.ecommerce.payment.api.repository;

import com.tech.challenge.ecommerce.payment.api.domain.model.PurchaseBag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseBagRepository extends JpaRepository<PurchaseBag, Long> {

    List<PurchaseBag> findByIdUserDataAndPayed(Long idUserData, Boolean payed);
}
