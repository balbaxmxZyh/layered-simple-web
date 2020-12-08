package com.newland.balbaxmx.layered.simple.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: zhangyh
 * @ClassName: TokenService
 * @Date: 2020/12/8 11:21
 * @Operation:
 * @Description:
 *
 * token放入请求头
 * 每个token只能用一次请求，防止重复提交
 *
 */
public interface TokenService {

    /**
     * 获取token
     * @return
     */
    String createToken();

    /**
     * 校验token是否有效
     *
     * 如果无效直接抛出，统一异常处理提示
     * @param request
     * @return
     * @throws Exception
     */
    boolean checkToken(HttpServletRequest request) throws Exception;
}
