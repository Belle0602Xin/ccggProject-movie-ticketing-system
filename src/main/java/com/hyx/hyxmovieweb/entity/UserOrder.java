package com.hyx.hyxmovieweb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "myOrders")
public class UserOrder {
    @Id
    private Integer userId;
    private List<MongoDBOrder> orders;
}