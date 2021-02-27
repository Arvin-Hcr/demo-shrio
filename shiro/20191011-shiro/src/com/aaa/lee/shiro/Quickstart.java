package com.aaa.lee.shiro;/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {

        /**
         * 1.先创建SecurityManager对象
         *  realms:认证和授权
         *  user:用户
         *  role:角色
         *  permission:权限
         *  以上的配置都在ini文件中体现
         *  如果shiro集成的是spring，最终ini文件会替换为application.xml
         *  如果使用的springboot，最终ini会替换成配置类(java配置类)
         *  shiro中如果需要创建SecurityManager对象，必须要使用工厂模式
         */
        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        /**
         * 1.创建SecurityManager的工厂
         */
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        /**
         * 2.通过工厂生产SecurityManager实例
         *  SecurityManager:ehcache,session,subject....
         *  SecurityManager是为了创建Subject对象
         *  SecurityManager:是单例
         */
        SecurityManager securityManager = factory.getInstance();

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        /**
         * 3.把所生成的securityManager放入到SecurityUtils中，为了生成subject对象
         */
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // get the currently executing user:
        /**
         * 以下就是shiro的重点
         * 4.生成Subject对象
         */
        Subject currentUser = SecurityUtils.getSubject();

        /**
         * 5.测试session
         *      5.1.html页面<form></form>
         *      5.2.跳转到loginController
         *      5.3.当用户登录成功后需要把用户存入到session中
         *  shiro会自带的有session，不需要再使用HttpSession了
         *  其实shiro的session也是根据HttpSession做的封装
         *  通过subject对象获取session对象
         *
         *  session一般就是在当用户认证成功后会直接把用户信息存入到session中
         *
         */
        // Do some stuff with a Session (no need for a web or EJB container!!!)
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        int i = 1;
        if (value.equals("aValue")) {
            log.info("--->session测试:Retrieved the correct value! [" + value + "]");
            //return;
        }

        // let's login the current user so we can check against roles and permissions:
        /**
         * 6.认证
         *  currentUser-->subject
         *  subject.isAuthenticated():
         *      判断用户是否处于登录成功状态，返回值为Boolean类型
         *      true:
         *          认证成功
         *      false:
         *          认证失败
         */
        if (!currentUser.isAuthenticated()) {
            /**
             * 6.1.需要把用户名和密码存入到token中
             */
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            /**
             * 6.2.记住我
             *      setRememberMe(boolean)
             *      true:生效
             *      false:失效
             */
            // token.setRememberMe(true);
            try {
                /**
                 * 6.3.调用subject的login方法，传入token
                 *      该方法就是登录方法
                 */
                currentUser.login(token);
                // UnknownAccountException:找不到用户异常，也就是说用户名不存在
                /**
                 * shiro在认证阶段中所判断的流程:
                 *      先判断用户是否存在（select * from user where username = #{username}）
                 */
            } catch (UnknownAccountException uae) {
                log.info("测试用户名异常--->There is no user with username of " + token.getPrincipal());
                //return;
                // IncorrectCredentialsException:密码错误异常
            } catch (IncorrectCredentialsException ice) {
                log.info("测试密码错误异常--->Password for account " + token.getPrincipal() + " was incorrect!");
                return;
                // LockedAccountException:账号锁定异常
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // AuthenticationException:认证异常
            /**
             * UnknownAccountException-->继承了AccountException
             * IncorrectCredentialsException-->继承了CredentialsException-->继承了AuthenticationException
             * LockedAccountException-->继承了DisabledAccountException-->继承了AccountException-->继承了AuthenticationException
             */ catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        /**
         * currentUser.getPrincipal():
         *      获取的是username
         * 7.登录成功
         */
        //say who they are:于是我们就是可以说他们是：
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
        System.out.println("测试是否登录成功---->" + currentUser.isAuthenticated());

        /**
         * 8.测试角色
         *      user
         *          多对多
         *      role
         *          多对多
         *      permission
         *   currentUser.hasRole():当前登录的用户是否拥有该角色
         */
        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("-->测试是否拥有角色May the Schwartz be with you!");
            //return;
        } else {
            log.info("Hello, mere mortal.");
        }

        /**
         * 9.测试权限(是一个粗粒度的权限)
         * subject.isPermitted()
         *
         */
        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:fdjskhiq1786kjahkjhrkje")) {
            log.info("--->测试是否拥有权限You may use a lightsaber ring.  Use it wisely.");
            //return;
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
            //return;
        }

        /**
         * 细粒度的权限
         */
        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        /**
         * 10.退出系统
         *      suject.logout()退出系统
         */
        currentUser.logout();
        System.out.println(currentUser.isAuthenticated());

        /**
         * jdk自带的
         *      exit(123):程序正常退出
         *      jvm虚拟机中:
         *          如果直接main执行完毕，程序如果不调用exit()表示程序退出，JVM并不确定该方法是否还会继续执行
         *          JVM不会做垃圾回收(GC)--->90s之后--->JVM才会做垃圾回收
         *          一旦调用了exit()方法，表示已经运行完毕，JVM会直接垃圾回收
         */
        System.exit(0);
    }
}
