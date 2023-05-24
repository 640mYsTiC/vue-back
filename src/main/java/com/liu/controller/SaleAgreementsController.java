package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.mapper.SaleAgreementsMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.liu.service.ISaleAgreementsService;
import com.liu.entity.SaleAgreements;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/saleAgreements")
public class SaleAgreementsController {

    @Resource
    private ISaleAgreementsService saleAgreementService;

    @Resource
    private SaleAgreementsMapper saleAgreementsMapper;

    @PostMapping
    public Result save(@RequestBody SaleAgreements saleAgreement) {
        saleAgreementService.saveOrUpdate(saleAgreement);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        saleAgreementService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        saleAgreementService.removeByIds(ids);
        return Result.success();
    }
    @GetMapping("/clients")
    public Result getClients(){
        return Result.success(saleAgreementsMapper.getClients());
    }
    @GetMapping
    public Result findAll() {
        return Result.success(saleAgreementService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(saleAgreementService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<SaleAgreements> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(saleAgreementService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
