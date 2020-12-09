package com.newland.balbaxmx.layered.simple.common;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: zhangyh
 * @ClassName: OrikaBeanUtils
 * @Date: 2020/12/9 10:37
 * @Operation:
 * @Description: 基于orika的bean转换工具
 *
 */
public class OrikaBeanUtils {

    private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    /**
     * 获取orika 的工厂类
     * @return
     */
    public static MapperFactory getMapperFactory(){
        return mapperFactory;
    }

    /**
     * 相同属性转换
     * @param source 源对象
     * @param target 目标对象类
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object source,Class<T> target) {
        mapperFactory.classMap(source.getClass(), target)
                .byDefault()
                .register();
        MapperFacade facade = mapperFactory.getMapperFacade();
        T t = facade.map(source, target);
        return t;
    }

    /**
     *
     * 相同属性转换
     * @param source 源对象
     * @param target 目标对象
     * @param fields 属性名映射 fieldNameA -> fieldNameB
     * @param <T>
     * @return
     */
    public static <T> T copyBean(Object source,Class<T> target,String...fields) throws Exception{
        if(fields.length % 2 != 0){
            throw new Exception("fileds 属性名映射必须是fieldNameA -> fieldNameB");
        }
        ClassMapBuilder classMapBuilder = mapperFactory.classMap(source.getClass(), target);
        for (int i = 0;i < fields.length; i+=2){
            classMapBuilder.field(fields[i],fields[i+1]);
        }
        classMapBuilder.byDefault()
                .register();
        MapperFacade facade = mapperFactory.getMapperFacade();
        T t = facade.map(source, target);
        return t;
    }

    /**
     * 集合类型转换
     * @param collection 对象集合
     * @param source 源类型
     * @param target 目标类型
     * @param isClear 复制完是否清空源集合
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(Collection collection,Class source, Class<T> target,boolean isClear){
        List list = new ArrayList();
        mapperFactory.classMap(source, target)
                .byDefault()
                .register();
        MapperFacade facade = mapperFactory.getMapperFacade();
        Iterator iterator =  collection.iterator();
        while (iterator.hasNext()){
            Object o = iterator.next();
            T t = facade.map(o, target);
            list.add(t);
        }
        if(isClear){
            collection.clear();
        }
        return list;
    }


    /**
     * 集合类型转换
     * @param collection 对象集合
     * @param source 源类型
     * @param target 目标类型
     * @param isClear 复制完是否清空源集合
     * @param <T>
     * @return
     */
    public static <T> List<T> copyList(Collection collection,Class source, Class<T> target,boolean isClear,
                                       String...fields)throws Exception{
        if(fields.length % 2 != 0){
            throw new Exception("fileds 属性名映射必须是fieldNameA -> fieldNameB");
        }
        List list = new ArrayList();
        ClassMapBuilder classMapBuilder = mapperFactory.classMap(source, target);
        for (int i = 0;i < fields.length; i+=2){
            classMapBuilder.field(fields[i],fields[i+1]);
        }
        classMapBuilder.byDefault()
                .register();
        MapperFacade facade = mapperFactory.getMapperFacade();
        Iterator iterator =  collection.iterator();
        while (iterator.hasNext()){
            Object o = iterator.next();
            T t = facade.map(o, target);
            list.add(t);
        }
        if(isClear){
            collection.clear();
        }
        return list;
    }
}
