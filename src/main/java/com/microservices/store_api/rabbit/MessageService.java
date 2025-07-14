package com.microservices.store_api.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final RabbitTemplate rabbitTemplate;

    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    public String sendMessage(Long productId, boolean isAvailable){

        Message message = new Message(productId, isAvailable);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);

        return "Notification sent to ProductService about product availability";
    }
}
