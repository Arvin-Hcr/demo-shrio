package com.hcr.shiro.service;

import com.hcr.shiro.mapper.PermissionMapper;
import com.hcr.shiro.mapper.RoleMapper;
import com.hcr.shiro.mapper.UserMapper;
import com.hcr.shiro.model.Permission;
import com.hcr.shiro.model.Role;
import com.hcr.shiro.model.User;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServicde
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/23
 * @Since version-1.0
 **/
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    public Map<String, Object> selectUserByUsername(String username){
        Map<String, Object> resultMap = new HashMap<>();
        User user = userMapper.selectUserByUsername(username);
        if (null == user){
            resultMap.put("code","400");
            resultMap.put("msg","查询的用户信息不存在");
        }else {
            resultMap.put("code","200");
            resultMap.put("data",user);
        }
        return resultMap;
    }

    /**
     * 通过用户名查询角色信息
     * @param username
     * @return
     */
    //表示当前 Subject 需要角色 admin和user
    //@RequiresRoles(value = { "admin", "user" }, logical = Logical.AND)
    public Map<String, Object> selectRoleByUser(String username){
        Map<String, Object> resultMap = new HashMap<>();
        List<Role> roleList = roleMapper.selectRoleByUser(username);
        System.out.println("角色信息：" + roleList);
        if (roleList.size()>0){
            //有角色
            resultMap.put("code","200");
            resultMap.put("data",roleList);
        }else {
            resultMap.put("code","404");
            resultMap.put("msg","当前登录用户没有角色");
        }
        return resultMap;
    }

    /**
     * 通过用户名查询所有权限名称
     * @param userName
     * @return
     */
    public Map<String, Object> selectPermissionByUserName(String userName){
        Map<String,Object> resuleMap = new HashMap<>();
        List<Permission> permissionList = permissionMapper.selectPermissionByUserName(userName);
        System.out.println("角色权限:" + permissionList);
        if (permissionList.size()>0){
            //说明用户的角色拥有权限
            resuleMap.put("code","200");
            resuleMap.put("data",permissionList);
        }else {
            resuleMap.put("code","404");
            resuleMap.put("msg","当前用户所拥有的角色没有权限");
        }
        return resuleMap;
    }
}
