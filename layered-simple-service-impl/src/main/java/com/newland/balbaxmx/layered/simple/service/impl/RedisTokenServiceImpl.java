package com.newland.balbaxmx.layered.simple.service.impl;

import com.newland.balbaxmx.layered.simple.exception.BussinessException;
import com.newland.balbaxmx.layered.simple.redis.util.RedisUtil;
import com.newland.balbaxmx.layered.simple.service.TokenService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author: zhangyh
 * @ClassName: RedisTokenServiceImpl
 * @Date: 2020/12/8 11:41
 * @Operation:
 * @Description: redis 生成、校验token
 *
 *
 */
@Service
public class RedisTokenServiceImpl implements TokenService {
    private static  Logger logger = LoggerFactory.getLogger(RedisTokenServiceImpl.class);

    private final String TOKEN_PRE = "form:request:";

    private final long TOKEN_TIME = 20000L;

    private final String TOKEN_NAME = "form-request-token";

    @Autowired
    private RedisUtil redisUtil;


    /**
     *  从redis获取token
     * @return
     */
    @Override
    public String createToken() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        StringBuilder builder = new StringBuilder();
        try {
            builder.append(TOKEN_PRE).append(uuid);
            boolean b = redisUtil.setEx(builder.toString(),builder.toString(),TOKEN_TIME);
            if(b){
                return builder.toString();
            }
        }catch (Exception e){
            logger.error("申请token失败e={}",e);
        }
        return null;
    }

    /**
     * 验证redis token是否有效
     * @param request
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request)throws Exception {
        String token = request.getHeader(TOKEN_NAME);
        if(StringUtils.isEmpty(token)){
            throw new BussinessException("400001","请求头,token不存在");
        }
        if(!redisUtil.exists(token)){
            throw new BussinessException("400002","无效toke或者失效token");
        }
        boolean remove = redisUtil.remove(token);
        if (!remove) {
            throw new BussinessException("400003","token清除失败");
        }
        return true;
    }
}
