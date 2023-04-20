package com.liu.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.liu.common.Result;
import com.liu.config.AuthAccess;
import com.liu.entity.User;
import com.liu.mapper.FileMapper;
import com.liu.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.sl.usermodel.PlaceholderDetails.PlaceholderSize.quarter;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Resource
    private FileMapper fileMapper;

    @GetMapping("/example")
    public Result get(){
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("1", "2", "3", "4", "5", "6", "7"));
        map.put("y", CollUtil.newArrayList(114, 150, 100, 300, 230, 166, 173));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0; //第一季度
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : list){
            Date createTime = user.getCreateTime();
            DateUtil.quarterEnum(createTime);
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @AuthAccess
    @GetMapping("/file/front/all")
    public Result frontAll(){
        return Result.success(fileMapper.selectList(null));
    }
}
