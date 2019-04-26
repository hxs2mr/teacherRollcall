package com.gykj.rollcall.entity;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:签到记录实体类
 * date   : 2019/3/149:48
 * version: 1.0
 */
public class RecordItemBean  {
    String starttime;
    String recordtime;
    int type;

    public RecordItemBean(String starttime, String recordtime, int type) {
        this.starttime = starttime;
        this.recordtime = recordtime;
        this.type = type;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
