package com.gykj.mvvmlibrary.entity;


/**
 * 作者： XS
 * 邮箱：1363826037@qq.com
 * 描述:MQ
 * 创建时间:  2018\6\27 0027 11:34
 */
public class MQRespose<T>{
    private String deviceId;
    private int devieceType;
    private String method;
    private String event;
    private String descript;
    private T data;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDevieceType() {
        return devieceType;
    }

    public void setDevieceType(int devieceType) {
        this.devieceType = devieceType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
