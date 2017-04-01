package com.yinghuo.beijingnews.activity;


import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinghuo.beijingnews.R;
import com.yinghuo.beijingnews.utils.saveUtil;

public class SplashActivity extends Activity {

    public static final String IS_FIRST_TIME = "is_first_time";
    ImageView iv_test;
    TextView tv_test;
    TextView tv_company;
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv_test = (ImageView) findViewById(R.id.iv_test);
        tv_test = (TextView) findViewById(R.id.tv_test);
        tv_company = (TextView) findViewById(R.id.tv_company);
        propertyValuesHolder(iv_test);
        propertyValuesHolder(tv_test);
        propertyValuesHolder(tv_company);

        //需要监听动画播放完这个动作

/*
        isFirst = saveUtil.getBoolean(this, IS_FIRST_TIME);

        Intent intent;
        if(!isFirst){
            intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
            saveUtil.setBoolean(this,IS_FIRST_TIME,true);
        }else{
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();*/
    }

    public void propertyValuesHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(3000).start();
    }
}
