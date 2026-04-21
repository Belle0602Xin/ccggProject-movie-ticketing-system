package com.hyx.hyxmovieweb.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_ORDER = "exchange.order";
    public static final String QUEUE_MONGODB = "queue.mongodb";
    public static final String QUEUE_STATISTICS = "queue.statistics";
    public static final String ROUTING_KEY = "newOrder";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(EXCHANGE_ORDER);
    }

    @Bean
    public Queue mongodbQueue() {
        return new Queue(QUEUE_MONGODB, true);
    }

    @Bean
    public Queue statisticsQueue() {
        return new Queue(QUEUE_STATISTICS, true);
    }

    @Bean
    public Binding bindingMongodb() {
        return BindingBuilder.bind(mongodbQueue()).to(orderExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingStatistics() {
        return BindingBuilder.bind(statisticsQueue()).to(orderExchange()).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());

        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }