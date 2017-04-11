package com.yinghuo.beijingnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yinghuo.beijingnews.R;
import com.yinghuo.beijingnews.utils.LogUtil;

import java.util.ArrayList;

public class WelcomeActivity extends Activity implements View.OnClickListener{

    private Button btn_main_start;
    private ViewPager viewPager;
    private LinearLayout lin_guide_point;
    private int pages[];
    private ArrayList<ImageView> imageViews;
    private  ImageView iv_red_point;
    private int spaceBetween;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        viewPager.setAdapter(new MyViewAdapter());
        //view的生命周期： 测量--》布局--》绘制
        //根据视图的生命周期，在页面绘制之前得到红点的位置，然后再绘制出来就可以
        //红点距离左边距离 = 红点初始位置距离左边距离+ 两点间距离*屏幕滑动百分比
        //获得视图树，调用下面的接口：当视图树中某个视图布局发生改变或者某个视图的可视状态发生改变时，所要调用的接口
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //添加viewPager滑动的监听器，已确定pager滑动的百分比
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
    }

    private void initView() {
        btn_main_start = (Button) findViewById(R.id.btn_start);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        lin_guide_point = (LinearLayout) findViewById(R.id.lin_guid_point);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);

        btn_main_start.setOnClickListener(this);

        pages = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};

        imageViews = new ArrayList<>();
        LogUtil.i(pages.length+"pages的长度");
        for (int i =0;i<pages.length;i++){
            ImageView imageView = new ImageView(this);
            LogUtil.i("执行第+"+i);
            imageView.setBackgroundResource(pages[i]);
            imageViews.add(imageView);

            //多少个page就有多少个点，所有可以直接在这里面新建点的view
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            if(i != 0){
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            lin_guide_point.addView(point);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
        }

    }

    private class MyViewAdapter extends PagerAdapter {

        //viewpager的页面个数
        @Override
        public int getCount() {
            return imageViews.size();
        }

        //对pager进行缓存前的初始化，将pager添加到容器中，然后返回这个view

        /**
         *
         * @param container 返回这个存储view容器
         * @param position 要创建页面的位置
         * @return 返回和要创建的页面有关系的值
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        //比较初始化的view和现在所显示的view是否是同一个view
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        //pageradapter只缓存二张pager，如果超过了缓存的范围，就换销毁前面的缓存
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);一定要移除这句代码

//            container.removeView(imageViews.get(position));这句代码和下面的代码功能相同，但是更加简单
            container.removeView((View) object);
        }


    }

    /**
     * 当视图树中某个视图布局发生改变或者某个视图的可视状态发生改变时，所要调用的接口
     */
    private class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            //因为有多个子页面，所以这个方法执行不止一次
            //下面时一个版本判断，因为方法有写过时

            //这个方法用来移除监听，防止每一次都执行这个方法，影响性能
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }else{
                iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            //得到两个点之间的距离
            spaceBetween = lin_guide_point.getChildAt(1).getLeft() - lin_guide_point.getChildAt(0).getLeft();
        }
    }

    /**
     * 为了得到pager滑动的百分比，以确定红点的位置
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         *
         * @param position  当前页面的位置
         * @param positionOffset       当前页面滑动的百分比
         * @param positionOffsetPixels  当前页面滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //左边距 = 两点间距* （滑动百分比 + 第几个点的位置）
            int leftMargin = (int) (spaceBetween*(positionOffset + position));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftMargin;
            iv_red_point.setLayoutParams(params);
        }

        /**
         * 当页面被选中时调用这个方法
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            LogUtil.i("页面位置"+position);
            if(position == imageViews.size()-1){
                btn_main_start.setVisibility(View.VISIBLE);
            }else{
                btn_main_start.setVisibility(View.GONE);
            }

        }

        /**
         * 当viewPager状态发生改变时，调用这个方法
         * @param state 这个是viewPager的状态参数，三种状态：1.静止2.拖动3.弹回
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            LogUtil.i("状态"+state);
        }
    }
}
