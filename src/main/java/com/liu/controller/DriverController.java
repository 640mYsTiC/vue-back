package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.liu.service.IDriverService;
import com.liu.entity.Driver;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-06-02
 */
@RestController
@RequestMapping("/driver")
public class DriverController {

    @Resource
    private IDriverService driverService;

    @PostMapping
    public Result save(@RequestBody Driver driver) {
        driverService.saveOrUpdate(driver);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        driverService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        driverService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(driverService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(driverService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<Driver> queryWrapper = new QueryWrapper<>();
        return Result.success(driverService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
