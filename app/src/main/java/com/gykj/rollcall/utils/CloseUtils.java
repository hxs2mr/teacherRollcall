package com.gykj.rollcall.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * desc   : 流关闭工具类
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/415:12
 * version: 1.0
 */
public class CloseUtils {

    private CloseUtils(){};

    public static void closeQuietly(Closeable... closeables){
        if(null != closeables){
            for (Closeable c: closeables) {
                try {
                    if(c != null){
                        c.close();
                        c = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}