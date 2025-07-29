package com.microservices.store_api.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.consume.queue}")
    private String consumeQueue;

    @Value("${spring.rabbitmq.consume.routingkey}")
    private String consumeRoutingKey;

    @Value("${spring.rabbitmq.response.queue}")
    private String responseQueue;

    @Value("${spring.rabbitmq.response.routingkey}")
    private String responseRoutingKey;

    @Bean
    public Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Queue consumeQueue() {
        return new Queue(consumeQueue, false);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(responseQueue, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

    @Bean
    public Binding consumeBinding() {
        return BindingBuilder.bind(consumeQueue()).to(exchange()).with(consumeRoutingKey);
    }

    @Bean
    public Binding responseBinding() {
        return BindingBuilder.bind(responseQueue())
                .to(exchange())
                .with(responseRoutingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
