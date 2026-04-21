package com.hyx.hyxmovieweb.component;

import com.hyx.hyxmovieweb.entity.Order;
import com.hyx.hyxmovieweb.service.MongoDBService;
import com.hyx.hyxmovieweb.service.StatisticsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class OrderMessageListener {

    private final MongoDBService mongoDBService;
    private final StatisticsService statisticsService;
    private final RedisTemplate<String, Object> redisTemplate;

    public OrderMessageListener(MongoDBService mongoDBService, StatisticsService statisticsService, RedisTemplate<String, Object> redisTemplate) {
        this.mongoDBService = mongoDBService;
        this.statisticsService = statisticsService;
        this.redisTemplate = redisTemplate;
    }

    @RabbitListener(queues = "queue.mongodb")
    public void onMongoDBMessage(Order order) {
        String lockKey = "msg:idempotent:mongodb:" + order.getId();
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(lockKey, "processed", Duration.ofHours(24));

        if (Boolean.TRUE.equals(isNew)) {
            System.out.println("Received MongoDB sync task: Order ID: " + order.id);
            mongoDBService.saveSingleOrder(order);
        } else {
            System.out.println("Duplicate MongoDB message detected, skipping: " + order.id);
        }
    }

    @RabbitListener(queues = "queue.statistics")
    public void onStatisticsMessage(Order order) {
        String lockKey = "msg:idempotent:stats:" + order.getId();
        Boolean isNew = redisTemplate.opsForValue().setIfAbsent(lockKey, "processed", Duration.ofHours(24));

        if (Boolean.TRUE.equals(isNew)) {
            System.out.println("--- Statistics Service Update ---");
            System.out.println("Processing sales statistics for Order ID: " + order.id);

            System.out.println("--- Statistics Service Updating sales data ---");
            statisticsService.updateStatistics(order);
            System.out.println("Statistics updated successfully for Schedule ID: " + order.getScheduleId());
        } else {
            System.out.println("Duplicate Statistics message detected, skipping: " + order.id);
        }
    }
}

//@RabbitListener(queues = "order.queue")
//public void handleOrderMessage(Order order) {
//    String lockKey = "msg:idempotent:" + order.getId();
//    Boolean isNew = redisTemplate.opsForValue().setIfAbsent(lockKey, "processed", Duration.ofHours(24));
//
//    if (Boolean.TRUE.equals(isNew)) {
//        mongoDBService.saveSingleOrder(order);
//        System.out.println("Message processed successfully: " + order.getId());
//    } else {
//        System.out.println("Duplicate message detected, skipping: " + order.getId());
//    }
//}