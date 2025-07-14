package com.microservices.store_api.dto;

import com.microservices.store_api.entity.Store;

import java.time.LocalDate;

public class StoreDTOByWarehouse {

    private String name;
    private Long productId;
    private String description;
    private String imageCover;
    private Double price;
    private Integer quantity;
    private LocalDate updatedAt;

    public StoreDTOByWarehouse() {

    }

    public StoreDTOByWarehouse(ProductDTO productDTO, Store store){
        this.name = productDTO.getName();
        this.productId = productDTO.getProductId();
        this.description = productDTO.getDescription();
        this.imageCover = productDTO.getImageCover();
        this.price = productDTO.getPrice();
        this.quantity = store.getQuantity();
        this.updatedAt = store.getUpdatedAt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
