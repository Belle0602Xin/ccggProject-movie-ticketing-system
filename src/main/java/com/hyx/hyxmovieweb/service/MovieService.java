package com.hyx.hyxmovieweb.service;

import com.hyx.hyxmovieweb.entity.*;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

@Service
public class MovieService {
    private static List<Movie> movies = new ArrayList<>();
    private static List<Order> orders = new ArrayList<>();

    static {
        movies.add(new Movie(100, "蜀山传", "2023-01-10 09:10:00", 200, 100.0));
        movies.add(new Movie(120, "蜀山传", "2023-01-12 11:10:00", 200, 100.0));
        movies.add(new Movie(200, "英雄", "2023-01-12 09:10:00", 200, 120.0));
        movies.add(new Movie(300, "机械师2：复活", "2023-01-13 11:10:00", 150, 300.0));
    }

    public List<Movie> getAllMovies() { return movies; }
    public List<Order> getAllOrders() { return orders; }

    public synchronized String bookTicket(int sid, int count, String user) {
        for (Movie movie : movies) {
            if (movie.movieId == sid && movie.ticketsAvailable >= count) {
                Order order = new Order();

                order.orderId = "HYX" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                order.movieName = movie.movieName;
                order.sessionId = sid;
                order.userName = user;
                order.ticketsCount = count;
                order.totalAmount = count * movie.moviePrice;
                order.orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                orders.add(order);

                new Thread(() -> {
                    synchronized (movie) {
                        movie.ticketsAvailable -= count;
                    }
                }).start();
                return "SUCCESS";
            }
        }
        return "FAIL";
    }

    public Movie findMovieById(String sid) {
        int id = Integer.parseInt(sid);

        for (Movie movie : movies) {
            if (movie.movieId == id) {
                return movie;
            }
        }

        return null;
    }

    private final String FILE_PATH = "orders_data.txt";

    public synchronized String saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(orders);

            return "SUCCESS";
        } catch (IOException e) {
            return "FAIL: " + e.getMessage();
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized String loadData() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return "FAIL: 文件不存在";
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            this.orders = (List<Order>) ois.readObject();

            return "SUCCESS";
        } catch (Exception e) {
            return "FAIL: " + e.getMessage();
        }
    }
}