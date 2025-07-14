package com.microservices.store_api.entity;

import com.microservices.store_api.enums.Operation;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @JoinColumn(name = "fk_warehouse_id")
    @ManyToOne
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation", length = 20)
    private Operation operation;

    private Integer quantityChanged;

    private LocalDate createdAt;

    public History(){}

    public History(Long productId, Warehouse warehouse, Operation operation, Integer quantityChanged, LocalDate createdAt) {
        this.productId = productId;
        this.warehouse = warehouse;
        this.operation = operation;
        this.quantityChanged = quantityChanged;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getQuantityChanged() {
        return quantityChanged;
    }

    public void setQuantityChanged(Integer quantityChanged) {
        this.quantityChanged = quantityChanged;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
