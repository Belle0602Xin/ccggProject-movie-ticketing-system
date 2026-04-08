package com.hyx.hyxmovieweb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orders")
public class MongoDBOrder {
    @Id
    private String id;
    private Integer quantity;
    private String address;
    private String orderTime;
    private String ticketNo;
    private Double price;
    private String showTime;
    private String filmName;
    private String classify;
    private Integer customerId;
}
