package com.hyx.hyxmovieweb;

import com.hyx.hyxmovieweb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HyxMovieWebApplication implements CommandLineRunner {

    @Autowired
    private MovieService movieService;

    public static void main(String[] args) {
        SpringApplication.run(HyxMovieWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        movieService.warmUpRedisInventory();
    }
}
