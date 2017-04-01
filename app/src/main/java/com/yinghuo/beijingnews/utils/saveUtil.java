package com.yinghuo.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jin on 2017/4/1.
 */

public class saveUtil   {

    public static final String SHARE_DATA = "share_data";




    public static boolean getBoolean(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_DATA, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    public  static void setBoolean(Context context,String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
    }
}
