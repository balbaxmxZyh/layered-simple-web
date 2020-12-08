package com.newland.balbaxmx.layered.simple.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangyh
 * @ClassName: BeansUtil
 * @Date: 2020/5/12 17:13
 * @Operation:
 * @Description: 实体工具类
 */
public class BeansUtil {

    /**
     * 根据属性名 设置值
     * @param o
     * @param clazz
     * @param key
     * @param value
     */
    public void put(Object o,Class clazz,String key,Object value){
        if("".equals(key) || key == null){
            return ;
        }
        setFieldValueByName(o,clazz,key,value);
    }

    /**
     * 获取所有属性值
     * @return
     */
    public String[] getFiledName(){
        Field[] fields=this.getClass().getDeclaredFields();
        String[] fieldNames=new String[fields.length];
        for(int i=0;i<fields.length;i++){
            fieldNames[i]=fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     * @param fieldName
     * @return
     */
    public String getFieldValueByName(String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = this.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(this, new Object[] {});
            return value.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 设置属性值
     * @param source
     * @param clazz
     * @param fieldName
     * @param o
     */
    public void setFieldValueByName(Object source,Class clazz,String fieldName,Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "set" + firstLetter + fieldName.substring(1);
            Method method = clazz.getMethod(getter, o.getClass());
            method.invoke(source,o);
        } catch (Exception e) {
        }

    }

    /**
     * 转换为map
     * @return
     */
    public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<>();
        String[] fileds = getFiledName();
        for (String name :fileds){
            String value = getFieldValueByName(name);
            map.put(name,value);
        }
        return map;
    }
}
