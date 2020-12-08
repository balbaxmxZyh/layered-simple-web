package com.newland.balbaxmx.layered.simple.launch.config;

import com.newland.balbaxmx.layered.simple.common.resp.ResData;
import com.newland.balbaxmx.layered.simple.exception.BussinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: chenjie
 * @Date: 2019/3/23 15:27
 * @Description:异常处理类
 */
@RestControllerAdvice
public class CustomExtHandler {
    private static Logger logger = LoggerFactory.getLogger(CustomExtHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResData<String> handleException(Exception e , HttpServletRequest request){
        logger.error("url {}, msg {}",request.getRequestURL(), e.getMessage());

        String code = "500";
        String message = "系统出现异常,请联系管理员";
        String data = e.getMessage();
        ResData<String> resData = new ResData<>("500",message,data);

        return resData;
    }


    @ExceptionHandler(value = BussinessException.class)
    public ResData<String> handleAuthorizationException(BussinessException e , HttpServletRequest request){
        logger.error("url {}, msg {}",request.getRequestURL(), e.getMsg());
        String data = e.getMessage();
        ResData<String> resData = new ResData<>(e.getCode(),e.getMsg(),data);

        return resData;
    }
}
