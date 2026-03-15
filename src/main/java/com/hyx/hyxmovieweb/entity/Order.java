package com.hyx.hyxmovieweb.entity;
import java.io.Serializable;

public class Order implements Serializable {
    public String orderId;
    public String movieName;
    public int sessionId;
    public String userName;
    public int ticketsCount;
    public String orderTime;
    public double totalAmount;

    public Order() {}
}