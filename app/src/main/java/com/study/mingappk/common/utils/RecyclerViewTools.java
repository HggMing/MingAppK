package com.study.mingappk.common.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.mingappk.R;

/**
 * 滚动工具类
 * Created by Ming on 2016/7/18.
 */
public class RecyclerViewTools {
    private boolean move = false;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private int mIndex = 0;

    public RecyclerViewTools(RecyclerView mRecyclerView, LinearLayoutManager mLinearLayoutManager) {
        this.mRecyclerView = mRecyclerView;
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    public void smoothMoveToPosition(int n) {
        mIndex = n;
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            mRecyclerView.smoothScrollToPosition(n);
            move = true;
        }
    }


    public void moveToPosition(int n) {
        mIndex = n;
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {//当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {//当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {//当要置顶的项在当前显示的最后一项的后面时
            mRecyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
    }

    public class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRecyclerView.getChildCount()) {
                    int top = mRecyclerView.getChildAt(n).getTop();
                    mRecyclerView.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动（最后的100米！）
            if (move) {
                move = false;
                //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRecyclerView.getChildCount()) {
                    //获取要置顶的项顶部离RecyclerView顶部的距离
                    int top = mRecyclerView.getChildAt(n).getTop();
                    //最后的移动
                    mRecyclerView.scrollBy(0, top);
                }
            }
        }
    }
}
