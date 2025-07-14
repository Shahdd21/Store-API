package com.microservices.store_api.dto;

import com.microservices.store_api.entity.History;

import java.time.LocalDate;

public class HistoryDTO {

    private Long productId;
    private Long warehouseId;
    private String operation;
    private Integer quantityChanged;
    private LocalDate createdAt;

    public HistoryDTO(){}

    public HistoryDTO(History history){
        this.productId = history.getProductId();
        this.warehouseId = history.getWarehouse().getWarehouseId();
        this.operation = history.getOperation().name();
        this.quantityChanged = history.getQuantityChanged();
        this.createdAt = history.getCreatedAt();
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
