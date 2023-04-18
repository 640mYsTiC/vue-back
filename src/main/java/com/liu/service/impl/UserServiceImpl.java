package com.liu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liu.common.Constants;
import com.liu.controller.dto.UserDTO;
import com.liu.controller.dto.UserPasswordDTO;
import com.liu.entity.Menu;
import com.liu.exception.ServiceException;
import com.liu.mapper.RoleMapper;
import com.liu.mapper.RoleMenuMapper;
import com.liu.service.IMenuService;
import com.liu.service.IUserService;
import com.liu.entity.User;
import com.liu.mapper.UserMapper;
import com.liu.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘小白
 * @since 2023-03-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log log = Log.get();

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Resource
    private UserMapper userMapper;
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            //获取角色
            String role = one.getRole();
            System.out.println("这是role"+role);
            //设置用户菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("这是+++"+roleMenus);
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }
    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null){
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        }
        else {
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return null;
    }

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); //从数据库查询是否有相同账号
        }catch (Exception e){
            log.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }

    private List<Menu> getRoleMenus(String roleName) {
        Integer roleId = roleMapper.selectByName(roleName);
        System.out.println("这是roleId"+roleId);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
        System.out.println("这是ids"+menuIds);
        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        int update = userMapper.updateUserPassword(userPasswordDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_600, "密码错误");
        }
    }

    @Override
    public Page findPage(Page<User> page, String username, String email, String address) {
        return userMapper.findPage(page, username, email, address);

    }
}
