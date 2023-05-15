package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.mapper.InMaterialmanageMapper;
import com.liu.mapper.MAndSMapper;
import com.liu.mapper.SupplierMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.liu.service.IInMaterialmanageService;
import com.liu.entity.InMaterialmanage;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/inMaterialmanage")
public class InMaterialmanageController {

    @Resource
    private IInMaterialmanageService inMaterialmanageService;

    @Resource
    private InMaterialmanageMapper inMaterialmanageMapper;
    @Resource
    private MAndSMapper mAndSMapper;

    @PostMapping
    public Result save(@RequestBody InMaterialmanage inMaterialmanage) {
        inMaterialmanageService.saveOrUpdate(inMaterialmanage);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        inMaterialmanageService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        inMaterialmanageService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(inMaterialmanageService.list());
    }
    @GetMapping("/materials")
    public Result getMaterials(){
        return Result.success(inMaterialmanageMapper.getMaterials());
    }
    @GetMapping("/getAgreementDetail/{material}")
    public Result getAgreementDetail(@PathVariable String material){
        return Result.success(mAndSMapper.getAgreementDetail(material));
    }
    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(inMaterialmanageService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<InMaterialmanage> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(inMaterialmanageService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
