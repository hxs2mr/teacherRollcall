package com.gykj.rollcall.model;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class DormitoryBean {

    /**
     * id : 1
     * dormitoryName : 1
     * buildingId : 1
     * schoolId : 1
     * bedsNum : 1
     * status : 1
     * gmtCreate : null
     * gmtModified : 2019-04-04 05:33:16
     */

    private int id;
    private String dormitoryName;
    private int buildingId;
    private int schoolId;
    private int bedsNum;
    private int status;
    private Object gmtCreate;
    private String gmtModified;
    private boolean isCheck= true;

    public DormitoryBean(int id, String dormitoryName, int buildingId, int schoolId, int bedsNum, int status, Object gmtCreate, String gmtModified, boolean isCheck) {
        this.id = id;
        this.dormitoryName = dormitoryName;
        this.buildingId = buildingId;
        this.schoolId = schoolId;
        this.bedsNum = bedsNum;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.isCheck = isCheck;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDormitoryName() {
        return dormitoryName;
    }

    public void setDormitoryName(String dormitoryName) {
        this.dormitoryName = dormitoryName;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getBedsNum() {
        return bedsNum;
    }

    public void setBedsNum(int bedsNum) {
        this.bedsNum = bedsNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Object gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }
}
