package com.gykj.rollcall.entity;

import java.io.Serializable;
import java.util.List;

/**
 * desc   : 天气实体类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/711:25
 * version: 1.0
 */
public class WeatherEntity implements Serializable {


    /**
     * data : {"yesterday":{"date":"6日星期日","high":"高温 7℃","fx":"东风","low":"低温 3℃","fl":"<![CDATA[<3级]]>","type":"小雨"},"city":"遵义","forecast":[{"date":"7日星期一","high":"高温 7℃","fengli":"<![CDATA[<3级]]>","low":"低温 3℃","fengxiang":"东风","type":"阴"},{"date":"8日星期二","high":"高温 5℃","fengli":"<![CDATA[<3级]]>","low":"低温 2℃","fengxiang":"东南风","type":"中雨"},{"date":"9日星期三","high":"高温 5℃","fengli":"<![CDATA[<3级]]>","low":"低温 2℃","fengxiang":"东北风","type":"阴"},{"date":"10日星期四","high":"高温 6℃","fengli":"<![CDATA[<3级]]>","low":"低温 4℃","fengxiang":"东南风","type":"小雨"},{"date":"11日星期五","high":"高温 8℃","fengli":"<![CDATA[<3级]]>","low":"低温 4℃","fengxiang":"东风","type":"阴"}],"ganmao":"昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。","wendu":"4"}
     * status : 1000
     * desc : OK
     */

    private DataBean data;
    private int status;
    private String desc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class DataBean implements Serializable{
        /**
         * yesterday : {"date":"6日星期日","high":"高温 7℃","fx":"东风","low":"低温 3℃","fl":"<![CDATA[<3级]]>","type":"小雨"}
         * city : 遵义
         * forecast : [{"date":"7日星期一","high":"高温 7℃","fengli":"<![CDATA[<3级]]>","low":"低温 3℃","fengxiang":"东风","type":"阴"},{"date":"8日星期二","high":"高温 5℃","fengli":"<![CDATA[<3级]]>","low":"低温 2℃","fengxiang":"东南风","type":"中雨"},{"date":"9日星期三","high":"高温 5℃","fengli":"<![CDATA[<3级]]>","low":"低温 2℃","fengxiang":"东北风","type":"阴"},{"date":"10日星期四","high":"高温 6℃","fengli":"<![CDATA[<3级]]>","low":"低温 4℃","fengxiang":"东南风","type":"小雨"},{"date":"11日星期五","high":"高温 8℃","fengli":"<![CDATA[<3级]]>","low":"低温 4℃","fengxiang":"东风","type":"阴"}]
         * ganmao : 昼夜温差很大，易发生感冒，请注意适当增减衣服，加强自我防护避免感冒。
         * wendu : 4
         */

        private YesterdayBean yesterday;
        private String city;
        private String ganmao;
        private String wendu;
        private List<ForecastBean> forecast;

        public YesterdayBean getYesterday() {
            return yesterday;
        }

        public void setYesterday(YesterdayBean yesterday) {
            this.yesterday = yesterday;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class YesterdayBean implements Serializable{
            /**
             * date : 6日星期日
             * high : 高温 7℃
             * fx : 东风
             * low : 低温 3℃
             * fl : <![CDATA[<3级]]>
             * type : 小雨
             */

            private String date;
            private String high;
            private String fx;
            private String low;
            private String fl;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFx() {
                return fx;
            }

            public void setFx(String fx) {
                this.fx = fx;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class ForecastBean implements Serializable{
            /**
             * date : 7日星期一
             * high : 高温 7℃
             * fengli : <![CDATA[<3级]]>
             * low : 低温 3℃
             * fengxiang : 东风
             * type : 阴
             */

            private String date;
            private String high;
            private String fengli;
            private String low;
            private String fengxiang;
            private String type;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHigh() {
                return high;
            }

            public void setHigh(String high) {
                this.high = high;
            }

            public String getFengli() {
                return fengli;
            }

            public void setFengli(String fengli) {
                this.fengli = fengli;
            }

            public String getLow() {
                return low;
            }

            public void setLow(String low) {
                this.low = low;
            }

            public String getFengxiang() {
                return fengxiang;
            }

            public void setFengxiang(String fengxiang) {
                this.fengxiang = fengxiang;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
