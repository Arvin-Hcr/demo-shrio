package com.aaa.lee.shiro.service;

import com.aaa.lee.shiro.mapper.PermissionMapper;
import com.aaa.lee.shiro.mapper.RoleMapper;
import com.aaa.lee.shiro.mapper.UserMapper;
import com.aaa.lee.shiro.model.Role;
import com.aaa.lee.shiro.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/10/12 9:51
 * @Description
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
     * @param [username]
     * @return com.aaa.lee.shiro.model.User
     * @throws
     * @author Seven Lee
     * @description 通过用户名查询用户信息
     * 规定:
     * 所有和业务逻辑有关的代码全部写在service层，controller不能出现任何一行，controller只负责跳转，不负责处理业务逻辑
     * 以后的所有controller返回和service返回都必须要统一
     * @date 2019/10/12
     **/
    public Map<String, Object> selectUserByUsername(String username) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        User user = userMapper.selectUserByUsername(username);
        // null:防止空指针异常
        // 使用包装类型也可以防止空指针异常(Integer == 1)
        if (null == user) {
            // 数据库中没有数据
            resultMap.put("code", "400");
            resultMap.put("msg", "用户不存在");
        } else {
            resultMap.put("code", "200");
            resultMap.put("data", user);
        }
        return resultMap;
    }

    /**
     * 通过用户名查询所有的角色
     * @param userName
     * @return
     */
    public Map<String, Object> selectRoleByUser(String userName){
        Map<String,Object> resuleMap = new HashMap<>();
        List<String> roleList = roleMapper.selectPermissionByUserName(userName);
        if (roleList.size()>0){
            //有角色
            resuleMap.put("code","200");
            resuleMap.put("data",roleList);
        }else {
            resuleMap.put("code","404");
            resuleMap.put("msg","当前登录用户没有角色");
        }
        return resuleMap;
    }

    /**
     * 通过用户名查询所有权限名称
     * @param userName
     * @return
     */
    public Map<String, Object> selectPermissionByUserName(String userName){
        Map<String,Object> resuleMap = new HashMap<>();
        List<String> permissionList = permissionMapper.selcetPermissionByUserName(userName);
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
