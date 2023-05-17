package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.mapper.OutMaterialmanageMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liu.service.IOutMaterialmanageService;
import com.liu.entity.OutMaterialmanage;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-17
 */
@RestController
@RequestMapping("/outMaterialmanage")
public class OutMaterialmanageController {

    @Resource
    private IOutMaterialmanageService outMaterialmanageService;

    @Resource
    private OutMaterialmanageMapper outMaterialmanageMapper;

    @PostMapping
    public Result save(@RequestBody OutMaterialmanage outMaterialmanage) {
        outMaterialmanageService.saveOrUpdate(outMaterialmanage);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        outMaterialmanageService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        outMaterialmanageService.removeByIds(ids);
        return Result.success();
    }
    @GetMapping("/productMaterial")
    public Result getProduct(){
        List<String> tool = outMaterialmanageMapper.getProduct();
        Set<String> product = new HashSet<>(tool);
        tool.clear();
        tool.addAll(product);
        return Result.success(tool);
    }
    @GetMapping("/client/{material}")
    public Result getClient(@PathVariable String material){
        return Result.success(outMaterialmanageMapper.getClient(material));
    }
    @GetMapping
    public Result findAll() {
        return Result.success(outMaterialmanageService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(outMaterialmanageService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<OutMaterialmanage> queryWrapper = new QueryWrapper<>();
        return Result.success(outMaterialmanageService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
