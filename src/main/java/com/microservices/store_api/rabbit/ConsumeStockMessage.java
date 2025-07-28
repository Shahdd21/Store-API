package com.microservices.store_api.rabbit;

import java.util.Map;

public class ConsumeStockMessage {

    private Map<Long, Integer> products_quantities;

    public ConsumeStockMessage(){}

    public ConsumeStockMessage(Map<Long, Integer> products_quantities) {
        this.products_quantities = products_quantities;
    }

    public Map<Long, Integer> getProducts_quantities() {
        return products_quantities;
    }

    public void setProducts_quantities(Map<Long, Integer> products_quantities) {
        this.products_quantities = products_quantities;
    }
}
