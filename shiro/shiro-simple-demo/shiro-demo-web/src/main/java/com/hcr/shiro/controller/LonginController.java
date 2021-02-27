package com.hcr.shiro.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: LonginController
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/23
 * @Since version-1.0
 **/
@Controller
//@Api(value = "shiro-login",tags = "shiro-demo-project")
public class LonginController {

    @RequestMapping("/login")
    @ApiOperation(value = "登录",notes = "登录接口")
    public String turnLonginPage(){
        return "login";
    }

    /**
     * shiro带参认证后经过controller跳转到根路径，通过（“/”）
     * @return
     */
    @RequestMapping("/")
    //@ApiOperation(value = "跳转",notes = "跳转接口")
    public String turnIndexPage(){
        return "index";
    }
}
