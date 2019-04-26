package com.gykj.rollcall.entity;

import java.io.Serializable;
import java.util.List;

/**
 * desc   : 主界面ItemBean对象
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/259:33
 * version: 1.0
 */
public class MainEntity implements Serializable {

    private String title;
    private String content;
    private String date;
    private String people;
    private List<String> images;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


}
