package com.gykj.rollcall.entity;

/**
 * desc   : 点名列表实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/289:23
 * version: 1.0
 */
public class CallEntity {

    private String callName;
    private String callDate;
    private int callType;
    private boolean enable;

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


}
