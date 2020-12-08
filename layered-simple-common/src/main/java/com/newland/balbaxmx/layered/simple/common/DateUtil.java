package com.newland.balbaxmx.layered.simple.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: zhangyh
 * @ClassName: DateUtil
 * @Date: 2020/5/12 15:48
 * @Operation:
 * @Description: 时间工具类
 */
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 获取当前时间与系统时间一致
     * @return
     */
    public static Date currentTime(){
        Long currentTime = System.currentTimeMillis();
        return new Date(currentTime);
    }


    /**
     * 格式化string为Date
     *
     * @param datestr
     * @return date
     */
    public static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(':') > 0) {
                fmtstr = "yyyy-MM-dd HH:mm:ss";
            } else {
                fmtstr = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
            return sdf.parse(datestr.replaceAll("/", "-").replaceAll("\\.", "-"));
        } catch (Exception e) {
            return null;
        }
    }

}
