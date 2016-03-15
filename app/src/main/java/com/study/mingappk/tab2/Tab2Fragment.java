package com.study.mingappk.tab2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


public class Tab2Fragment extends Fragment {

    @Bind(R.id.toolbar_tab2)
    Toolbar toolbar2;
    @Bind(R.id.pull_to_refresh)
    PtrClassicFrameLayout ptrFrame;
    String imageUrls = "http://imgsrc.baidu.com/forum/pic/item/3ac79f3df8dcd10036d1faba728b4710b8122fdf.jpg";
    @Bind(R.id.img_test)
    ImageView imgTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        AppCompatActivity mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar2);

        super.onViewCreated(view, savedInstanceState);

        Glide.with(this).load(imageUrls).into(imgTest);

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
             */
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            /**
             * 开发者可以通过此方法，确定可以进行下拉刷新的时机。比如列表数据为空，比如列表数据过期，比如嵌套在ViewPager中的某个Fragment中的一个列表数据为空。
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        ptrFrame.setLastUpdateTimeRelateObject(this);

        // 下面这些是默认设置，也可通过xml设置
        ptrFrame.setResistance(1.7f);//抗性，默认1.7f
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
    }

    /**
     * 设置下拉刷新的具体数据
     */
    protected void updateData() {
        ptrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                //具体刷新内容
                ptrFrame.refreshComplete();
            }
        }, 300);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
