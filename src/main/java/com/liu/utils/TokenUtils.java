package com.liu.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.liu.entity.User;
import com.liu.service.IUserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

public class TokenUtils {

    private static IUserService staticuserService;

    @Resource
    private IUserService userService;

    @PostConstruct
    public void setUserService() {
        staticuserService = userService;
    }
    //生成token
    public static String getToken(String userId, String sign){
        return JWT.create().withAudience(userId)   //将userId保存到token里 作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  //两小时后过期
                .sign(Algorithm.HMAC256(sign)); //以password作为token的秘钥
    }
    //获取当前登录用户信息
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticuserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
