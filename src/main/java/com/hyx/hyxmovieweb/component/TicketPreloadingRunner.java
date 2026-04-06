package com.hyx.hyxmovieweb.component;

import com.hyx.hyxmovieweb.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class TicketPreloadingRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public void run(String... args) {
        filmRepository.findAll().forEach(film -> {
            String key = "ticket:stock:" + film.getId();
            redisTemplate.opsForValue().set(key, 100);
        });

        System.out.println("Redis Warm-up: All movie ticket stocks loaded.");
    }
}
