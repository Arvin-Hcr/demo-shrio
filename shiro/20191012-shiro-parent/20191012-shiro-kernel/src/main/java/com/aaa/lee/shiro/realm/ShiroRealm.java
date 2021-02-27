package com.aaa.lee.shiro.realm;

import com.aaa.lee.shiro.model.User;
import com.aaa.lee.shiro.service.UserService;
import org.apache.ibatis.ognl.Ognl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/10/12 11:18
 * @Description
 **/
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @throws
     * @author Seven Lee
     * @description 认证方法
     * @date 2019/10/12
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.获取用户名
        String username = (String) authenticationToken.getPrincipal();
        // 2.从数据库中查询该用户是否存在，如果不存在直接抛出异常
        Map<String, Object> resultMap = userService.selectUserByUsername(username);
        if ("400".equals((String) resultMap.get("code"))) {
            // 说明数据库中没有数据
            throw new UnknownAccountException("用户不存在");
        }
        // 3.判断密码是否正确(需要创建SimpleAuthenticationInfo对象)
        /**
         * 第一个参数:用户名/用户对象(从页面上获取的username，还有一个是从数据库中查询出来的用户对象)
         *              要求必须要传从数据库中查询出来的对象
         * 第二个参数:密码
         *          必须是从数据库中查询出的密码
         *          shiro已经获取到从页面上传递的密码了，需要和数据库中密码进行匹配
         * 第三个参数:盐值
         *      参数类型为ByteSource
         * 第四个参数:传递自己通过class.forName获取类的对象信息
         */
        User user = (User) resultMap.get("data");
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        // 4.返回SimpleAuthenticationInfo对象
        return info;
    }

    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @throws
     * @author Seven Lee
     * @description 授权方法
     * @date 2019/10/12
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1.获取用户名/用户信息
        // 根据认证方法的第一个参数决定
        // 如果在认证方法的第一个参数传递的是user对象getPrimaryPrincipal()获取的就是对象，如果传的是用户名获取的就是用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 2.通过用户名查询角色信息
        Map<String, Object> resultMap = userService.selectRoleByUser(username);
        //3.创建SimpleAutorizationInfo对象，不要初始化（授权）
        //不需要传值，点进去有默认无参构造方法
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if ("200".equals((String)resultMap.get("code")) && null!= resultMap.get("data")){
            //该用户有角色
            info.addRoles((List<String>)resultMap.get("data"));
        }
        //3.通过用户名查询权限信息
        Map<String,Object> resultMap2 = userService.selectPermissionByUserName(username);
        if ("200".equals((String)resultMap2.get("code")) && null!= resultMap2.get("data")){
            info.addStringPermissions((List<String>)resultMap2.get("data"));
        }
        //4.返回SimpleAutorizationInfo对象
        /**
         * 用户和角色是多对多的关系
         *
         */
        return info;
    }
   /* public static void main(String[] args) {
        int hashIterations = 10000;//加密的次数
        Object salt = null;//盐值
        Object credentials = "123456";//密码
        String hashAlgorithmName = "MD5";//加密方式
        Object simpleHash = new SimpleHash(hashAlgorithmName, credentials,
                salt, hashIterations);
        System.out.println("加密后的值----->" + simpleHash);
    }

*/
}
