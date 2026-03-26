package com.hyx.hyxmovieweb.controller;

import com.hyx.hyxmovieweb.entity.Result;
import com.hyx.hyxmovieweb.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchService esService;

    @RequestMapping("/createIndex")
    public Result createIndex() {
        esService.createIndexWithMapping();

        return Result.ok("索引及映射创建成功");
    }

    @PostMapping("/freeSearch")
    public Result searchFree(@RequestBody Map<String, Object> condition) {
        String content = (String) condition.get("content");
        if (content == null) {
            return Result.error("搜索内容不能为空");
        }

        int pageNo = (int) condition.getOrDefault("pageNo", 1);
        int pageSize = (int) condition.getOrDefault("pageSize", 10);

        return Result.ok("搜索成功", esService.freeSearch(content, pageNo - 1, pageSize));
    }

    @RequestMapping("/full")
    public Result indexAll() {
        esService.syncAllToEs();
        return Result.ok("全量数据同步完成");
    }

    @RequestMapping("/groupByClassify")
    public Result groupByClassify() {
        return Result.ok("统计成功", esService.countByClassify());
    }

    @PostMapping("/searchByCondition")
    public Result searchByCondition(@RequestBody Map<String, Object> condition) {
        return Result.ok("搜索成功", esService.searchByCondition(condition));
    }
}

