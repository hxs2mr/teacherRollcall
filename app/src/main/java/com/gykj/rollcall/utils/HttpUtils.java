package com.gykj.rollcall.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * desc   : 网络请求工具类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/411:14
 * version: 1.0
 */

public class HttpUtils {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
