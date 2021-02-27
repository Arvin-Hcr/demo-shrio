package com.arvin.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

/**
 * @ClassName: ShiroConfig
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/17
 * @Since version-1.0
 **/
@SpringBootApplication
public class ShiroConfig {
    /**
     * @description: 用来管理shiro的Bean的生命周期
     * @param null 1
     * @return 
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        LifecycleBeanPostProcessor postProcessor =new LifecycleBeanPostProcessor();
        return postProcessor;
    }

   /**
    * @description: 加密规则以及加密次数和加密方式
    * @param setHashAlgorithmName() 加密规则为MD5
    *         setHashIterations() 加密次数
    *         setStoredCredentialsHexEncoded 当shiro验证密码的时候开启加密
    * @return
    */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher =new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }
    /**
     * @description: todo
     * @param null 1
     * @return 
     */
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();

    }
    
}
