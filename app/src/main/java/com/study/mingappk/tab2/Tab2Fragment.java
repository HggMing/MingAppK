package com.study.mingappk.tab2;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class Tab2Fragment extends Fragment {

    @Bind(R.id.toolbar_tab2)
    Toolbar toolbar;
    @Bind(R.id.slideshow_tab2)
    SlideShowView slideshow;
    @Bind(R.id.pull_to_refresh)
    PtrClassicFrameLayout ptrFrame;


    private AppCompatActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar);

        super.onViewCreated(view, savedInstanceState);

        setPictureHeight();
        setPullToRefresh();
    }

    /**
     * 设置下拉刷新
     */

    private void setPullToRefresh() {

        ptrFrame.disableWhenHorizontalMove(true);//横向移动时不可下拉
        ptrFrame.setPtrHandler(new PtrHandler() {//实现接口：PtrHandler接口关注业务的变化。其包含2个方法
            /**
             * 多种模式刷新模式可以选，多种UI样式可选。不管万千变化，在开始刷新时，都会调用这个方法进行数据刷新。
             * @param frame
             */
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            /**
             * 开发者可以通过此方法，确定可以进行下拉刷新的时机。比如列表数据为空，比如列表数据过期，比如嵌套在ViewPager中的某个Fragment中的一个列表数据为空。
             * @param frame
             * @param content
             * @param header
             * @return
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        ptrFrame.setLastUpdateTimeRelateObject(this);

        // 下面这些是默认设置，也可通过xml设置
        ptrFrame.setResistance(3f);//抗性，默认1.7f
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(200);
        ptrFrame.setDurationToCloseHeader(1000);
        ptrFrame.setPullToRefresh(false);
        ptrFrame.setKeepHeaderWhenRefresh(true);

        // scroll then refresh
        // comment in base fragment
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ptrFrame.autoRefresh();
            }
        }, 150);
        setupViews(ptrFrame);
    }

    protected void setupViews(final PtrClassicFrameLayout ptrFrame) {

    }

    /**
     * 设置下拉刷新的具体数据
     */
    protected void updateData() {
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                slideshow.initData();
                ptrFrame.refreshComplete();
            }
        }, 300);
    }

    /**
     * 设置滚动图片的高度为屏幕宽度的一半
     */
    private void setPictureHeight() {
        WindowManager wm = getActivity().getWindowManager();
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int width = size.x;
        // int width = wm.getDefaultDisplay().getWidth();
        //int height = wm.getDefaultDisplay().getHeight();
        double i = width * 0.5;
        int a = (int) i;
        ViewGroup.LayoutParams lp = slideshow.getLayoutParams();
        lp.width = lp.MATCH_PARENT;
        lp.height = a;
        slideshow.setLayoutParams(lp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}