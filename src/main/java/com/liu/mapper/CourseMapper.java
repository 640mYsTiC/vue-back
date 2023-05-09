package com.liu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> page, @Param("name") String name);

    void deleteStudentCourse(@Param("courseId")Integer courseId, @Param("studentId") Integer studentId);

    void setStudentCourse(@Param("courseId")Integer courseId, @Param("studentId") Integer studentId);

}
