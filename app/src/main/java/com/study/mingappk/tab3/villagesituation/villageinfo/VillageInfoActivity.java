package com.study.mingappk.tab3.villagesituation.villageinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.VillageInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab3.villagelist.VillageListAdapter;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class VillageInfoActivity extends BackActivity implements BaseRecyclerViewAdapter.OnItemClickListener {

    @Bind(R.id.m_x_recyclerview)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    public static String VILLAGE_ID = "the_village_id";
    public static String TYPE = "the_title_name";
    private String vid;
    private int type;//1、荣誉室2、活动3、村委（Item不同，单独写）4、美食

    private VillageInfoAdapter mAdapter = new VillageInfoAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    List<VillageInfo.DataBean> mList = new ArrayList<>();

    final private static int PAGE_SIZE = 20;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_info);
        ButterKnife.bind(this);

        initData();
        configXRecyclerView();//XRecyclerView配置
        getDataList(page);//获取List数据
    }

    private void initData() {
        vid=getIntent().getStringExtra(VILLAGE_ID);
        type = getIntent().getIntExtra(TYPE, 0);
        switch (type){
            case 1:
                setToolbarTitle("荣誉室");
                break;
            case 2:
                setToolbarTitle("活动");
                break;
            case 4:
                setToolbarTitle("美食");
                break;
        }
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mAdapter.setOnItemClickListener(this);
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mAdapter.setItem(null);
                mList.clear();
                page = 1;
                getDataList(page);
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getDataList(++page);
                mXRecyclerView.loadMoreComplete();
            }
        });
    }

    private void getDataList(int page) {
        MyServiceClient.getService()
                .get_VillageInfoList(vid, type, page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VillageInfo>() {
                    @Override
                    public void call(VillageInfo villageInfo) {
                        mList.addAll(villageInfo.getData());
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(this,VillageInfoDetailActivity.class);
        intent.putExtra(VillageInfoDetailActivity.VILLAGE_INFO_DETAIL,mList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
