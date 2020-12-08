package com.newland.balbaxmx.layered.simple.launch;

import com.newland.balbaxmx.layered.simple.common.LogUtil;
import com.newland.balbaxmx.layered.simple.common.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.newland.balbaxmx")
public class LayeredSimpleLaunchApplication {

    public static void main(String[] args) {
        LogUtil.setLogFilePath();
        ApplicationContext context = SpringApplication.run(LayeredSimpleLaunchApplication.class, args);
        SpringBeanUtil.setApplicationContext(context);
    }

}
