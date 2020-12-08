package com.newland.balbaxmx.layered.simple.launch.controller;

import com.newland.balbaxmx.layered.simple.common.resp.ResMessage;
import com.newland.balbaxmx.layered.simple.launch.annotations.AutoIdempotent;
import com.newland.balbaxmx.layered.simple.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhangyh
 * @ClassName: DemoController
 * @Date: 2020/5/13 15:20
 * @Operation:
 * @Description: ${description}
 */
@RestController
public class DemoController {

    @Autowired
    private TokenService tokenService;


    @GetMapping("/demo/index")
    public String index(){
        return "succeed";
    }


    @GetMapping("/ksb/form")
    public String getToken(){
        return tokenService.createToken();
    }

    @AutoIdempotent
    @PostMapping("/ksb/form")
    public ResMessage ksb(){
        return new ResMessage().ok("接口幂等性测试") ;
    }




}
