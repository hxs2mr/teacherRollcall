package com.gykj.mvvmlibrary.entity.callrealm;
import io.realm.RealmObject;

/**
 * Data on :2019/4/18 0018
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :用于存储 点名规则数据库
 */
public class CallRealm extends RealmObject {

    private String startdate;
    private String enddate;
    private String starttime;
    private String endtime;
    private int rollType;
    private int rollCallRuleId;
    private  int status;
    private String weekList;
    private int isruning;//是否已经启动点名
    private int rollCallId;

    public int getRollCallId() {
        return rollCallId;
    }

    public void setRollCallId(int rollCallId) {
        this.rollCallId = rollCallId;
    }

    public int getIsruning() {
        return isruning;
    }

    public void setIsruning(int isruning) {
        this.isruning = isruning;
    }

    public int getRollCallRuleId() {
        return rollCallRuleId;
    }

    public void setRollCallRuleId(int rollCallRuleId) {
        this.rollCallRuleId = rollCallRuleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getRollType() {
        return rollType;
    }

    public void setRollType(int rollType) {
        this.rollType = rollType;
    }

    public String getWeekList() {
        return weekList;
    }

    public void setWeekList(String weekList) {
        this.weekList = weekList;
    }
}
