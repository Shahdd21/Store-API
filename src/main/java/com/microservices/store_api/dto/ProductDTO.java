package com.microservices.store_api.dto;

public class ProductDTO {

    private Long productId;
    private String name;
    private String description;
    private String imageCover;
    private Double price;

    public ProductDTO() {

    }

    public ProductDTO(Long productId,String name, String description, String imageCover, Double price) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.imageCover = imageCover;
        this.price = price;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
