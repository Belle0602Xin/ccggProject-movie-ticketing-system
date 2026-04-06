package com.hyx.hyxmovieweb.controller;

import com.hyx.hyxmovieweb.entity.*;
import com.hyx.hyxmovieweb.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@CrossOrigin(
        origins = "http://localhost:3000",
        allowCredentials = "true",
        allowedHeaders = "*",
        exposedHeaders = "Set-Cookie"
)
@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public Result getMovies(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) String user,
                            HttpSession session) {
        String loginUser = (String) session.getAttribute("currentUser");

        if (loginUser == null) {
            loginUser = user;
        }

        if (loginUser == null) {
            return Result.error("未登录");
        }

        Page<Movie> moviePage = movieService.getMoviesPage(page);

        return Result.ok("查询成功", moviePage);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user, HttpSession session) {
        if (user.username == null || user.username.length() < 3) {
            return Result.error("账号过短");
        }

        if (user.password == null || user.password.length() < 6 || user.password.length() > 20) {
            return Result.error("密码长度6~20");
        }

        if (user.gender == null || user.gender.isEmpty()) {
            return Result.error("性别不能为空");
        }

        if (user.nickname == null || user.nickname.length() < 2 || user.nickname.length() > 20) {
            return Result.error("昵称长度2~20");
        }

        if (user.email == null || user.email.length() < 10 || user.email.length() > 30) {
            return Result.error("邮箱在10到30个字符");
        }

        movieService.addUser(user);
        session.setAttribute("currentUser", user.username);

        return Result.ok("注册成功");
    }

    @PostMapping("/book")
    public ResponseEntity<Result> book(@RequestParam String sid,
                                       @RequestParam int count,
                                       @RequestParam(required = false) String user,
                                       HttpSession session) {

        String loginUser = (user != null) ? user : "testUser";
        int movieSid = Integer.parseInt(sid);

        try {
            String res = movieService.bookTicket(movieSid, 123);

            if (res.contains("Success")) {
                return ResponseEntity.ok(Result.ok(res));
            } else {
                return ResponseEntity.status(400).body(Result.error(res));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Result.error(e.getMessage()));
        }
    }

    @GetMapping("/orders")
    public Result getOrders(@RequestParam(required = false) String user, HttpSession session) {
        String loginUser = (String) session.getAttribute("currentUser");

        if (loginUser == null) {
            loginUser = user;
        }

        if (loginUser == null) {
            return Result.error("未登录，无法查看订单");
        }

        return Result.ok("获取成功", movieService.getOrdersByUsername(loginUser));
    }

    @GetMapping("/sales")
    public Result getSales(@RequestParam(required = false) String user, HttpSession session) {
        String loginUser = (String) session.getAttribute("currentUser");

        if (loginUser == null) {
            loginUser = user;
        }

        if (loginUser == null) {
            return Result.error("未登录");
        }

        return Result.ok("获取成功", movieService.getSalesStatistics());
    }

    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        User user = movieService.login(username, password);

        if (user != null) {
            session.setAttribute("currentUser", username);

            return Result.ok("登录成功", user.nickname);
        }
        return Result.error("账号或密码错误");
    }

    @PostMapping("/save")
    public Result save() {
        return Result.ok("数据库版本自动实时保存");
    }

    @PostMapping("/load")
    public Result load() {
        return Result.ok("数据库数据已是最新");
    }
}

//    public Result getMovies(@RequestParam(defaultValue = "0") int page, HttpSession session) {
//        if (session.getAttribute("currentUser") == null) {
//            return Result.error("未登录");
//        }
//
//        Page<Movie> moviePage = movieService.getMoviesPage(page);
//
//        return Result.ok("查询成功", moviePage);
//    }


//        String loginUser = (String) session.getAttribute("currentUser");
//        if (loginUser == null) {
//            loginUser = user;
//        }
//
//        if (loginUser == null) {
//            return Result.error("请登录后再进行购票操作");
//        }
//
//        int movieSid = Integer.parseInt(sid);
//
//        try {
//            String res = movieService.bookTicket(movieSid, count, loginUser);
//            return "SUCCESS".equals(res) ? Result.ok("购票成功") : Result.error("购票失败");
//        } catch (Exception e) {
//            return Result.error(e.getMessage());
//        }

