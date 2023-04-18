package com.liu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liu.controller.dto.UserPasswordDTO;
import com.liu.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    int updateUserPassword(UserPasswordDTO userPasswordDTO);

    Page findPage(Page<User> page, @Param("username") String username,@Param("email") String email,@Param("address") String address);
}
