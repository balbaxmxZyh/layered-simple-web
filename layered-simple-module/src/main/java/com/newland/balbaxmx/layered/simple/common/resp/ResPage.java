package com.newland.balbaxmx.layered.simple.common.resp;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: chenjie
 * @Date: 2019/3/24 11:53
 * @Description:返回分页列表信息
 */
public class ResPage<T> implements Serializable {
    private static final long serialVersionUID = 3863559687292427577L;
    private int pageNum;
    private int pageSize;
    private long total;
    private int pages;
    private List<T> list;
    private String code;
    private String message;

    public ResPage(int pageNum, int pageSize, long total, int pages, List<T> list, String code, String message) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.list = list;
        this.code = code;
        this.message = message;
    }

    public ResPage() {}

    public ResPage<T> ok(int pageNum, int pageSize, long total, int pages, List<T> list,String message){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.pages = pages;
        this.list = list;
        this.code = "200";
        this.message = message;
        return this;
    }

    public ResPage<T> ok(int pageNum, int pageSize, long total, int pages, List<T> list){
        this.message = "列表信息返回成功";
        return this.ok(pageNum , pageSize , total , pages , list , message);
    }

    public ResPage<T> ok(PageInfo pageInfo , String message){
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.list = pageInfo.getList();
        this.code = "200";
        this.message = message;
        return this;
    }

    public ResPage<T> ok(PageInfo pageInfo){
        return this.ok(pageInfo , "列表信息返回成功");
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotal() {
        return total;
    }

    public int getPages() {
        return pages;
    }

    public List<T> getList() {
        return list;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
