package com.liu.controller.dto;

import com.liu.entity.Menu;
import lombok.Data;

import java.util.List;

//接收前端登录数据
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
