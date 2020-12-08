package com.newland.balbaxmx.layered.simple.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @Author: zhangyh
 * @ClassName: SpringBeanUtil
 * @Date: 2020/3/20 9:20
 * @Operation:
 * @Description: ${description}
 */
public class SpringBeanUtil {

    private static Logger logger = LoggerFactory.getLogger(SpringBeanUtil.class);
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }
    /**
     * 获取applicationContext
     *
     * @return
     *      applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     *          bean name
     * @return
     *          bean
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz
     *          class
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     *          bean name
     * @param clazz
     *          class
     * @param <T>
     * @return
     *          bean
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
