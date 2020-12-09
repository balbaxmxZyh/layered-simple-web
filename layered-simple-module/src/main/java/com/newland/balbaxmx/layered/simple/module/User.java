package com.newland.balbaxmx.layered.simple.module;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "BLX_USER")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     *用户id
     */
    @Id
    @Column(name = "USER_ID")
    private String userId;
    /**
     *
     *用户名称
     */
    @Column(name = "USER_NAME")
    private String userName;
    /**
     *
     *用户账户
     */
    @Column(name = "USER_ACCOUNT")
    private String userAccount;
    /**
     *
     *用户密码
     */
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    /**
     *
     *联系方式
     */
    @Column(name = "USER_PHONE")
    private String userPhone;
    /**
     *
     *状态
     */
    @Column(name = "USER_STATUS")
    private String userStatus;
    /**
     *
     *备注
     */
    @Column(name = "REMARK")
    private String remark;
    /**
     *
     *操作人姓名
     */
    @Column(name = "OPERATION_NAME")
    private String operationName;
    /**
     *
     *操作人Id
     */
    @Column(name = "OPERATION_ID")
    private String operationId;
    /**
     *
     *操作时间
     */
    @Column(name = "OPERATION_TIME")
    private Date operationTime;
    /**
     *
     *有效期开始时间
     */
    @Column(name = "STAR_TIME")
    private Date starTime;
    /**
     *
     *有效期结束时间
     */
    @Column(name = "END_TIME")
    private Date endTime;

}