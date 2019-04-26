package com.gykj.rollcall.utils;

/**
 * **********************
 */

public class ApiException extends Exception {
    private int status;
    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.status = code;
    }

    public int getCode() {
        return status;
    }
    public void setCode(int code) {
        this.status = code;
    }
}
