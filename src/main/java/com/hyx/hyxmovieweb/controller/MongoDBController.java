package com.hyx.hyxmovieweb.controller;

import com.hyx.hyxmovieweb.entity.MongoDBOrder;
import com.hyx.hyxmovieweb.entity.Result;
import com.hyx.hyxmovieweb.service.MongoDBService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mongo")
public class MongoDBController {

    @Autowired
    private MongoDBService mongoDBService;

    @PostMapping("/transferOders")
    public Result transferOrders() {
        try {
            mongoDBService.transferAllOrders();

            return Result.ok("MySQL订单导入MongoDB成功");
        } catch (Exception e) {
            return Result.error("订单迁移失败：" + e.getMessage());
        }
    }

    @PostMapping("/transferMyOrders")
    public Result transferCustom() {
        mongoDBService.transferAllOrders();

        return Result.ok("个人订单导入成功");
    }

    @PostMapping("/myOrders")
    public Result myOrders(@RequestParam(defaultValue = "1") Integer pageNo,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            userId = 79;
        }

        List<MongoDBOrder> orders = mongoDBService.getMyOrdersPaged(userId, pageNo, pageSize);

        return Result.ok("获取成功", orders);
    }

    @PostMapping("/top3ByClassify")
    public Result top3ByClassify() {
        try {
            List<Map> top3 = mongoDBService.getTop3Classify();

            return Result.ok("分类排行统计成功", top3);
        } catch (Exception e) {
            return Result.error("分类排行统计失败：" + e.getMessage());
        }
    }
}