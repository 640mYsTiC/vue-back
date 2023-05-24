package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.mapper.SaleAgreementsMapper;
import com.liu.mapper.SaleOrderMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.liu.service.ISaleOrderService;
import com.liu.entity.SaleOrder;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-23
 */
@RestController
@RequestMapping("/saleOrder")
public class SaleOrderController {

    @Resource
    private ISaleOrderService saleOrderService;

    @Resource
    private SaleOrderMapper saleOrderMapper;

    @PostMapping
    public Result save(@RequestBody SaleOrder saleOrder) {
        saleOrderService.saveOrUpdate(saleOrder);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        saleOrderService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        saleOrderService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/agreementCodes")
    public Result getAgreementCodes(){
        return Result.success(saleOrderMapper.getagreementCodes());
    }

    @GetMapping("/clientname/{agreementsCode}")
    public Result getAgreementCodes(@PathVariable String agreementsCode){
        return Result.success(saleOrderMapper.getClientname(agreementsCode));
    }
    @GetMapping
    public Result findAll() {
        return Result.success(saleOrderService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(saleOrderService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<SaleOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(saleOrderService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
