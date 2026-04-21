package com.hyx.hyxmovieweb.repository;

import com.hyx.hyxmovieweb.entity.FilmStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmStatisticsRepository extends JpaRepository<FilmStatistics, Integer> {
}