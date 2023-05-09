package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import com.liu.mapper.BillSettlementMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.liu.service.IBillSettlementService;
import com.liu.entity.BillSettlement;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/billSettlement")
public class BillSettlementController {

    @Resource
    private IBillSettlementService billSettlementService;

    @Resource
    private BillSettlementMapper billSettlementMapper;

    @PostMapping
    public Result save(@RequestBody BillSettlement billSettlement) {
        billSettlementService.saveOrUpdate(billSettlement);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        billSettlementService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        billSettlementService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping("/agreementCode")
    public Result getAgreementCode(){
        return Result.success(billSettlementMapper.getCodes());
    }
    @GetMapping("/agreementDetail/{agreementCode}")
    public Result getAgreementDetail(@PathVariable String agreementCode){
        return Result.success(billSettlementMapper.getDetail(agreementCode));
    }
    @GetMapping
    public Result findAll() {
        return Result.success(billSettlementService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(billSettlementService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<BillSettlement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return Result.success(billSettlementService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


}
