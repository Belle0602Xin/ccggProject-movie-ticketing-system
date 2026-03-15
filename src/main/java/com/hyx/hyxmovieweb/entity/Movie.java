package com.hyx.hyxmovieweb.entity;

public class Movie {
    public int movieId;
    public String movieName;
    public String movieTime;
    public int ticketsAvailable;
    public double moviePrice;

    public Movie(int id, String name, String time, int tickets, double price) {
        this.movieId = id;
        this.movieName = name;
        this.movieTime = time;
        this.ticketsAvailable = tickets;
        this.moviePrice = price;
    }

    public int getTicketsAvailable() {
        return this.ticketsAvailable;
    }
}