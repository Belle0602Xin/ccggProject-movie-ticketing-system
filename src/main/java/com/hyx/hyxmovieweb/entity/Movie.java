package com.hyx.hyxmovieweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_schedule")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "price")
    public Double moviePrice;

    @Column(name = "quota")
    public Integer ticketsAvailable;

    @Column(name = "show_time")
    public String movieTime;

    private Integer version;

    @Column(name = "f_id")
    public Integer filmId;

    public String getMovieName() {
        return "电影编号: " + filmId;
    }

    public Integer getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(Integer quota) {
        this.ticketsAvailable = quota;
    }

    public Double getMoviePrice() {
        return moviePrice.doubleValue();
    }
}