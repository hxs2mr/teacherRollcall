package com.gykj.rollcall.callback;

import com.gykj.mvvmlibrary.entity.callrealm.CallRealm;

import io.realm.RealmResults;

/**
 * Data on :2019/4/19 0019
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :查找数据库回调
 */
public interface CallTimerBack {
        void Success(RealmResults<CallRealm> realms);
        void Error(String msg);
}
