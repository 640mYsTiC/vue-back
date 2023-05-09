package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.mapper.SupplierMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.liu.service.ISupplierService;
import com.liu.entity.Supplier;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-08
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Resource
    private ISupplierService supplierService;
    @Resource
    private SupplierMapper supplierMapper;

    @PostMapping
    public Result save(@RequestBody Supplier supplier) {
        supplierService.saveOrUpdate(supplier);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        supplierService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        supplierService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(supplierService.list());
    }

    @GetMapping("/names")
    public Result findAllNames() {
        return Result.success(supplierMapper.getNames());
    }

    @GetMapping("/getContact/{name}")
    public Result selectByName(@PathVariable String name){
        return Result.success(supplierMapper.selectInfoByName(name));
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(supplierService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<Supplier> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("supplier_id");
        return Result.success(supplierService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
