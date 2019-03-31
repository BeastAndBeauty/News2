package com.example.paopao.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by paopao on 2018/10/2.
 */

public class PreUtils {
    public static final String PRE_NAME="news";

    public static SharedPreferences sp;

    public static void putBoolean(Context context,String key,boolean value){
        if(sp==null)//MODE_APPEND：追加模式（此模式每一次commit，不会把上一次信息清除）
                    //MODE_PRIVATE：私有模式（此模式每一次commit，都会把上一次信息清除）
            sp=context.getSharedPreferences(PRE_NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).apply();
    }

    public static boolean getBoolean(Context context,String key,boolean value){
        if(sp==null)
            sp=context.getSharedPreferences(PRE_NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,value);
    }

    public static void putString(Context context,String key,String value){
        if(sp==null)
            sp=context.getSharedPreferences(PRE_NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();
    }

    public static String getString(Context context,String key,String value){
        if(sp==null)
            sp=context.getSharedPreferences(PRE_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,value);
    }

}
