package com.liu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘小白
 * @since 2023-04-17
 */
public interface CourseMapper extends BaseMapper<Course> {
    Page<Course> findPage(Page<Course> page, @Param("name") String name);
    void deleteStudentCourse(@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);

    void setStudentCourse(@Param("courseId") Integer courseId,@Param("studentId") Integer studentId);
}
