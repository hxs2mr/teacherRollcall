package com.gykj.rollcall.entity;

/**
 * desc   : 物品借用Item实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/49:57
 * version: 1.0
 */
public class BorrowEntity {

    private String name;
    private String room;
    private String date;
    private String items;
    private String avater;
    private String code;
    private int type;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
