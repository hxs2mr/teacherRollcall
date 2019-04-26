package com.gykj.rollcall.model;

import java.util.List;

/**
 * Data on :2019/4/11 0011
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class PoliceBean {


    /**
     * records : [{"id":38,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 16:10:29","gmtModified":null},{"id":37,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 16:09:09","gmtModified":null},{"id":36,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 15:08:42","gmtModified":null},{"id":35,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:59:53","gmtModified":null},{"id":34,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:55:56","gmtModified":null},{"id":33,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:50:02","gmtModified":null},{"id":32,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:44:42","gmtModified":null},{"id":31,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:42:41","gmtModified":null},{"id":30,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:40:02","gmtModified":null},{"id":29,"buildingId":1,"dormitoryId":1,"status":0,"gmtCreate":"2019-04-11 14:35:42","gmtModified":null}]
     * total : 38
     * size : 10
     * current : 1
     * searchCount : true
     * pages : 4
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

    public static class RecordsBean {
        /**
         * id : 38
         * buildingId : 1
         * dormitoryId : 1
         * status : 0
         * gmtCreate : 2019-04-11 16:10:29
         * gmtModified : null
         */

        private int id;
        private int buildingId;
        private int dormitoryId;
        private int status;
        private String gmtCreate;
        private Object gmtModified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public int getDormitoryId() {
            return dormitoryId;
        }

        public void setDormitoryId(int dormitoryId) {
            this.dormitoryId = dormitoryId;
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
