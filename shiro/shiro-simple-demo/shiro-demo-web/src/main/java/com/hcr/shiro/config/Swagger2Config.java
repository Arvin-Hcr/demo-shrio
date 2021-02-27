package com.hcr.shiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * User: cheng
 * Date: 2020/3/10
 * Time: 20:16
 * Description:
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                //描述该网站的信息
                .apiInfo(apiInfo())
                //查询对外所需要提供的接口都是什么(consumer项目的controller)s
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hcr.shiro.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("shiro-demo-项目 服务接口")
                .description("提供了shiro中测试的接口信息")
                .contact(new Contact("Arvin","http://www.baidu.com","h.chaoran@qq.com"))
                .version("1.0 beta")
                .build();
    }

}