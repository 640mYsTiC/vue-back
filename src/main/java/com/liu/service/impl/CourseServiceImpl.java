package com.liu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.entity.Course;
import com.liu.mapper.CourseMapper;
import com.liu.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘小白
 * @since 2023-04-17
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    private CourseMapper courseMapper;
    @Override
    public Page<Course> findPage(Page<Course> page, String name) {
        System.out.println("course查询成功");
        return courseMapper.findPage(page, name);
    }

    @Transactional
    @Override
    public void setStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.deleteStudentCourse(courseId, studentId);
        courseMapper.setStudentCourse(courseId, studentId);
    }
}
