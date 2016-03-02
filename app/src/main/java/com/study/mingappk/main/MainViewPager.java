package com.study.mingappk.main;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @ClassName: MainViewPager @Description: TODO自定义ViewPager
 */
public class MainViewPager extends ViewPager {
    private boolean isSlipping = true;/*可滑动标志位，初始化为可滑动*/

    public MainViewPager(Context context) {
        super(context);
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!isSlipping) {
            return false;
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (!isSlipping) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

    /**
     * @param isSlipping
     * @Title: setSlipping
     * @Description: TODO设置ViewPager是否可滑动
     */
    public void setSlipping(boolean isSlipping) {
        this.isSlipping = isSlipping;
    }
}
