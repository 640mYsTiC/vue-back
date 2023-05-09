//package com.liu.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.liu.common.Result;
//import jakarta.annotation.Resource;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author 刘小白
// * @since 2023-05-04
// */
//@RestController
//@RequestMapping("/purchaseAgreements")
//public class PurchaseAgreementsController11 {
//
//    @Resource
//    private IPurchaseAgreementsService purchaseAgreementsService;
//
//    @PostMapping
//    public Result save(@RequestBody PurchaseAgreements purchaseAgreements) {
//        purchaseAgreementsService.saveOrUpdate(purchaseAgreements);
//        return Result.success();
//    }
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        purchaseAgreementsService.removeById(id);
//        return Result.success();
//    }
//
//    @DeleteMapping("/del/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids) {
//        purchaseAgreementsService.removeByIds(ids);
//        return Result.success();
//    }
//
//    @GetMapping
//    public Result findAll() {
//        return Result.success(purchaseAgreementsService.list());
//    }
//
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(purchaseAgreementsService.getById(id));
//    }
//
//    @GetMapping("/page")
//    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//
//        QueryWrapper<PurchaseAgreements> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
//        return Result.success(purchaseAgreementsService.page(new Page<>(pageNum, pageSize), queryWrapper));
//    }
//
//
//}
