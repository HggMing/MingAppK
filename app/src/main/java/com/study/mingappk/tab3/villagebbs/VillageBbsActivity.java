package com.study.mingappk.tab3.villagebbs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.bean.BBSList;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.tab3.affairs.GovernmentAffairsActivity;
import com.study.mingappk.tab3.newpost.NewPostActivity;
import com.study.mingappk.tab3.villagebbs.bbsdetail.BbsDetailActivity;
import com.study.mingappk.tab3.villagesituation.VillageSituationActivity;
import com.study.mingappk.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VillageBbsActivity extends BaseActivity implements VillageBbsAdapter.OnItemClickListener {
    public static final String VILLAGE_ID = "village_id";
    public static final String VILLAGE_NAME = "village_name";
    public static final String VILLAGE_PIC = "village_pic";
    public static final String COMMENT_NO_NEW = "comment_number_change";
    public static final String LIKEED_TAG = "liked_tag";//标志已经点赞
    public static final String LIKE_NO_NEW = "like_no+1";//点赞数据+1


    @Bind(R.id.toolbar_bbs)
    Toolbar toolbar;
    @Bind(R.id.tab3_bbs_list)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.collapsing_toolbar_layout_bbs)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.village_image)
    ImageView villageImage;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    private VillageBbsAdapter mAdapter = new VillageBbsAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    private List<BBSList.DataEntity.ListEntity> mList = new ArrayList<>();
    private BBSList.DataEntity.ListEntity bbsDetail;

    final private static int PAGE_SIZE = 5;
    private int page = 1;
    private int ppid;//点击查看详情的帖子号
    private final int REQUEST_LIKE_COMMENT_NUMBER = 2000;
    private final int ReQUEST_AFTER_POST = 2001;

    private String mVid;//村id
    private int themeColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_list);
        ButterKnife.bind(this);
        //设置fab
        fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现
        themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
        int themeColor2 = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark);
        fab.setColorNormal(themeColor);//fab背景颜色
        fab.setColorPressed(themeColor2);//fab点击后背景颜色
        fab.setColorRipple(themeColor2);//fab点击后涟漪颜色

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //设置toolbar后,开启返回图标
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //设备返回图标样式
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_toolbar_back);
        }

        //顶部村名和村图片的加载
        mVid = getIntent().getStringExtra(VILLAGE_ID);
        String villageName = getIntent().getStringExtra(VILLAGE_NAME);
        mCollapsingToolbarLayout.setTitle(villageName);
        Glide.with(this)
                .load(getIntent().getStringExtra(VILLAGE_PIC))
                .priority(Priority.IMMEDIATE)
                .placeholder(R.mipmap.default_village)
                .into(villageImage);

        configXRecyclerView();//XRecyclerView配置
        getBBSList(page);//获取bbsList数据
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        BaseTools.transparentStatusBar2(this);//透明状态栏

        //获取当前app主题的颜色,设置收缩后颜色
        mCollapsingToolbarLayout.setContentScrimColor(themeColor);
        mCollapsingToolbarLayout.setStatusBarScrimColor(themeColor);
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

    private void getBBSList(int page) {
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_BBSList(auth, mVid, page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BBSList>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("mm", e.getMessage());
                    }

                    @Override
                    public void onNext(BBSList bbsList) {
                        if (bbsList != null && bbsList.getErr() == 0) {
//                            mList.addAll(bbsList.getData().getList());
//                            mAdapter.setItem(mList);
                            if (mList.isEmpty() && bbsList.getData().getList().isEmpty()) {
                                contentEmpty.setVisibility(View.VISIBLE);
                            } else {
                                contentEmpty.setVisibility(View.GONE);
                            }
                            mAdapter.setItem(mList, bbsList.getData().getList());
                        }
                    }
                });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        //设置adapter
        mAdapter.setOnItemClickListener(VillageBbsActivity.this);
        mXRecyclerView.setAdapter(mAdapter);
        //设置布局管理器
        mXRecyclerView.setLayoutManager(mLayoutManager);
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST, 30));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        //设置XRecyclerView相关
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
//        mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标
        mXRecyclerView.setPullRefreshEnabled(false);//关闭刷新功能

        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                getBBSList(++page);
                mXRecyclerView.loadMoreComplete();
            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.bbs_item:
//                Toast.makeText(this, "点击整个选项操作", Toast.LENGTH_SHORT).show();
                bbsDetail = mList.get(position);
                ppid = position;
                Intent intent = new Intent(this, BbsDetailActivity.class);
                intent.putExtra(BbsDetailActivity.BBS_DETAIL, bbsDetail);
                startActivityForResult(intent, REQUEST_LIKE_COMMENT_NUMBER);
                break;
            case R.id.bbs_comment:
//                Toast.makeText(this, "点击留言操作", Toast.LENGTH_SHORT).show();
                bbsDetail = mList.get(position);
                ppid = position;
                Intent intent2 = new Intent(this, BbsDetailActivity.class);
                intent2.putExtra(BbsDetailActivity.BBS_DETAIL, bbsDetail);
                intent2.putExtra(BbsDetailActivity.IS_CLICK_COMMENT, true);
                startActivityForResult(intent2, REQUEST_LIKE_COMMENT_NUMBER);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @OnClick({R.id.icon_specialty, R.id.icon_village, R.id.fab, R.id.icon_ga})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_village:
                //点击村况
                Intent intent = new Intent(this, VillageSituationActivity.class);
                intent.putExtra(VillageSituationActivity.VILLAGE_ID, mVid);
                startActivity(intent);
                break;
            case R.id.icon_specialty:
                //点击特产
                break;
            case R.id.icon_ga:
                //点击政务
                Intent intent3 = new Intent(this, GovernmentAffairsActivity.class);
                intent3.putExtra(GovernmentAffairsActivity.VILLAGE_ID, mVid);
                startActivity(intent3);
                break;
            case R.id.fab:
                Intent intent4 = new Intent(this, NewPostActivity.class);
                intent4.putExtra(NewPostActivity.VILLAGE_ID, mVid);
                startActivityForResult(intent4, ReQUEST_AFTER_POST);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ReQUEST_AFTER_POST://发布新帖子后刷新列表
                if (resultCode == RESULT_OK) {
                    mAdapter.setItem(null);
                    mList.clear();
                    page = 1;
                    getBBSList(page);
                }
                break;
            case REQUEST_LIKE_COMMENT_NUMBER://帖子详情页，点赞或评论后，就数据返回显示
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String newCommentNumber = data.getExtras().getString(COMMENT_NO_NEW);
                        if (newCommentNumber != null && !newCommentNumber.isEmpty()) {
                            mList.get(ppid).setNums(newCommentNumber);
                        }
                        int isCliked = data.getExtras().getInt(LIKEED_TAG);
                        if (isCliked == 1) {
                            mList.get(ppid).setMy_is_zan(1);
                            String newLikeNumber = data.getExtras().getString(LIKE_NO_NEW);
                            mList.get(ppid).setZans(newLikeNumber);
                        }
                        mAdapter.setItem(mList);
                    } else {//删除帖子后，刷新列表
                        mAdapter.setItem(null);
                        mList.clear();
                        page = 1;
                        getBBSList(page);
                    }
                }
                break;
        }
    }
}
