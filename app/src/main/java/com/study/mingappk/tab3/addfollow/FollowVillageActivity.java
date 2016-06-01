package com.study.mingappk.tab3.addfollow;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.Dialog_Model;
import com.study.mingappk.model.bean.QueryVillageList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowVillageActivity extends AppCompatActivity implements FollowVillageAdapter.OnItemClickListener {

    @Bind(R.id.id_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.id_tablayout)
    TabLayout mTabLayout;
    @Bind(R.id.id_appbarlayout)
    AppBarLayout mAppbarLayout;
    @Bind(R.id.id_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.id_coordinatorlayout)
    CoordinatorLayout mCoordinatorLayout;
    @Bind(R.id.toolbar_title2)
    TextView toolbarTitle;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.x_recyclerview)
    RecyclerView mXRecyclerView;

    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    // ViewPager的数据适配器
    private MyViewPagerAdapter mViewPagerAdapter;

    private FollowVillageAdapter mAdapter = new FollowVillageAdapter();
    List<QueryVillageList.DataBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_village);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        toolbarTitle.setText(R.string.title_activity_follow_village);

        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.app_back);
        }
        // 初始化mTitles、mFragments等ViewPager需要的数据
        initData();
        // 对各种控件进行设置、适配、填充数据
        configViews();
        //设置搜索村子
        configSearch();
        //配置RecyclerView
        configXRecyclerView();
        //设置滑动关闭当前，返回上一页
        swipeBack();
    }

    private void swipeBack() {
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setScrimColor(APP.getInstance().getResources().getColor(R.color.swipe_back))//底层阴影颜色
                .setClosePercent(0.6f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(500)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(false)//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
                .addListener(new SwipeListener() {//滑动监听

                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    private void configSearch() {
        //是否使用语音搜索
        searchView.setVoiceSearch(true);
        //自定义光标
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(FollowVillageActivity.this, "搜索提交", Toast.LENGTH_SHORT).show();
                searchVillage(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(FollowVillageActivity.this, "搜索文字改变", Toast.LENGTH_SHORT).show();
//                searchVillage(newText);
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
//                Toast.makeText(FollowVillageActivity.this, "搜索打开", Toast.LENGTH_SHORT).show();
                mAdapter.clear();
                mViewPager.setVisibility(View.VISIBLE);
                mXRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
//                Toast.makeText(FollowVillageActivity.this, "搜索关闭", Toast.LENGTH_SHORT).show();
                mViewPager.setVisibility(View.VISIBLE);
                mXRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void searchVillage(String village) {

        MyServiceClient.getService()
                .getObservable_QueryVillage(village)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryVillageList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QueryVillageList queryVillageList) {
                        if (!queryVillageList.getData().isEmpty()) {
                            mViewPager.setVisibility(View.GONE);
                            mXRecyclerView.setVisibility(View.VISIBLE);
                            mList.clear();
                            mList.addAll(queryVillageList.getData());
                            mAdapter.addNewDatas(mList);
                        } else {
                            Toast.makeText(FollowVillageActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mAdapter.setOnItemClickListener(this);
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

    }

    private void initData() {

        // Tab的标题
        mTitles = new String[]{"推荐", "自选"};

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        mFragments.add(0, new FollowR1Fragment());
        mFragments.add(1, new FollowR2Fragment());
    }

    private void configViews() {

        // 设置显示Toolbar
        setSupportActionBar(mToolbar);
        // 初始化ViewPager的适配器，并设置给它
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 设置ViewPager最大缓存的页面个数
        mViewPager.setOffscreenPageLimit(2);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onItemClick(View view, final int position) {
        String villageName = mList.get(position).getTown_name() + mList.get(position).getVillage_name();
        Dialog_Model.Builder builder = new Dialog_Model.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否要关注" + villageName + "?");
        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        followVillage(position);
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void followVillage(int position) {
        String auth = Hawk.get(APP.USER_AUTH);
        String vid = mList.get(position).getVillage_id();
        MyServiceClient.getService().postCall_FollowVillage(auth, vid)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Result result = response.body();
                            if (result != null) {
                                if (result.getErr() == 0) {
                                    Toast.makeText(FollowVillageActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        private String[] mTitles;
        private List<Fragment> mFragments;

        public MyViewPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
            super(fm);
            this.mTitles = mTitles;
            this.mFragments = mFragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

}
