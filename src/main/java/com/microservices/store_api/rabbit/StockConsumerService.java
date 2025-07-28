package com.microservices.store_api.rabbit;

import com.microservices.store_api.service.StoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class StockConsumerService {

    private final StoreService storeService;

    public StockConsumerService(StoreService storeService) {
        this.storeService = storeService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.consume.queue}")
    public void consumeStock(ConsumeStockMessage consumeStockMessage){

        System.out.println("received message from order: "+ consumeStockMessage.getProducts_quantities());

        storeService.consumeStockForOrder(consumeStockMessage.getProducts_quantities());
    }
}
