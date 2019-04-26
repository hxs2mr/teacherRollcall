package com.gykj.mvvmlibrary.entity;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * desc   : 城市数据库
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/414:22
 * version: 1.0
 */
public class CityRealm extends RealmObject {

    private String cityCode;
    private String cityName;
    private String cityPingyin;
    private String province;


    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityPingyin() {
        return cityPingyin;
    }

    public void setCityPingyin(String cityPingyin) {
        this.cityPingyin = cityPingyin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
