package com.itheima.laosiji.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Marlboro丶 on 2016/9/25.
 */
public class SpUtil {
    public static final String SP_NAME = "config";
    public static void putInt(Context context,String key,int value){
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt(Context context,String key,int defValue) {
        SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }


    public static void putString(Context context,String key,String flag){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, flag);
        edit.commit();
    }

    public static String getString(Context context,String key,String df){
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, df);
    }
}
