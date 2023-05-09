package com.liu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> page, String name);

    void setStudentCourse(Integer courseId, Integer studentId);
}
