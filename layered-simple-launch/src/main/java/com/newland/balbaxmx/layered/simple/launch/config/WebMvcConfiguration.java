package com.newland.balbaxmx.layered.simple.launch.config;

import com.newland.balbaxmx.layered.simple.launch.Interceptor.AuthInterceptor;
import com.newland.balbaxmx.layered.simple.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author: zhangyh
 * @ClassName: WebMvcConfiguration
 * @Date: 2020/12/8 14:33
 * @Operation:
 * @Description:
 * 配置拦截器
 *
 *
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private AuthInterceptor authInterceptor;


    /**
     * 设置拦截器拦截规则
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.authInterceptor)
                .addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    /**
     * 配置静态资源拦截
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }
}
