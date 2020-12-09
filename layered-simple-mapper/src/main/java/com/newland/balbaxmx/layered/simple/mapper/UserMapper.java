package com.newland.balbaxmx.layered.simple.mapper;

import com.newland.balbaxmx.layered.simple.module.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;


/**
 * @Author: zhangyh
 * @ClassName: StyUserDao
 * @Date: 2020/1/6 16:57
 * @Operation:
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectUser();
}
