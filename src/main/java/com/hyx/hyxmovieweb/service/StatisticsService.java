package com.hyx.hyxmovieweb.service;

import com.hyx.hyxmovieweb.entity.FilmStatistics;
import com.hyx.hyxmovieweb.entity.Order;
import com.hyx.hyxmovieweb.repository.FilmStatisticsRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StatisticsService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final FilmStatisticsRepository filmStatsRepository;

    public StatisticsService(RedisTemplate<String, Object> redisTemplate, FilmStatisticsRepository filmStatsRepository) {
        this.redisTemplate = redisTemplate;
        this.filmStatsRepository = filmStatsRepository;
    }

    @Transactional
    public void updateStatistics(Order order) {
        redisTemplate.opsForZSet().incrementScore("rank:film:sales",
                order.getScheduleId().toString(),
                order.getTotalPrice());

        FilmStatistics filmStatistics = filmStatsRepository.findById(order.getScheduleId())
                .orElse(new FilmStatistics());

        filmStatistics.setScheduleId(order.getScheduleId());
        filmStatistics.setTotalSales((filmStatistics.getTotalSales() == null ? 0 : filmStatistics.getTotalSales()) + order.getTotalPrice());
        filmStatistics.setTotalTickets((filmStatistics.getTotalTickets() == null ? 0 : filmStatistics.getTotalTickets()) + order.getTicketsQuality());
        filmStatistics.setLastUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        filmStatsRepository.save(filmStatistics);
    }
}
