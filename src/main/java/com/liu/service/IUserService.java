package com.liu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.controller.dto.UserDTO;
import com.liu.controller.dto.UserPasswordDTO;
import com.liu.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘小白
 * @since 2023-03-27
 */
public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    void updatePassword(UserPasswordDTO userPasswordDTO);

    Page findPage(Page<User> page, String username, String email, String address);
}
