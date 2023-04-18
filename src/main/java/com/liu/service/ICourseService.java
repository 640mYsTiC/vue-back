package com.liu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘小白
 * @since 2023-04-17
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> objectPage, String name);

    void setStudentCourse(Integer courseId, Integer studentId);
}
