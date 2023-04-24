package com.liu.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.liu.common.Constants;
import com.liu.common.Result;
import com.liu.config.AuthAccess;
import com.liu.entity.Files;
import com.liu.entity.User;
import com.liu.mapper.FileMapper;
import com.liu.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.liu.common.Constants.FILES_KEY;
import static org.apache.poi.sl.usermodel.PlaceholderDetails.PlaceholderSize.quarter;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Resource
    private FileMapper fileMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        //1.从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(FILES_KEY);
        List<Files> files;
        if(StrUtil.isBlank(jsonStr)){  //取出为空（没有缓存）
             files = fileMapper.selectList(null);
             //在缓存到redis
            stringRedisTemplate.opsForValue().set(FILES_KEY, JSONUtil.toJsonStr(files));
        }
        else {
            //如果有 则从redis中取出数据
            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
            }, true);
        }

        return Result.success(files);
    }
}
