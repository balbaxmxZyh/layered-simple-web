package com.newland.balbaxmx.layered.simple.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

/**
 * @Author: zhangyh
 * @ClassName: RandomIDUtil
 * @Date: 2020/5/12 15:56
 * @Operation:
 * @Description: 随机ID工具类
 */
public class RandomIDUtil {
    private static Logger logger = LoggerFactory.getLogger(RandomIDUtil.class);
    public static Random random = new Random();

    /**
     * 随机生成long id
     * @return
     */
    public static long nextLong() {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (random.nextLong() << 1) >>> 1;
            val = bits % (long)(Long.MAX_VALUE/100000000);
        } while (bits-val+(Long.MAX_VALUE-1) < 0L);
        return val;
    }

    /**
     * 随机生成32位ID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
