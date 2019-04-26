package com.gykj.rollcall.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import com.gykj.rollcall.R;

/**
 * Data on :2019/4/1 0001
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :防抖动和快速点击
 */
public class AntiShakeUtils {

    private  final static  long INTERNAL_TIME = 1000;


    public static boolean isInvalidClick(View target)
    {
        return isInvalidClick(target, INTERNAL_TIME);
    }
    public static boolean isInvalidClick(@NonNull View target, @IntRange(from = 0) long internalTime) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp = 0;
        Object o = target.getTag(R.id.last_click_time);
        if (o == null){
            target.setTag(R.id.last_click_time, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < internalTime;
        if (!isInvalid)
            target.setTag(R.id.last_click_time, curTimeStamp);
        return isInvalid;
    }

}
