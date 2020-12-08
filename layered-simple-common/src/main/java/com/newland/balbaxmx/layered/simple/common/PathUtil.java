package com.newland.balbaxmx.layered.simple.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Author: zhangyh
 * @ClassName: com.newland.balbaxmx.layered.simple.common.PathUtil
 * @Date: 2020/5/12 15:27
 * @Operation:
 * @Description: 程序路径设置
 */
public class PathUtil {
    private static Logger logger = LoggerFactory.getLogger(PathUtil.class);
    /**
     * 获取程序安装路径
     * @return
     */
    public static String getUserDir(){
        String path = System.getProperty("user.dir");
        return path;
    }

    /**
     * 获取当前系统的路径分隔符 == File.separator
     * @return
     */
    public static String getSeparator(){
        return File.separator;
    }

    /**
     * 获取操作系统的名称
     * @return
     */
    public static String getOsName(){
        return System.getProperty("os.name");
    }


    /**
     * 获取classes目录绝对路径
     * @return
     */
    public static String getClassesPath(){
        return PathUtil.class.getClassLoader().getResource("").getPath();
    }

    /**
     * 获取config路径
     * @param configName config文件夹名称（相对于程序安装安装路径）
     * @return
     */
    public static String getConfigPath(String configName){
        String binPath = getUserDir();
        return binPath+getSeparator()+".."+getSeparator()+configName;
    }
}
