package com.hyx.hyxmovieweb.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.hyx.hyxmovieweb.entity.*;

@Component
public class StatisticsMessageListener {
    @RabbitListener(queues = "queue.statistics")
    public void handleStatistics(Order order) {
        System.out.println("--- Statistics Service Update ---");
        System.out.println("Processing Sales Statistics for Film ID: " + order.getScheduleId());
        System.out.println("Adding Amount: " + order.totalPrice);
    }
}