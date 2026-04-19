package com.hyx.hyxmovieweb.component;

import com.hyx.hyxmovieweb.entity.Order;
import com.hyx.hyxmovieweb.service.MongoDBService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageListener {

    private final MongoDBService mongoDBService;

    public OrderMessageListener(MongoDBService mongoDBService) {
        this.mongoDBService = mongoDBService;
    }

    @RabbitListener(queues = "queue.mongodb")
    public void onMongodbMessage(Order order) {
        System.out.println("收到 MongoDB 同步任务：" + "订单 ID：" + order.id);
        mongoDBService.saveSingleOrder(order);
    }

    @RabbitListener(queues = "queue.statistics")
    public void onStatisticsMessage(Order order) {
        System.out.println("收到分场次统计任务：" + order.id);
        // statisticsService.update(order);
    }
}