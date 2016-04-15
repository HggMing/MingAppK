package com.study.mingappk.tab3.villagebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.BBSListResult;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab3.villagesituation.VillageSituationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VillageBbsActivity extends AppCompatActivity implements VillageBbsAdapter.OnItemClickListener {
    public static final String VILLAGE_ID = "village_id";
    public static final String VILLAGE_NAME = "village_name";
    public static final String VILLAGE_PIC = "village_pic";
    @Bind(R.id.toolbar_bbs)
    Toolbar toolbar;
    @Bind(R.id.tab3_bbs_list)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.collapsing_toolbar_layout_bbs)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.village_image)
    ImageView villageImage;

    private VillageBbsAdapter mAdapter;
    private List<BBSListResult.DataEntity.ListEntity> mList;

    private XRecyclerView.LayoutManager mLayoutManager;
    private int cnt;//帖子数
    final private static int PAGE_SIZE = 20;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3_bbs_list);
        ButterKnife.bind(this);
        BaseTools.transparentStatusBar(this);//透明状态栏

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }

        //顶部村名和村图片的加载
        String villageName=getIntent().getStringExtra(VILLAGE_NAME);
        if(villageName.length()>6){
            mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.MyMenuAppearance);
        }
        mCollapsingToolbarLayout.setTitle(villageName);
        Glide.with(this)
                .load(getIntent().getStringExtra(VILLAGE_PIC))
                .priority(Priority.IMMEDIATE)
                .placeholder(R.mipmap.default_village)
                .into(villageImage);

        configXRecyclerView();//XRecyclerView配置
        getBBSList();//获取bbsList数据
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getBBSList() {
        page = 1;
        String auth = APP.getInstance().getAuth();
        String mVid = getIntent().getStringExtra(VILLAGE_ID);
        MyServiceClient.getService().getCall_BBSList(auth, mVid, page, PAGE_SIZE)
                .enqueue(new Callback<BBSListResult>() {
                    @Override
                    public void onResponse(Call<BBSListResult> call, Response<BBSListResult> response) {
                        if (response.isSuccessful()) {
                            BBSListResult bbsListResult = response.body();
                            if (bbsListResult != null && bbsListResult.getErr() == 0) {
                                mList = new ArrayList<>();
                                mList.addAll(bbsListResult.getData().getList());
                                cnt = Integer.parseInt(bbsListResult.getData().getCnt());

                                mAdapter = new VillageBbsAdapter(VillageBbsActivity.this, mList);
                                mAdapter.setOnItemClickListener(VillageBbsActivity.this);
                                mXRecyclerView.setAdapter(mAdapter);//设置adapter
                                page = 2;
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

        mXRecyclerView.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST, 30));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标
        mXRecyclerView.setPullRefreshEnabled(false);//关闭刷新功能

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                int pages = (int) (cnt / PAGE_SIZE + 1);
                if (page <= pages) {
                    String auth = APP.getInstance().getAuth();
                    String mVid = getIntent().getStringExtra(VILLAGE_ID);
                    MyServiceClient.getService().getCall_BBSList(auth, mVid, page, PAGE_SIZE)
                            .enqueue(new Callback<BBSListResult>() {
                                @Override
                                public void onResponse(Call<BBSListResult> call, Response<BBSListResult> response) {
                                    if (response.isSuccessful()) {
                                        BBSListResult bbsListResult = response.body();
                                        if (bbsListResult != null && bbsListResult.getErr() == 0) {
                                            mList.addAll(bbsListResult.getData().getList());
                                            mAdapter.notifyDataSetChanged();
                                            mXRecyclerView.loadMoreComplete();
                                            page++;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BBSListResult> call, Throwable t) {

                                }
                            });
                } else {
                    mXRecyclerView.loadMoreComplete();
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
            default:
                break;

        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @OnClick({R.id.icon_specialty, R.id.icon_village})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_specialty:
                break;
            case R.id.icon_village:
                Intent intent = new Intent(this, VillageSituationActivity.class);
                startActivity(intent);
                break;
        }
    }
}
