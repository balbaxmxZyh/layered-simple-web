package com.newland.balbaxmx.layered.simple.launch.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * mybatis的配置信息
 *
 */
@Configuration
public class MyBatisConfig {

    /**
     *
     * 功能描述: 分页插件配置信息
     *
     * @param: 无
     * @return: 分页插件配置信息
     * @auther: chenjie
     * @date: 2019/3/11 3:18 PM
     */
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum","true");
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
