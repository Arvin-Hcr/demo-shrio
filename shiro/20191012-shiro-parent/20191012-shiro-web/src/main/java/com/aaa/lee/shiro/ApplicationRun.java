package com.aaa.lee.shiro;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/10/12 9:28
 * @Description
 **/
@SpringBootApplication
@MapperScan("com.aaa.lee.shiro.mapper")
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class, args);
    }

}
