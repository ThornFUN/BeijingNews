# BeijingNews

AppCompatActivity和Activity区别在于actionBar


继承AppCompatActivity的类使用  requestWindowFeature(Window.FEATURE_NO_TITLE);
这句代码会崩溃？？？？


AnimationSet这个集合用来装一一些动画，然后统一设置给一个view

Ctrl+alt+c：抽取数据

selector不允许名称大写，，，，文字的selector必须要时color，不能设置图片

引导页面的红点移动位置确定方法：

        //view的生命周期： 测量--》布局--》绘制
        //根据视图的生命周期，在页面绘制之前得到红点的位置，然后再绘制出来就可以
        //红点距离左边距离 = 红点初始位置距离左边距离+ 两点间距离*屏幕滑动百分比

viewPagerAdapter的使用需要实现以下四个方法：
	
	getCount()	//获得pager的总个数
	instantiateItem(ViewGroup, int)		//初始化pager页面的view
	isViewFromObject(View, Object)		//比较正在显示的页面view和初始化的view，是否相同
	destroyItem(ViewGroup, int, Object)	//viewPager默认缓存两个pager页面，当多于两个时，销毁前面缓存的页面

为viewPager滑动设置监听器，以获得页面滑动的百分比

	viewPager.addOnPageChangeListener(new MyOnPageChangeListener());

MyOnPageChangeListener需要实现三个方法：

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
         * @param position 被选中页面的位置，这里用来确定button的显示时间
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


实现视图树的页面改变的监听，其方法有可能执行多次，可以继承接口，并在第一次执行后移除相应接口，可提高部分性能

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
