package com.hyx.hyxmovieweb.service;

import com.hyx.hyxmovieweb.entity.*;
import com.hyx.hyxmovieweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MongoDBService {

    @Autowired private OrderRepository mysqlOrderRepo;
    @Autowired private MovieRepository movieRepo;
    @Autowired private FilmRepository filmRepo;
    @Autowired private MongoTemplate mongoTemplate;

    public void transferAllOrders() {
        List<Order> allMysqlOrders = mysqlOrderRepo.findAll();


        mongoTemplate.dropCollection("orders");

        for (Order mo : allMysqlOrders) {
            MongoDBOrder mongoDBOrder = new MongoDBOrder();
            mongoDBOrder.setOrderTime(mo.orderTime);
            mongoDBOrder.setPrice(mo.totalAmount);
            mongoDBOrder.setQuantity(mo.ticketsCount);
            mongoDBOrder.setCustomerId(mo.customerId);

            movieRepo.findById(mo.sessionId).ifPresent(movie -> {
                filmRepo.findById(movie.getFilmId()).ifPresent(film -> {
                    mongoDBOrder.setFilmName(film.getName());
                    mongoDBOrder.setClassify(film.getClassify());
                });
            });

            mongoTemplate.save(mongoDBOrder, "orders");
        }
    }

   public List<MongoDBOrder> getMyOrdersPaged(Integer userId, int pageNo, int pageSize) {
        Query query = new Query(Criteria.where("customerId").is(userId));
        query.with(Sort.by(Sort.Direction.DESC, "orderTime"));
        query.skip((long) (pageNo - 1) * pageSize).limit(pageSize);

        return mongoTemplate.find(query, MongoDBOrder.class, "orders");
   }

   public List<Map> getTop3Classify() {
        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.group("classify")
                        .sum("price").as("totalAmount")
                        .count().as("totalSales"),

                Aggregation.project("totalAmount", "totalSales").and("_id").as("classify"),
                Aggregation.sort(Sort.Direction.DESC, "totalAmount"),
                Aggregation.limit(3)
        );

        return mongoTemplate.aggregate(aggregation, "orders", Map.class).getMappedResults();
   }
}