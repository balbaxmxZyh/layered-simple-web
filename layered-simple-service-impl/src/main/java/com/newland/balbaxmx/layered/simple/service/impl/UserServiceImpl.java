package com.newland.balbaxmx.layered.simple.service.impl;

import com.newland.balbaxmx.layered.simple.mapper.UserMapper;
import com.newland.balbaxmx.layered.simple.module.User;
import com.newland.balbaxmx.layered.simple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhangyh
 * @ClassName: UserServiceImpl
 * @Date: 2020/1/14 20:24
 * @Operation:
 * @Description: ${description}
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> selectAll() {
        return userMapper.selectUser();
    }
}
