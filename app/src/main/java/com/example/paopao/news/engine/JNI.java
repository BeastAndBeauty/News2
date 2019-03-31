package com.example.paopao.news.engine;

/**
 * Created by paopao on 2018/10/6.
 */

public class JNI {
    static{
        System.loadLibrary("native-lib");
    }
    //获取AppKey
    public static native String getAppKey();
}
