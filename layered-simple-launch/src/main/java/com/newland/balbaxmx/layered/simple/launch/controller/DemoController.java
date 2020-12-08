package com.newland.balbaxmx.layered.simple.launch.controller;

import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/demo/index")
    public String index(){
        return "succeed";
    }
}
