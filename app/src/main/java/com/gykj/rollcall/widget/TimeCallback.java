package com.gykj.rollcall.widget;

/**
 * Data on :2019/4/17 0017
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :时间选择回调
 */
public interface TimeCallback {
    void single(String start_h,String start_m,String end_h,String end_m);
    void rule(String start_h,String start_m,String end_h,String end_m);
}
