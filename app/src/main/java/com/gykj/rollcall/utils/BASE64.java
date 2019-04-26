package com.gykj.rollcall.utils;

import android.util.Base64;

/**
 * Data on :2019/4/1 0001
 * By User :HXS
 * Email on :1363826037@qq.com
 * Description on :BASE64加密解密
 */
public class BASE64 {
    //将 s 进行 BASE64 编码

    //base64编码
    public static String getBASE64(String s) {
        String strBase64 = Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
        return strBase64;
    }

    //将 BASE64 编码的字符串 s 进行解码
    public static String getFromBASE64(String strBase64) {
        String str2 =  new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));
        return str2;
    }
}
