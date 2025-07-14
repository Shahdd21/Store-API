package com.microservices.store_api.dto;

import com.microservices.store_api.entity.Store;

import java.time.LocalDate;

public class StoreDTO {

    private Long productId;
    private Long warehouseId;
    private Integer quantity;
    private LocalDate updatedAt;

    public StoreDTO(){

    }

    public StoreDTO(Store store){
        this.productId = store.getStoreId().getProductId();
        this.warehouseId = store.getStoreId().getWarehouseId();
        this.quantity = store.getQuantity();
        this.updatedAt = store.getUpdatedAt();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
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
}
