package com.study.mingappk.tab3.villagesituation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.tab3.villagesituation.villageinfo.VillageInfoActivity;
import com.study.mingappk.tab3.villagesituation.villagemaster.VillageMasterActivity;
import com.study.mingappk.tmain.baseactivity.BackActivity;
import com.study.mingappk.tmain.baseactivity.WebViewActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VillageSituationActivity extends BackActivity implements VillageSituationAdapter.OnItemClickListener {

    @Bind(R.id.id_recyclerview)
    RecyclerView mRecyclerView;

    public static final String VILLAGE_ID = "village_id";

    private RecyclerView.LayoutManager mLayoutManager;
    private VillageSituationAdapter mAdapter;

    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;

    private static final int SPAN_COUNT = 3;
    private int flag;
    private String vid;//村id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_situation);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_village_situation);

        flag = VERTICAL_GRID;
        configRecyclerView(flag);

        vid = getIntent().getStringExtra(VILLAGE_ID);
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
        switch (position) {
            case 0://村况
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TAG, WebViewActivity.TITLE_NAME2);
                intent.putExtra(WebViewActivity.VILLAGE_ID, vid);
                startActivity(intent);
                break;
            case 1://荣誉室
                Intent intent1 = new Intent(this, VillageInfoActivity.class);
                intent1.putExtra(VillageInfoActivity.TYPE, 1);
                intent1.putExtra(VillageInfoActivity.VILLAGE_ID, vid);
                startActivity(intent1);
                break;
            case 2://村官
                Intent intent2 = new Intent(this, VillageMasterActivity.class);
                intent2.putExtra(VillageMasterActivity.VILLAGE_ID, vid);
                startActivity(intent2);
                break;
            case 3://活动
                Intent intent3 = new Intent(this, VillageInfoActivity.class);
                intent3.putExtra(VillageInfoActivity.TYPE, 2);
                intent3.putExtra(VillageInfoActivity.VILLAGE_ID, vid);
                startActivity(intent3);
                break;
            case 4://美食
                Intent intent4 = new Intent(this, VillageInfoActivity.class);
                intent4.putExtra(VillageInfoActivity.TYPE, 4);
                intent4.putExtra(VillageInfoActivity.VILLAGE_ID, vid);
                startActivity(intent4);
                break;
        }
//        Toast.makeText(this, "点击选项", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "长按选项", Toast.LENGTH_SHORT).show();
    }
}