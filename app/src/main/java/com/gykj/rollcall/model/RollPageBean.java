package com.gykj.rollcall.model;

import java.io.Serializable;
import java.util.List;

/**
 * Data on :2019/4/4 0004
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class RollPageBean {


    /**
     * records : [{"id":1,"rollType":0,"buildingId":1,"singleStartDate":"2019-04-04","singleEndDate":"2019-04-04","weekList":null,"startTime":"14:14:00","endTime":"14:30:00","dormitoryList":"-1","status":1,"gmtCreate":"2019-04-04 14:13:20","gmtModified":null},{"id":2,"rollType":0,"buildingId":1,"singleStartDate":"2019-04-04","singleEndDate":"2019-04-04","weekList":null,"startTime":"14:30","endTime":"15:00","dormitoryList":"1,2","status":1,"gmtCreate":"2019-04-04 14:21:11","gmtModified":null},{"id":3,"rollType":0,"buildingId":1,"singleStartDate":"2019-04-04","singleEndDate":"2019-04-04","weekList":null,"startTime":"14:30","endTime":"15:00","dormitoryList":"1,2","status":1,"gmtCreate":"2019-04-04 14:46:52","gmtModified":null}]
     * total : 3
     * size : 10
     * current : 1
     * searchCount : true
     * pages : 1
     */
    private int total;
    private int size;
    private int current;
    private boolean searchCount;
    private int pages;
    private List<RecordsBean> records;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isSearchCount() {
        return searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean implements Serializable {
        /**
         * id : 1
         * rollType : 0
         * buildingId : 1
         * singleStartDate : 2019-04-04
         * singleEndDate : 2019-04-04
         * weekList : null
         * startTime : 14:14:00
         * endTime : 14:30:00
         * dormitoryList : -1
         * status : 1
         * gmtCreate : 2019-04-04 14:13:20
         * gmtModified : null
         */

        private int id;
        private int rollType;
        private int buildingId;
        private String singleStartDate;
        private String singleEndDate;
        private Object weekList;
        private String startTime;
        private String endTime;
        private String dormitoryList;
        private int status;
        private String gmtCreate;
        private Object gmtModified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRollType() {
            return rollType;
        }

        public void setRollType(int rollType) {
            this.rollType = rollType;
        }

        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public String getSingleStartDate() {
            return singleStartDate;
        }

        public void setSingleStartDate(String singleStartDate) {
            this.singleStartDate = singleStartDate;
        }

        public String getSingleEndDate() {
            return singleEndDate;
        }

        public void setSingleEndDate(String singleEndDate) {
            this.singleEndDate = singleEndDate;
        }

        public Object getWeekList() {
            return weekList;
        }

        public void setWeekList(Object weekList) {
            this.weekList = weekList;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getDormitoryList() {
            return dormitoryList;
        }

        public void setDormitoryList(String dormitoryList) {
            this.dormitoryList = dormitoryList;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public Object getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(Object gmtModified) {
            this.gmtModified = gmtModified;
        }
    }
}
