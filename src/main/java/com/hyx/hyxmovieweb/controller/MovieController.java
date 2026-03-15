package com.hyx.hyxmovieweb.controller;

import com.hyx.hyxmovieweb.entity.*;
import com.hyx.hyxmovieweb.service.MovieService;
import com.hyx.hyxmovieweb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping("/book")
    public Result book(@RequestParam String sid, @RequestParam String uid, @RequestParam int count) {

        if (count <= 0 || count > 100) {
            return Result.error("购票张数必须在1-100之间");
        }

        Movie movie = movieService.findMovieById(sid);

        if (movie == null) {
            return Result.error("购票失败：场次不存在");
        }

        if (count > movie.getTicketsAvailable()) {
            return Result.error("购票失败：余票不足 (当前仅剩 " + movie.getTicketsAvailable() + " 张票)");
        }

        movieService.bookTicket(Integer.parseInt(sid), count, uid);

        return Result.ok("购票成功");
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return movieService.getAllOrders();
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if ("admin".equals(username) && "123456".equals(password)) {
            return "OK";
        }
        return "FAIL";
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        List<String> errors = new ArrayList<>();

        // 1. 账号长度校验 (3~20个字符)
        if (user.username == null || user.username.length() < 3 || user.username.length() > 20) {
            errors.add("账号长度在3~20个字符");
        }

        // 2. 密码长度校验 (6~20个字符)
        if (user.password == null || user.password.length() < 6 || user.password.length() > 20) {
            errors.add("用户密码在6~20个字符");
        }

        // 3. 性别校验
        if (user.gender == null || user.gender.isEmpty()) {
            errors.add("性别不能为空");
        }

        // 4. 昵称长度校验（2~20个字符）
        if (user.nickname == null || user.nickname.length() < 2 || user.nickname.length() > 20) {
            errors.add("昵称length must be between 2 and 20");
        }

        // 5. 邮箱长度校验 (10~30个字符)
        if (user.email == null || user.email.length() < 10 || user.email.length() > 30) {
            errors.add("邮箱在10到30个字符");
        }

        // 如果有任何错误，返回错误列表
        if (!errors.isEmpty()) {
            return Result.error("参数不符合要求", errors);
        }

        return Result.ok("注册成功");
    }


    @PostMapping("/save")
    public Result save() {
        String res = movieService.saveData();

        if ("SUCCESS".equals(res)) {
            return Result.ok("数据备份成功");
        } else {
            return Result.error("备份失败: " + res);
        }
    }

    @PostMapping("/load")
    public Result load() {
        String res = movieService.loadData();

        return res.equals("SUCCESS") ? Result.ok("数据加载成功") : Result.error(res);
    }
}