package com.newland.balbaxmx.layered.simple.common.resp;


import java.io.Serializable;
import java.util.List;

/**
 * @Auther: chenjie
 * @Date: 2019/3/24 12:18
 * @Description:
 */
public class ResList<T> implements Serializable {
    private static final long serialVersionUID = 3863659087276427570L;
    private String code;
    private String message;
    private List<T> dataList;

    public ResList(String code, String message, List<T> dataList) {
        this.code = code;
        this.message = message;
        this.dataList = dataList;
    }

    public ResList(){}

    public ResList ok(String message , List<T> list){
        this.code = "200";
        this.message = message;
        this.dataList = list;
        return this;
    }

    public ResList ok(List<T> list){
        return this.ok("列表信息返回成功" , list);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
