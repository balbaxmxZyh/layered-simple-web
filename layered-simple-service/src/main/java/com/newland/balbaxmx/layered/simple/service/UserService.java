package com.newland.balbaxmx.layered.simple.service;


import com.newland.balbaxmx.layered.simple.module.User;

import java.util.List;

/**
 * @Author: zhangyh
 * @ClassName: SecurityUserService
 * @Date: 2020/1/14 20:24
 * @Operation:
 * @Description: ${description}
 */
public interface UserService {

    List<User> selectAll();
}
