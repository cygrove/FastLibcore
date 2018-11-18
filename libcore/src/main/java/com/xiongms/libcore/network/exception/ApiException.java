package com.xiongms.libcore.network.exception;


import com.google.gson.annotations.SerializedName;

import java.io.IOException;

/**
 * @author cygrove
 * @time 2018-11-14 11:46
 */
public class ApiException extends IOException {

    private int code;
    private String message;
    @SerializedName("code")
    private String strCode;

    public int getCode() {
        return code;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ApiException(String msg) {
        super(msg);
        this.message = msg;
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public ApiException(String message, String strCode) {
        super(message);
        this.message = message;
        this.strCode = strCode;
    }
}