package com.microservices.store_api.entity;

import com.microservices.store_api.keys.StoreId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Store {

    @EmbeddedId
    private StoreId storeId;

    @MapsId("warehouseId")
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    private Integer quantity;

    private LocalDate updatedAt;

    public StoreId getStoreId() {
        return storeId;
    }

    public void setStoreId(StoreId storeId) {
        this.storeId = storeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
