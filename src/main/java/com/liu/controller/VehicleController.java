package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.entity.Vehicle;
import com.liu.service.IVehicleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-06-05
 */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Resource
    private IVehicleService vehicleService;

    @PostMapping
    public Result save(@RequestBody Vehicle vehicle) {
        System.out.println(("8888888888888888888888888888888888888888888888"));
        vehicleService.saveOrUpdate(vehicle);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        vehicleService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        vehicleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(vehicleService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(vehicleService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("vehicle_id");
        return Result.success(vehicleService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
