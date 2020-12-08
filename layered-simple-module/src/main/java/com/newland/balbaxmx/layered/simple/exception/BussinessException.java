package com.newland.balbaxmx.layered.simple.exception;

/**
 * @Author: zhangyh
 * @ClassName: BussinessException
 * @Date: 2020/12/8 14:19
 * @Operation:
 * @Description:
 *
 * 请求提交token错误
 */
public class BussinessException extends Exception {
    private String code;
    private String msg;

    public BussinessException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public BussinessException(){
        this.code = "400001";
        this.msg = "请求token出错";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
