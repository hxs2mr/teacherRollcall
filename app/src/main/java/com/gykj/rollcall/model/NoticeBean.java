package com.gykj.rollcall.model;

import java.io.Serializable;
import java.util.List;

/**
 * Data on :2019/4/3 0003
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :获取通知的类
 */
public class NoticeBean {

    /**
     * records : [{"id":3,"buildingId":1,"userId":1,"noticeTitle":"测试标题3","noticeImgUrl":"测试内容2","noticeDesc":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1633600873,148835269&fm=26&gp=0.jpg","status":1,"gmtCreate":null,"gmtModified":"2019-04-03 08:10:49"}]
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

    public static class RecordsBean implements Serializable {
        /**
         * id : 3
         * buildingId : 1
         * userId : 1
         * noticeTitle : 测试标题3
         * noticeImgUrl : 测试内容2
         * noticeDesc : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1633600873,148835269&fm=26&gp=0.jpg
         * status : 1
         * gmtCreate : null
         * gmtModified : 2019-04-03 08:10:49
         */

        private int id;
        private int buildingId;
        private int userId;
        private String noticeTitle;
        private String noticeImgUrl;
        private String noticeDesc;
        private int status;
        private Object gmtCreate;
        private String gmtModified;

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeImgUrl() {
            return noticeImgUrl;
        }

        public void setNoticeImgUrl(String noticeImgUrl) {
            this.noticeImgUrl = noticeImgUrl;
        }

        public String getNoticeDesc() {
            return noticeDesc;
        }

        public void setNoticeDesc(String noticeDesc) {
            this.noticeDesc = noticeDesc;
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
}
