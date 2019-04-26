package com.gykj.rollcall.model;

import java.util.List;

/**
 * Data on :2019/4/8 0008
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class BorrowBean {


    /**
     * records : [{"id":1,"userNum":"123","userName":"12","userId":1,"dormitoryId":1,"buildingId":1,"articleName":"1","articleNum":1,"status":1,"payTime":"2019-04-01 16:41:21","gmtCreate":"2019-04-01 16:41:23","gmtModified":"2019-04-01 16:41:25"}]
     * total : 1
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

    public static class RecordsBean {
        /**
         * id : 1
         * userNum : 123
         * userName : 12
         * userId : 1
         * dormitoryId : 1
         * buildingId : 1
         * articleName : 1
         * articleNum : 1
         * status : 1
         * payTime : 2019-04-01 16:41:21
         * gmtCreate : 2019-04-01 16:41:23
         * gmtModified : 2019-04-01 16:41:25
         */

        private int id;
        private String userNum;
        private String userName;
        private int userId;
        private int dormitoryId;
        private int buildingId;
        private String articleName;
        private int articleNum;
        private int status;
        private String payTime;
        private String gmtCreate;
        private String gmtModified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getDormitoryId() {
            return dormitoryId;
        }

        public void setDormitoryId(int dormitoryId) {
            this.dormitoryId = dormitoryId;
        }

        public int getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(int buildingId) {
            this.buildingId = buildingId;
        }

        public String getArticleName() {
            return articleName;
        }

        public void setArticleName(String articleName) {
            this.articleName = articleName;
        }

        public int getArticleNum() {
            return articleNum;
        }

        public void setArticleNum(int articleNum) {
            this.articleNum = articleNum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
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
