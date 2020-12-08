package com.newland.balbaxmx.layered.simple.common.resp;


import java.io.Serializable;

/**
 * @Auther: chenjie
 * @Date: 2019/3/24 10:02
 * @Description:
 */
public class ResData<T> implements Serializable {
    private static final long serialVersionUID = 3863559087276427570L;

    private String code;
    private String message;
    private T data;

    public ResData(){}

    public ResData(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResData(String code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResData(String code) {
        this.code = code;
    }

    public ResData(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResData ok(T data){
        this.data = data;
        this.message = "操作成功";
        this.code = "200";
        return this;
    }

    public ResData ok(T data , String message){
        this.data = data;
        this.message = message;
        this.code = "200";

        return this;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
