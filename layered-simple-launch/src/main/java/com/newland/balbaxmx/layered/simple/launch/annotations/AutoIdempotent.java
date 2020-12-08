package com.newland.balbaxmx.layered.simple.launch.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: zhangyh
 * @ClassName: AutoIdempotent
 * @Date: 2020/12/8 11:13
 * @Operation:
 * @Description: 该注解作用在方法上，接口请求冥等性
 * 防止重复提交
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {
}
