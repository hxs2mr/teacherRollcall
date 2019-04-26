package com.gykj.rollcall.manager;

import com.gykj.mvvmlibrary.entity.CityRealm;
import com.gykj.rollcall.model.iview.IRealmListener;

import io.realm.Realm;

/**
 * desc   : 城市数据库管理器
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/415:14
 * version: 1.0
 */
public class CityRealmManager {

    private CityRealmManager() {
    }

    private static class CityRealmManagerHolder {
        private static CityRealmManager instance = new CityRealmManager();
    }

    public static CityRealmManager getManager() {
        return CityRealmManager.CityRealmManagerHolder.instance;
    }


    /**
     * 添加指静脉
     */
    public void addCityRealm(final String cityCode, final String cityName, final String cityPingyin, final String province, final IRealmListener listener) {
        final Realm mRealm = Realm.getDefaultInstance();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CityRealm cityRealm = new CityRealm();
                cityRealm.setCityCode(cityCode);
                cityRealm.setCityName(cityName);
                cityRealm.setCityPingyin(cityPingyin);
                cityRealm.setProvince(province);
                realm.copyToRealm(cityRealm);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.OnSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                listener.onError(error);
            }
        });

    }
}
