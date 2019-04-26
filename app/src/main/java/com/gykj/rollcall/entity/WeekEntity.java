package com.gykj.rollcall.entity;

/**
 * desc   : 星期实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/299:32
 * version: 1.0
 */
public class WeekEntity {

    private int id;
    private String weekName;
    private boolean isCheck;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeekName() {
        return weekName;
    }

    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
