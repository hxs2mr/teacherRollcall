package com.gykj.rollcall.manager;

import com.gykj.mvvmlibrary.entity.Config;
import com.gykj.rollcall.app.RollCallApplication;
import com.gykj.rollcall.entity.CityInfo;
import com.gykj.rollcall.entity.WeatherEntity;
import com.gykj.rollcall.utils.ACache;

/**
 * desc   : 点名系统缓存管理器
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/713:17
 * version: 1.0
 */
public class RCManager {

    private ACache mACache;

    private RCManager(){
        mACache = ACache.get(RollCallApplication.getInstance());
    }

    public static RCManager getInstance(){
        return RCManagerHolde.instance;
    }


    private static class RCManagerHolde{
        private static RCManager instance = new RCManager();
    }


    public void saveCityInfo(CityInfo info){
        mACache.put(Config.CITY_INFO,info);
    }

    public CityInfo getCityInfo(){
        return (CityInfo) mACache.getAsObject(Config.CITY_INFO);
    }


    public void saveWeatherInfo(WeatherEntity entity){
        mACache.put(Config.WEATHER_INFO,entity);
    }

    public WeatherEntity getWeatherEntity(){
        return (WeatherEntity) mACache.getAsObject(Config.WEATHER_INFO);
    }
}
