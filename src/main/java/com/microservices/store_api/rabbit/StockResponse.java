package com.microservices.store_api.rabbit;

import java.util.Map;

public class StockResponse {
    private Long orderId;
    private boolean success;
    private String message;
    private Map<String, Integer> insufficientProducts;

    public StockResponse() {}

    public StockResponse(Long orderId, boolean success, String message, Map<String, Integer> insufficientProducts) {
        this.orderId = orderId;
        this.success = success;
        this.message = message;
        this.insufficientProducts = insufficientProducts;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Integer> getInsufficientProducts() {
        return insufficientProducts;
    }

    public void setInsufficientProducts(Map<String, Integer> insufficientProducts) {
        this.insufficientProducts = insufficientProducts;
    }
}
