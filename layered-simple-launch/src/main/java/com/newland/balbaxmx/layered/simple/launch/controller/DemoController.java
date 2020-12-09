package com.newland.balbaxmx.layered.simple.launch.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.newland.balbaxmx.layered.simple.common.OrikaBeanUtils;
import com.newland.balbaxmx.layered.simple.common.resp.ResMessage;
import com.newland.balbaxmx.layered.simple.launch.annotations.AutoIdempotent;
import com.newland.balbaxmx.layered.simple.module.User;
import com.newland.balbaxmx.layered.simple.service.TokenService;
import com.newland.balbaxmx.layered.simple.service.UserService;
import com.newland.balbaxmx.layered.simple.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private UserService userService;


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


    @GetMapping("/list")
    public String list(){
        PageHelper.startPage(1,10);
        List<User> list = userService.selectAll();
        List<UserVo> vo = OrikaBeanUtils.copyList(list,User.class,UserVo.class,true);
        PageInfo<UserVo> pageInfo = new PageInfo<>(vo);
        System.out.println(pageInfo);
        return "succeed";
    }



}
