package com.gykj.rollcall.model;

import java.util.List;

/**
 * Data on :2019/4/22 0022
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class RollCallIdBean {


    /**
     * records : [{"id":58,"rollRuleId":16,"status":0,"allUserNum":2,"signNum":0,"unsignNum":2,"percent":0,"gmtCreate":"2019-04-22 16:44:56","gmtModified":"2019-04-22 08:44:56"},{"id":57,"rollRuleId":16,"status":0,"allUserNum":2,"signNum":0,"unsignNum":2,"percent":0,"gmtCreate":"2019-04-22 15:13:05","gmtModified":"2019-04-22 07:13:04"}]
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

    public static class RecordsBean {
        /**
         * id : 58
         * rollRuleId : 16
         * status : 0
         * allUserNum : 2
         * signNum : 0
         * unsignNum : 2
         * percent : 0
         * gmtCreate : 2019-04-22 16:44:56
         * gmtModified : 2019-04-22 08:44:56
         */
        private int id;
        private int rollRuleId;
        private int status;
        private int allUserNum;
        private int signNum;
        private int unsignNum;
        private int percent;
        private String gmtCreate;
        private String gmtModified;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRollRuleId() {
            return rollRuleId;
        }

        public void setRollRuleId(int rollRuleId) {
            this.rollRuleId = rollRuleId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAllUserNum() {
            return allUserNum;
        }

        public void setAllUserNum(int allUserNum) {
            this.allUserNum = allUserNum;
        }

        public int getSignNum() {
            return signNum;
        }

        public void setSignNum(int signNum) {
            this.signNum = signNum;
        }

        public int getUnsignNum() {
            return unsignNum;
        }

        public void setUnsignNum(int unsignNum) {
            this.unsignNum = unsignNum;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
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
