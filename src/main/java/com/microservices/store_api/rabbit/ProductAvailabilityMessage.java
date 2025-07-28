package com.microservices.store_api.rabbit;

public class ProductAvailabilityMessage {

    private Long productId;
    private boolean isAvailable;

    public ProductAvailabilityMessage(){

    }

    public ProductAvailabilityMessage(Long productId, boolean isAvailable){
        this.productId = productId;
        this.isAvailable = isAvailable;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
