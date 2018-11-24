package com.xiongms.libcore.bean;

/**
 * Created by cygrove on 2018/11/15.
 */

public class BaseBean<T> extends BaseEntity {
    private T body;
    private String code;
    private String message;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}