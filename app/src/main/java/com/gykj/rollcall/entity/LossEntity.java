package com.gykj.rollcall.entity;

import java.io.Serializable;

/**
 * desc   : 报损实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/210:02
 * version: 1.0
 */
public class LossEntity implements Serializable {

    private String avater;
    private String people;
    private String items;
    private String date;
    private int type;
    private String room;
    private String deal_date;
    private String detail;
    private String model;


    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDeal_date() {
        return deal_date;
    }

    public void setDeal_date(String deal_date) {
        this.deal_date = deal_date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


}
