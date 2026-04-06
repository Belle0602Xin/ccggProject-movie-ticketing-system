package com.hyx.hyxmovieweb.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class OrderService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String bookTicket(Integer movieId, Integer userId) {
        String lockKey = "lock:movie:" + movieId;
        String stockKey = "ticket:stock:" + movieId;

        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                Integer stock = (Integer) redisTemplate.opsForValue().get(stockKey);

                if (stock != null && stock > 0) {
                    redisTemplate.opsForValue().decrement(stockKey);

                    return "Success: User " + userId + " got the ticket.";
                } else {
                    return "Fail: Sold out!";
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return "Fail: System busy, try again.";
    }
}