package com.tech.challenge.ecommerce.items.api.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @SequenceGenerator(name = "item_seq",
            sequenceName = "item_seq", allocationSize = 1)
    @GeneratedValue(generator = "item_seq")
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "name")
    @NotNull(message = "Please enter item name")
    @NotEmpty(message = "Please enter item name")
    private String name;
    @Column(name = "description")
    @NotNull(message = "Please enter item description")
    @NotEmpty(message = "Please enter item description")
    private String description;
    @Column(name = "type")
    @NotNull(message = "Please enter item type")
    private TypeEnum type;
    @Column(name = "price")
    @NotNull(message = "Please enter item price")
    private BigDecimal price;
    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
