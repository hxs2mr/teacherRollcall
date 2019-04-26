package com.gykj.rollcall.entity;

/**
 * desc   : 门禁考勤实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/169:48
 * version: 1.0
 */
public class DoorEntity {

    private String avater;
    private String name;
    private String room;
    private String grade;
    private String code;
    private String date;
    private int type;

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
