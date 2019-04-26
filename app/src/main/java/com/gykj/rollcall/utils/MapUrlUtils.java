package com.gykj.rollcall.utils;

/**
 * desc   : 地图地址URL 类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/710:21
 * version: 1.0
 */
public class MapUrlUtils {

    /**
     * 获取地图URL
     * @param longitude
     * @param latitude
     * @return
     */
    public static String getMapUrl(String longitude,String latitude){
        return "http://api.map.baidu.com/geocoder?output=json&location="+latitude+","+longitude+"&ak=esNPFDwwsXWtsQfw4NMNmur1";
    }

    /**
     * 获取天气预报URL
     * @param city
     * @return
     */
    public static String getWeatherUrl(String city){
        return "http://wthrcdn.etouch.cn/weather_mini?city="+city;
    }
}
