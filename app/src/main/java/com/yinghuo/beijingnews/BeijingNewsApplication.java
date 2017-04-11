package com.yinghuo.beijingnews;

import android.app.Application;
import android.util.Log;

import com.yinghuo.beijingnews.utils.LogUtil;

/**
 * Created by jin on 2017/4/1.
 */

public class BeijingNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.setIsDebug(true);
        LogUtil.setTAG("jin");
    }
}
