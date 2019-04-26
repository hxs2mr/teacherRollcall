package com.gykj.mvvmlibrary.entity;

/**
 * desc   : 基类返回实体
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2414:25
 * version: 1.0
 */
public class BaseEntity<T> {

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk(){
        return code == 0?true:false;
    }
}
