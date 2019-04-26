package com.gykj.mvvmlibrary.entity.callrealm;

import io.realm.RealmResults;

/**
 * Data on :2019/4/19 0019
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :
 */
public interface IRealmSelectListener {
    void Success( RealmResults<CallRealm> callRealms);
    void Error(String error);
}
