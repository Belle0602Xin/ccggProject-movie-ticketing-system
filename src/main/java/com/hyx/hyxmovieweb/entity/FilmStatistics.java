package com.hyx.hyxmovieweb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "t_statistics")
public class FilmStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private Double totalSales;

    @Column(name = "quality")
    private Integer totalTickets;

    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Transient
    private String lastUpdateTime;
}