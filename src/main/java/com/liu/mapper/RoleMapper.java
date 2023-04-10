package com.liu.mapper;

import com.liu.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘小白
 * @since 2023-04-06
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select id from sys_role where name = #{name}")
    Integer selectByName(@Param("name") String name);
}
