package com.yinghuo.beijingnews.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.yinghuo.beijingnews.R;
import com.yinghuo.beijingnews.utils.LogUtil;
import com.yinghuo.beijingnews.utils.ToastUtil;
import com.yinghuo.beijingnews.utils.SharePreferenceUtil;

public class SplashActivity extends Activity {

    private RelativeLayout rel_splash;

    public static final String IS_FIRST_TIME = "is_first_time";
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rel_splash = (RelativeLayout) findViewById(R.id.rel_splash);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
//        alphaAnimation.setDuration(1000);
//        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
//        scaleAnimation.setDuration(1000);
//        scaleAnimation.setFillAfter(true);

//用集合的好处时可以统一设置，避免混乱，也可以统一播放时间，当然分开设置也可以统一播放
        AnimationSet animationSet = new AnimationSet(false);

        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setDuration(1000);//这里设置了会覆盖上面的时间设置
        animationSet.setFillAfter(true);
        //为动画设置监听，因为动画完成后需要相应处理才行
        animationSet.setAnimationListener(new MyAnimationListener());
        rel_splash.setAnimation(animationSet);

    }

    /**
     * 监听动画播放完成的内部类
     */
        class MyAnimationListener implements Animation.AnimationListener{

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

//                ToastUtil.showShort(SplashActivity.this,"animation end");

                isFirst = SharePreferenceUtil.getBoolean(SplashActivity.this, IS_FIRST_TIME);

                LogUtil.i(" "+isFirst);

                Intent intent;
                if(!isFirst){
                    intent = new Intent(SplashActivity.this,WelcomeActivity.class);
                    startActivity(intent);
//                    SharePreferenceUtil.setBoolean(SplashActivity.this,IS_FIRST_TIME,true);
                }else{
                    intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        }

}
