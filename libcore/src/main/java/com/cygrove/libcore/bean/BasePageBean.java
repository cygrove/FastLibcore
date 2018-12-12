package com.cygrove.libcore.bean;

import java.util.List;

/**
 * Created by cygrove on 2018-11-14.
 */

public class BasePageBean<T> {
    private int pageNum;
    private int pageSize;
    private List<T> list;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}