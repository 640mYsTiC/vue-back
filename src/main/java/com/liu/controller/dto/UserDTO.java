package com.liu.controller.dto;

import lombok.Data;
//接收前端登录数据
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
}
