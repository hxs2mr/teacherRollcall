package com.gykj.rollcall.entity;

/**
 * desc   : 宿舍实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2018/12/2816:06
 * version: 1.0
 */
public class RoomEntity {

    private int roomId;
    private String roomName;
    private boolean isCheck;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

}
