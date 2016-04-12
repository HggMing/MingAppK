package com.study.mingappk.tab3.villagesituation;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.test.adapter.MyRecyclerViewAdapter;
import com.study.mingappk.test.adapter.MyStaggeredViewAdapter;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VillageSituationActivity extends BackActivity implements VillageSituationAdapter.OnItemClickListener {

    @Bind(R.id.id_recyclerview)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private VillageSituationAdapter mAdapter;

    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;

    private static final int SPAN_COUNT = 3;
    private int flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_situation);
        ButterKnife.bind(this);

        flag = VERTICAL_GRID;
        configRecyclerView(flag);
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
        mAdapter = new VillageSituationAdapter(this);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
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