package com.liu.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.entity.ProductTask;
import com.liu.entity.Vehicle;
import com.liu.mapper.VehicleMapper;
import com.liu.service.IProductTaskService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-06-04
 */
@RestController
@RequestMapping("/productTask")
public class ProductTaskController {

    @Resource
    private IProductTaskService productTaskService;
    @Resource
    VehicleMapper vehicleMapper;

    @PostMapping
    public Result save(@RequestBody ProductTask productTask) {
        productTaskService.saveOrUpdate(productTask);
        if(productTask.getDriver()!=null){
            System.out.println("****************************************");
            UpdateWrapper<Vehicle> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("plate_number",productTask.getCar());
            updateWrapper.set("driver",productTask.getDriver());
            updateWrapper.set("state","正在使用");
            vehicleMapper.update(null,updateWrapper);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        productTaskService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        productTaskService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(productTaskService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(productTaskService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<ProductTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(productTaskService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @ResponseBody
    @PostMapping("/search")
    public Result Search(@RequestBody String S1) {
        ProductTask productTask = JSON.parseObject(S1,ProductTask.class);
        System.out.println(productTask.toString());
        QueryWrapper<ProductTask> queryWrapper = new QueryWrapper<>();
        if(productTask.getTaskDate()!=null){
            queryWrapper.eq("task_date",productTask.getTaskDate());
        }
        if(productTask.getClientName()!=null){
            queryWrapper.eq("client_name",productTask.getClientName());
        }
        if(productTask.getProjectName()!=null){
            queryWrapper.eq("project_name",productTask.getProjectName());
        }
        if(productTask.getConcrete()!=null){
            queryWrapper.eq("concrete",productTask.getConcrete());
        }
        if(productTask.getWay()!=null){
            queryWrapper.eq("way",productTask.getWay());
        }
        if(productTask.getState()!=null){
            queryWrapper.eq("state",productTask.getState());
        }
        if(productTask.getLine()!=null){
            queryWrapper.eq("line",productTask.getLine());
        }
        if(productTask.getCar()!=null){
            queryWrapper.eq("car",productTask.getCar());
        }
        queryWrapper.orderByDesc("id");
        return Result.success(productTaskService.page(new Page<>(1, 10), queryWrapper));
    }

}
