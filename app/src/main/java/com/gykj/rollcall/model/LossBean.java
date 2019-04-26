package com.gykj.rollcall.model;

import java.io.Serializable;
import java.util.List;

/**
 * Data on :2019/4/9 0009
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class LossBean {


    /**
     * records : [{"id":1,"userName":"123","buildingId":1,"dormitoryId":1,"articleName":"123","spec":"1","reportDesc":"2","status":0,"handleTime":null,"gmtCreate":"2019-04-09 16:30:01","gmtModified":"2019-04-09 08:30:06"},{"id":2,"userName":"456","buildingId":1,"dormitoryId":1,"articleName":"213","spec":"1","reportDesc":"23","status":1,"handleTime":"2019-04-09 16:30:20","gmtCreate":"2019-04-09 16:30:17","gmtModified":"2019-04-09 08:30:22"}]
     * total : 2
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
         * userName : 123
         * buildingId : 1
         * dormitoryId : 1
         * articleName : 123
         * spec : 1
         * reportDesc : 2
         * status : 0
         * handleTime : null
         * gmtCreate : 2019-04-09 16:30:01
         * gmtModified : 2019-04-09 08:30:06
         */

        private int id;
        private String userName;
        private int buildingId;
        private int dormitoryId;
        private String articleName;
        private String spec;
        private String reportDesc;
        private int status;
        private Object handleTime;
        private String gmtCreate;
        private String gmtModified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getArticleName() {
            return articleName;
        }

        public void setArticleName(String articleName) {
            this.articleName = articleName;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getReportDesc() {
            return reportDesc;
        }

        public void setReportDesc(String reportDesc) {
            this.reportDesc = reportDesc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(Object handleTime) {
            this.handleTime = handleTime;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }
    }
}
