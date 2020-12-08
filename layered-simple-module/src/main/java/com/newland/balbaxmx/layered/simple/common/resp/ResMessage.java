package com.newland.balbaxmx.layered.simple.common.resp;


import java.io.Serializable;

/**
 * @Auther: chenjie
 * @Date: 2019/3/24 13:52
 * @Description:
 */
public class ResMessage implements Serializable {
    private static final long serialVersionUID = 3863559087266422570L;
    private String code;
    private String message;

    public ResMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResMessage(){}

    public ResMessage ok(String message){
        this.code = "200";
        this.message = message;
        return this;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
