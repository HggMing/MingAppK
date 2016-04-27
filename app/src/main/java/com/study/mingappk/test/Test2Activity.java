package com.study.mingappk.test;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.test.adapter.MyRecyclerViewAdapter;
import com.study.mingappk.test.adapter.MyStaggeredViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Test2Activity extends BackActivity implements MyRecyclerViewAdapter.OnItemClickListener, MyStaggeredViewAdapter.OnItemClickListener {

    @Bind(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    @Bind(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private MyStaggeredViewAdapter mStaggeredAdapter;

    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;

    private static final int SPAN_COUNT = 2;
    private int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_test2);

        flag = VERTICAL_LIST;
        configRecyclerView(flag);
//      刷新设置
        myRefresh();
    }

    private void myRefresh() {
        // 刷新时，指示器旋转后变化的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.cardview_dark_background);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新时模拟数据的变化
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        int temp = (int) (Math.random() * 10);
                        if (flag != STAGGERED_GRID) {
                            mRecyclerViewAdapter.mDatas.add(0, "new" + temp);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            mStaggeredAdapter.mDatas.add(0, "new" + temp);
                            mStaggeredAdapter.mHeights.add(0, (int) (Math.random() * 300) + 200);
                            mStaggeredAdapter.notifyDataSetChanged();
                        }
                    }
                }, 1000);
            }
        });
    }

    private void configRecyclerView(int flag) {

        switch (flag) {
            case VERTICAL_LIST:
                mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(this, SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        if (flag != STAGGERED_GRID) {
            mRecyclerViewAdapter = new MyRecyclerViewAdapter(this);
            mRecyclerViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        } else {
            mStaggeredAdapter = new MyStaggeredViewAdapter(this);
            mStaggeredAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mStaggeredAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "点击选项", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "长按选项", Toast.LENGTH_SHORT).show();
    }
}
