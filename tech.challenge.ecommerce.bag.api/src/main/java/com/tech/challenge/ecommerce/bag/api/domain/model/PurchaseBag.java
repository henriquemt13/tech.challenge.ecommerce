package com.tech.challenge.ecommerce.bag.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "purchase_bag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseBag {

    @Id
    @SequenceGenerator(name = "item_seq",
            sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(generator = "item_seq")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "id_user_data", unique = true)
    @NotNull(message = "Please enter idUserData")
    private Long idUserData;
    @Column(name = "id_item", unique = true)
    @NotNull(message = "Please enter idItem")
    private Long idItem;
    @Column(name = "quantity")
    @NotNull(message = "Please enter item quantity")
    private Integer quantity;
    @Column(name = "payed")
    private Boolean payed = false;
    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}

