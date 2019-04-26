package com.gykj.mvvmlibrary.utils;

import android.os.Environment;

import com.alibaba.sdk.android.oss.OSS;

import java.io.File;

import retrofit2.http.PUT;

/**
 * Data on :2019/3/28 0028
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :数据
 */
public class Contract {

    /**MQ**/
    public static final String MQ_HOST = "120.78.175.59";
    public static final int MQ_PORT = 33675;
    public static final String MQ_USERNAME = "cashier";
    public static final String MQ_PASSWORD = "pass4cashier";

    public static final String QUEUE_NAME = "topic.dormitory.local.";
    public static final String MQ_EXCHANGE_CAR = "cashierTopicExchange";


    /**阿里云OSS模块**/
    //基础endpoint地址
    public static final String OSS_BASE = "http://oss-cn-shanghai.aliyuncs.com";

    //鉴权服务器地址
    public static  final String  STS_URL = "https://wrs.gykjewm.com/appInterface/osstoken/main";

    //上传的空间地址
    public static final String BUKET_NAME= "guanyukeji-front";

    public static  final String OSS_ACCESSS_KEY="";
    public static  final String OSS_ACCESS_KEY_SECRET="";

    //上传 下载文件的标志位
    public static final int DOWNLOAD_SUC = 1;
    public static final int DOWNLOAD_Fail = 2;
    public static final int UPLOAD_SUC = 3;
    public static final int UPLOAD_Fail = 4;


    //Header请求头
    public static  String isToken="false";
    public static  String TENANT_ID="1";
    public static  String Authorization="";
    public static  String CLIENT_FROM="web_manager";


    /*地址*/
    public static final String PROJECT_NAME = "HXS";
    public static final String DELITE = "SS";
    /*缓存路径*/
    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + PROJECT_NAME + File.separator + "hxs";

    public  static  String USER_ID="USER_ID";
    public static  String buildingId="buildingId";

    public static int   PAGE_SIZE = 10;

    //通知模块  标记发布还是修改
    public static  String EditFlage = "EditFlage";
    public static  String TITLE="TITLE";
    public static  String IMG="IMG";
    public static  String CONTENT = "CONTENT";
}
