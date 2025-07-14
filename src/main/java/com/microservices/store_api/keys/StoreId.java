package com.microservices.store_api.keys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class StoreId implements Serializable {

    private Long productId;

    private Long warehouseId;

    public StoreId(){

    }

    public StoreId(Long productId, Long warehouseId) {
        this.productId = productId;
        this.warehouseId = warehouseId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
