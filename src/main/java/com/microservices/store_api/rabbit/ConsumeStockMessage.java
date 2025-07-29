package com.microservices.store_api.rabbit;

import java.util.Map;

public class ConsumeStockMessage {

    private Long orderId;
    private Map<Long, Integer> products_quantities;

    public ConsumeStockMessage(){}

    public ConsumeStockMessage(Long orderId, Map<Long, Integer> products_quantities) {
        this.orderId = orderId;
        this.products_quantities = products_quantities;
    }

    public Map<Long, Integer> getProducts_quantities() {
        return products_quantities;
    }

    public void setProducts_quantities(Map<Long, Integer> products_quantities) {
        this.products_quantities = products_quantities;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
