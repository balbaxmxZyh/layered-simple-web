package com.newland.balbaxmx.layered.simple.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

/**
 * @Author: zhangyh
 * @ClassName: OSUtil
 * @Date: 2020/5/12 16:25
 * @Operation:
 * @Description: 系统工具类
 */
public class OSUtil {
    private static Logger logger = LoggerFactory.getLogger(OSUtil.class);

    /**
     * 获取进程号
     * @return
     */
    public static String getPID(){
        String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        return PID;
    }

    /**
     * 判断进程是否存活
     * @param pid
     * @return
     */
    public static Boolean isRunning(String pid){
        try {
            Process process = Runtime.getRuntime().exec("ps aux  | awk '{print $2}' | "+pid);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String ls_1="";
            while ((ls_1=bufferedReader.readLine()) != null){
                if (ls_1.startsWith(pid)) {
                    return true;
                }
            }
            process.waitFor();
            process.destroy();
        } catch (Exception e) {
            logger.error("readHistory [{}]失败[{}]",pid,e);
        }
        return false;
    }
}
