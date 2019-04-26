package com.gykj.rollcall.model;

/**
 * Data on :2019/4/24 0024
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public class StartCallBean {

    /**
     * rollCallId : 75
     * rollCallRule : {"id":28,"rollType":0,"buildingId":1,"singleStartDate":"2019年04月24","singleEndDate":"2019年04月24","weekList":null,"startTime":"13时45分","endTime":"13时50分","dormitoryList":"1","status":1,"gmtCreate":"2019-04-24 13:45:18","gmtModified":null}
     */

    private int rollCallId;
    private RollCallRuleBean rollCallRule;

    public int getRollCallId() {
        return rollCallId;
    }

    public void setRollCallId(int rollCallId) {
        this.rollCallId = rollCallId;
    }

    public RollCallRuleBean getRollCallRule() {
        return rollCallRule;
    }

    public void setRollCallRule(RollCallRuleBean rollCallRule) {
        this.rollCallRule = rollCallRule;
    }

    public static class RollCallRuleBean {
        /**
         * id : 28
         * rollType : 0
         * buildingId : 1
         * singleStartDate : 2019年04月24
         * singleEndDate : 2019年04月24
         * weekList : null
         * startTime : 13时45分
         * endTime : 13时50分
         * dormitoryList : 1
         * status : 1
         * gmtCreate : 2019-04-24 13:45:18
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
