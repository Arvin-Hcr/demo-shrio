package com.aaa.lee.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.aaa.lee.shiro.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/10/12 10:50
 * @Description
 **/
@SpringBootApplication
public class ShiroConfig {

    /**
     * @param []
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor
     * @throws
     * @author Seven Lee
     * @description 当mybatis去集成spring的时候，需要把mybatis交给spring进行托管
     * 把整个shiro的生命周期交给spring进行托管
     * @date 2019/10/12
     **/
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        LifecycleBeanPostProcessor processor = new LifecycleBeanPostProcessor();
        return processor;
    }


    /**
     * @param []
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     * @throws
     * @author Seven Lee
     * @description HashedCredentialsMatcher:
     * 加密的规则以及加密的方式和加密的次数
     * setHashAlgorithmName():指定了加密的方式
     * setHashIterations():加密的次数
     * setStoredCredentialsHexEncoded():当shiro验证密码的时候开启加密
     * @date 2019/10/12
     **/
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }


    /**
     * @param []
     * @return ShiroRealm
     * @throws
     * @author Seven Lee
     * @description 创建realm对象
     * @date 2019/10/12
     **/
    @Bean(name = "shiroRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        // realm有认证阶段:
        // 密码需要匹配(密码是不会进行加密匹配的)
        // html ---> user(username:zhangsan, password:123456)---> String类型的password转换为char[]数组["1","2"...]
        // 再进行匹配根据ascii进行匹配(A,a)
        // setCredentialsMatcher():密码的加密规则就会按照hashedCredentialsMatcher()进行加密
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        // 开发阶段用不到
        //shiroRealm.setCacheManager(ehCacheManager());
        return shiroRealm;
    }


    /**
     * @param []
     * @return org.apache.shiro.cache.ehcache.EhCacheManager
     * @throws
     * @author Seven Lee
     * @description 一般我喜欢使用redis集成shiro的缓存！
     * @date 2019/10/12
     **/
    @Bean(name = "ehCacheManager")
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager() {
        return new EhCacheManager();
    }


    /**
     * @param []
     * @return org.apache.shiro.web.mgt.DefaultWebSecurityManager
     * @throws
     * @author Seven Lee
     * @description 创建securityManager对象
     * @Bean: 相当于application.xml中的<bean       id   =   "   securityManager   "></bean>
     * @date 2019/10/12
     **/
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        //securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }


    /**
     * @param []
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @throws
     * @author Seven Lee
     * @description shiro的过滤器配置
     * @date 2019/10/12
     **/
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager());
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        /**
         * put("key", "value"):
         *      key:controller中@RequestMapping的路径
         *      value:shiro所提供的过滤规则
         * 1.logout:
         *      suject.logout()-->退出登录
         * 2.anon:
         *      匿名访问(shiro直接不拦截)
         *      html,js,css,pictures,ico:图片(logo)
         *  springboot的资源加载文件夹:
         *      public
         *      static
         *      resources
         *      META-INF/resources
         *  优先级:
         *      META-INF/resources > resources > static > public
         *
         *  3.authc:
         *      认证成功后才可以访问
         *  4.*和**的区别:
         *      *:com.aaa.lee.shiro只包含子路径(ApplicationRun.java)
         *      **:com.aaa.lee.shiro包含子文件夹以及子文件夹下的所有的文件/文件夹
         *  5.setLoginUrl：设置默认的登录地址
         *  6.setSuccessUrl:认证成功后跳转的地址
         *  7.setUnauthorizedUrl:没有授权的访问页面
         *  8./userInfo = user:认证成功以后才可以访问
         *  9./userInfo = role["admin"]:只有admin角色的用户可以访问
         *  10./userInfo = role["admin", "testAdmin"]:只要满足一个就可以访问
         *  permission
         *
         */
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        filter.setLoginUrl("/login");
        // filter.setSuccessUrl("/index");
        filter.setUnauthorizedUrl("/404");


        filter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return filter;
    }


    /**
     * @param []
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @throws
     * @author Seven Lee
     * @description 创建代理对象
     * @ConditionalOnMissingBean: 当springboot框架中没有这个bean的时候才会加载DefaultAdvisorAutoProxyCreator方法
     * spring框架是IOC：逆向生成对象(正向:User user = new User，逆向:<bean></bean>创建对象，
     * 使用的都是动态代理创建(java自带的，cglib))
     * 通过动态代理创建所有和shiro有关的对象
     * shiro交给spring进行托管
     * -->如果使用到shiro的情况下，需要创建(securotyManagemer, subject, session...)
     * -->就是通过spring的动态代理进行创建(需要声明@Bean)
     * DefaultAdvisorAutoProxyCreator:springboot自带
     * 当spring默认没有自带的DefaultAdvisorAutoProxyCreator的时候，
     * 才会加载自己项目中defaultAdvisorAutoProxyCreator()
     * @date 2019/10/12
     **/
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * @param []
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @throws
     * @author Seven Lee
     * @description 授权适配器
     * @date 2019/10/12
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager());
        return aASA;
    }


    /**
     * @param []
     * @return at.pollux.thymeleaf.shiro.dialect.ShiroDialect
     * @throws
     * @author Seven Lee
     * @description ShiroDialect:shiro的方言
     * 目的是在thymeleaf中使用shiro的标签
     * @date 2019/10/12
     **/
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
