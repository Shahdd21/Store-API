package com.microservices.store_api.rabbit;

import com.microservices.store_api.service.StoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockConsumerService {

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.response.routingkey}")
    private String responseRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    private final StoreService storeService;

    public StockConsumerService(RabbitTemplate rabbitTemplate, StoreService storeService) {
        this.rabbitTemplate = rabbitTemplate;
        this.storeService = storeService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.consume.queue}")
    public void consumeStock(ConsumeStockMessage consumeStockMessage){

        System.out.println("received message from order: "+ consumeStockMessage.getProducts_quantities());

        StockResponse stockResponse = storeService.consumeStockForOrder(consumeStockMessage.getOrderId(),
                consumeStockMessage.getProducts_quantities());

        rabbitTemplate.convertAndSend(exchange, responseRoutingKey, stockResponse);
    }
}
