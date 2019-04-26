package com.gykj.rollcall.utils;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;

import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;
import com.gykj.mvvmlibrary.entity.callrealm.CallRealmManager;
import com.gykj.mvvmlibrary.entity.callrealm.IRealmSelectListener;
import com.gykj.mvvmlibrary.utils.ToastUtils;
import com.gykj.rollcall.callback.CallTimerBack;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Data on :2019/4/19 0019
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on : 定时器   用于间隔沦陷数据库中的点名规则
 */
public class TimerUtil {
    private Timer timer = new Timer();
    //单利
    private static TimerUtil instance= null;
    private    Realm mRealm;;
    //单锁
    public static synchronized TimerUtil getInstance()
    {
        if(instance == null) {
            instance = new TimerUtil();
        }
        return  instance;
    }

    public void StartTimer(Activity activity, CallTimerBack callTimerBack){
        if (timer == null) {
            timer = new Timer();
        }
        if(null == mRealm){
            mRealm = Realm.getDefaultInstance();
        }
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                RealmResults<CallRealm> callRealms = mRealm.where(CallRealm.class).findAll(); //人脸识别的数据库
                                Log.d("HXS","点名规则数据库:"+callRealms.size());
                                if(callTimerBack==null)
                                {
                                    return;
                                }
                                    if(callRealms.size()>0)
                                    {
                                      callTimerBack.Success(callRealms);

                                    }else {
                                        callTimerBack.Error("暂无数据！");
                                    }
                            }
                        });

            }
        };
        timer.schedule(timerTask,0,15*1000);
    }

    public void timerCancle() {
        if (timer != null)
        {
            timer.cancel();
        }
        timer = null;
    }

}
