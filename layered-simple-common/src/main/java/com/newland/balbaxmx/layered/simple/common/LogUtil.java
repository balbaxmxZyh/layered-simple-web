package com.newland.balbaxmx.layered.simple.common;

import java.lang.management.ManagementFactory;

/**
 * @Author: zhangyh
 * @ClassName: LogUtil
 * @Date: 2020/5/11 15:35
 * @Operation:
 * @Description: log4j2 工具类
 */
public class LogUtil {
    /**
     * 日志配置信息
     */
    public static void setLogFilePath() {
        String PID = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.setProperty("LOG_PID",PID);
    }
}
