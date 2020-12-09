package com.newland.balbaxmx.layered.simple.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhangyh
 * @date 2020-01-06 16:04:00
 * @description 用户表
 */
@Setter
@Getter
@ToString
@Entity
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     *用户id
     */
    private String userId;
    /**
     *
     *用户名称
     */
    private String userName;
    /**
     *
     *用户账户
     */
    private String userAccount;
    /**
     *
     *联系方式
     */
    private String userPhone;
    /**
     *
     *状态
     */
    private String userStatus;
    /**
     *
     *备注
     */
    private String remark;
    /**
     *
     *操作人姓名
     */
    private String operationName;
    /**
     *
     *操作时间
     */
    private Date operationTime;
    /**
     *
     *有效期开始时间
     */
    private Date starTime;
    /**
     *
     *有效期结束时间
     */
    private Date endTime;

}