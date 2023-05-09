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
// * @since 2023-05-01
// */
//@RestController
//@RequestMapping("/spplier")
//public class SpplierController11 {
//
//    @Resource
//    private ISpplierService spplierService;
//
//    @Resource
//    private SpplierMapper spplierMapper;
//
//    @PostMapping
//    public Result save(@RequestBody Spplier spplier) {
//        spplierService.saveOrUpdate(spplier);
//        return Result.success();
//    }
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        spplierService.removeById(id);
//        return Result.success();
//    }
//
//    @DeleteMapping("/del/batch")
//    public Result deleteBatch(@RequestBody List<Integer> ids) {
//        spplierService.removeByIds(ids);
//        return Result.success();
//    }
//
//    @GetMapping
//    public Result findAll() {
//        return Result.success(spplierService.list());
//    }
//
//    @GetMapping("/names")
//    public Result findAllNames() {
//        return Result.success(spplierMapper.getNames());
//    }
//
//    @GetMapping("/getContact/{name}")
//    public Result selectByName(@PathVariable String name){
//        return Result.success(spplierMapper.selectInfoByName(name));
//    }
//
//    @GetMapping("/{id}")
//    public Result findOne(@PathVariable Integer id) {
//        return Result.success(spplierService.getById(id));
//    }
//
//    @GetMapping("/page")
//    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//
//        QueryWrapper<Spplier> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("supplierId");
//        return Result.success(spplierService.page(new Page<>(pageNum, pageSize), queryWrapper));
//    }
//
//
//}
