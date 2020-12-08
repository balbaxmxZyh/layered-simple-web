package com.newland.balbaxmx.layered.simple.launch.Interceptor;

import com.newland.balbaxmx.layered.simple.exception.BussinessException;
import com.newland.balbaxmx.layered.simple.launch.annotations.AutoIdempotent;
import com.newland.balbaxmx.layered.simple.service.TokenService;
import com.newland.balbaxmx.layered.simple.service.impl.RedisTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: zhangyh
 * @ClassName: AuthInterceptor
 * @Date: 2020/12/8 14:59
 * @Operation:
 * @Description:
 * 拦截器，拦截需要幂等性的请求
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private TokenService tokenService;


    /**
     *  拦截AutoIdempotent 注解的方法级别
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //不是方法级别的请求，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AutoIdempotent autoIdempotent = method.getAnnotation(AutoIdempotent.class);
        if(autoIdempotent != null){
           try {
               tokenService.checkToken(request);
           }catch (BussinessException e){
               throw e;
           }
        }
        return true;
    }
}
