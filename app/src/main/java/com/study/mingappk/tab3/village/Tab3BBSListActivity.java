package com.study.mingappk.tab3.village;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.study.mingappk.R;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.BBSListResult;
import com.study.mingappk.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab3BBSListActivity extends AppCompatActivity implements Tab3BBSListAdapter.OnItemClickListener {
    @Bind(R.id.toolbar_bbs)
    Toolbar toolbarBbs;
    @Bind(R.id.toolbar_layout_bbs)
    CollapsingToolbarLayout toolbarLayoutBbs;
    @Bind(R.id.tab3_bbs_list)
    XRecyclerView mXRecyclerView;

    private Tab3BBSListAdapter mAdapter;
    private List<BBSListResult.DataEntity.ListEntity> mList;

    private XRecyclerView.LayoutManager mLayoutManager;
    private int cnt;//帖子数
    final private static int PAGE_SIZE = 20;
    private int nowPage = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_bbs_list);
        ButterKnife.bind(this);

        getBBSList();//获取bbsList数据
    }

    private void getBBSList() {
        String auth = MyApplication.getInstance().getAuth();
        String mVid = getIntent().getStringExtra("click_vid");
        new MyServiceClient().getService().getCall_BBSList(auth, mVid, 1, PAGE_SIZE)
                .enqueue(new Callback<BBSListResult>() {
                    @Override
                    public void onResponse(Call<BBSListResult> call, Response<BBSListResult> response) {
                        if (response.isSuccessful()) {
                            BBSListResult bbsListResult = response.body();
                            if (bbsListResult != null && bbsListResult.getErr() == 0) {
                                mList = new ArrayList<>();
                                mList.addAll(bbsListResult.getData().getList());
                                cnt = Integer.parseInt(bbsListResult.getData().getCnt());

                                mAdapter = new Tab3BBSListAdapter(Tab3BBSListActivity.this, mList);
                                mAdapter.setOnItemClickListener(Tab3BBSListActivity.this);
                                mXRecyclerView.setAdapter(mAdapter);//设置adapter
                                configXRecyclerView();//XRecyclerView配置
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BBSListResult> call, Throwable t) {

                    }
                });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                String auth = MyApplication.getInstance().getAuth();
                String mVid = getIntent().getStringExtra("click_vid");
                new MyServiceClient().getService().getCall_BBSList(auth, mVid, 1, PAGE_SIZE)
                        .enqueue(new Callback<BBSListResult>() {
                            @Override
                            public void onResponse(Call<BBSListResult> call, Response<BBSListResult> response) {
                                if (response.isSuccessful()) {
                                    BBSListResult bbsListResult = response.body();
                                    if (bbsListResult != null && bbsListResult.getErr() == 0) {
                                        mList.clear();
                                        mList.addAll(bbsListResult.getData().getList());
                                        mAdapter.notifyDataSetChanged();
                                        mXRecyclerView.refreshComplete();
                                        nowPage = 2;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BBSListResult> call, Throwable t) {

                            }
                        });
            }


            @Override
            public void onLoadMore() {
                int pages = (int) (cnt / PAGE_SIZE + 1);
                if (nowPage <= pages) {
                    String auth = MyApplication.getInstance().getAuth();
                    String mVid = getIntent().getStringExtra("click_vid");
                    new MyServiceClient().getService().getCall_BBSList(auth, mVid, 1, PAGE_SIZE)
                            .enqueue(new Callback<BBSListResult>() {
                                @Override
                                public void onResponse(Call<BBSListResult> call, Response<BBSListResult> response) {
                                    if (response.isSuccessful()) {
                                        BBSListResult bbsListResult = response.body();
                                        if (bbsListResult != null && bbsListResult.getErr() == 0) {
                                            mList.addAll(bbsListResult.getData().getList());
                                            mAdapter.notifyDataSetChanged();
                                            mXRecyclerView.loadMoreComplete();
                                            nowPage++;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BBSListResult> call, Throwable t) {

                                }
                            });
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mXRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                }
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.id_cardview:
                Toast.makeText(this, "点击整个选项操作", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_msg:
                Toast.makeText(this, "点击留言操作", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_like:
                Toast.makeText(this, "点击点赞操作", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;

        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
