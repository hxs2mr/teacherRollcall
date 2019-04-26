package com.gykj.rollcall.model.iview;

/**
 * desc   : Realm数据库回调接口
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/415:18
 * version: 1.0
 */
public interface IRealmListener {

    void OnSuccess();

    void onError(Throwable error);
}
