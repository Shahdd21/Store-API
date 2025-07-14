package com.microservices.store_api.dto;

import com.microservices.store_api.entity.Store;

import java.time.LocalDate;

public class StoreDTOByProduct {

    private Long warehouseId;
    private String location;
    private Integer quantity;
    private LocalDate updatedAt;

    public StoreDTOByProduct() {

    }

    public StoreDTOByProduct(Store store, String location){
        this.warehouseId = store.getStoreId().getWarehouseId();
        this.location = location;
        this.quantity = store.getQuantity();
        this.updatedAt = store.getUpdatedAt();
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

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
