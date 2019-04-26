package com.gykj.rollcall.entity;

import java.util.List;

/**
 * name : HXS
 * e-mail : 1363826037@qq.com
 * descript:人员签到的实体类
 * date   : 2019/3/1511:14
 * version: 1.0
 */
public class StartRecordBean {

    /**
     * dormitoryList : [{"signNum":0,"rullCallId":66,"dormitoryName":"1","unSignNum":2,"dormitoryId":1},{"signNum":0,"rullCallId":66,"dormitoryName":"2","unSignNum":0,"dormitoryId":2},{"signNum":0,"rullCallId":66,"dormitoryName":"3","unSignNum":0,"dormitoryId":3},{"signNum":0,"rullCallId":66,"dormitoryName":"4","unSignNum":0,"dormitoryId":4},{"signNum":0,"rullCallId":66,"dormitoryName":"5","unSignNum":0,"dormitoryId":5},{"signNum":0,"rullCallId":66,"dormitoryName":"6","unSignNum":0,"dormitoryId":6},{"signNum":0,"rullCallId":66,"dormitoryName":"7","unSignNum":0,"dormitoryId":7},{"signNum":0,"rullCallId":66,"dormitoryName":"8","unSignNum":0,"dormitoryId":8},{"signNum":0,"rullCallId":66,"dormitoryName":"9","unSignNum":0,"dormitoryId":9},{"signNum":0,"rullCallId":66,"dormitoryName":"10","unSignNum":0,"dormitoryId":10},{"signNum":0,"rullCallId":66,"dormitoryName":"11","unSignNum":0,"dormitoryId":11}]
     * rollCallRule : {"id":19,"rollType":0,"buildingId":1,"singleStartDate":"2019年04月23","singleEndDate":"2019年04月23","weekList":null,"startTime":"15时40分","endTime":"16时00分","dormitoryList":"1","status":1,"gmtCreate":"2019-04-23 15:49:15","gmtModified":"2019-04-23 07:49:36"}
     * rollCall : {"id":66,"rollRuleId":19,"status":0,"allUserNum":2,"signNum":0,"unsignNum":2,"percent":0,"gmtCreate":"2019-04-23 15:49:37","gmtModified":"2019-04-23 07:49:37"}
     */

    private RollCallRuleBean rollCallRule;
    private RollCallBean rollCall;
    private List<DormitoryListBean> dormitoryList;

    public RollCallRuleBean getRollCallRule() {
        return rollCallRule;
    }

    public void setRollCallRule(RollCallRuleBean rollCallRule) {
        this.rollCallRule = rollCallRule;
    }

    public RollCallBean getRollCall() {
        return rollCall;
    }

    public void setRollCall(RollCallBean rollCall) {
        this.rollCall = rollCall;
    }

    public List<DormitoryListBean> getDormitoryList() {
        return dormitoryList;
    }

    public void setDormitoryList(List<DormitoryListBean> dormitoryList) {
        this.dormitoryList = dormitoryList;
    }

    public static class RollCallRuleBean {
        /**
         * id : 19
         * rollType : 0
         * buildingId : 1
         * singleStartDate : 2019年04月23
         * singleEndDate : 2019年04月23
         * weekList : null
         * startTime : 15时40分
         * endTime : 16时00分
         * dormitoryList : 1
         * status : 1
         * gmtCreate : 2019-04-23 15:49:15
         * gmtModified : 2019-04-23 07:49:36
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
        private String gmtModified;

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

        public String getGmtModified() {
            return gmtModified;
        }

        public void setGmtModified(String gmtModified) {
            this.gmtModified = gmtModified;
        }
    }

    public static class RollCallBean {
        /**
         * id : 66
         * rollRuleId : 19
         * status : 0
         * allUserNum : 2
         * signNum : 0
         * unsignNum : 2
         * percent : 0
         * gmtCreate : 2019-04-23 15:49:37
         * gmtModified : 2019-04-23 07:49:37
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

    public static class DormitoryListBean {
        /**
         * signNum : 0
         * rullCallId : 66
         * dormitoryName : 1
         * unSignNum : 2
         * dormitoryId : 1
         */

        private int signNum;
        private int rullCallId;
        private String dormitoryName;
        private int unSignNum;
        private int dormitoryId;

        public int getSignNum() {
            return signNum;
        }

        public void setSignNum(int signNum) {
            this.signNum = signNum;
        }

        public int getRullCallId() {
            return rullCallId;
        }

        public void setRullCallId(int rullCallId) {
            this.rullCallId = rullCallId;
        }

        public String getDormitoryName() {
            return dormitoryName;
        }

        public void setDormitoryName(String dormitoryName) {
            this.dormitoryName = dormitoryName;
        }

        public int getUnSignNum() {
            return unSignNum;
        }

        public void setUnSignNum(int unSignNum) {
            this.unSignNum = unSignNum;
        }

        public int getDormitoryId() {
            return dormitoryId;
        }

        public void setDormitoryId(int dormitoryId) {
            this.dormitoryId = dormitoryId;
        }
    }
}
