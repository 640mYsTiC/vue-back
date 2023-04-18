package com.liu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.liu.service.ICourseService;
import com.liu.entity.Course;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 刘小白
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private ICourseService courseService;

    @PostMapping
    public Result save(@RequestBody Course course) {
        courseService.saveOrUpdate(course);
        return Result.success();
    }
    @PostMapping("/studentCourse/{courseId}/{studentId}")
    public Result studentCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.setStudentCourse(courseId, studentId);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        courseService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        courseService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(courseService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(courseService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {

        Page<Course> page = courseService.findPage(new Page<>(pageNum, pageSize), name);
        return Result.success(page);
    }


}
