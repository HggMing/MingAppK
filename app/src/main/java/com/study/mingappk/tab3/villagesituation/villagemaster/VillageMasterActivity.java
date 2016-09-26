package com.study.mingappk.tab3.villagesituation.villagemaster;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.VillageMaster;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.shop.shoptab3.AddVillageMasterActivity;
import com.study.mingappk.tmain.baseactivity.BackActivity;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 村委
 */
public class VillageMasterActivity extends BackActivity implements BaseRecyclerViewAdapter.OnItemClickListener {

    @Bind(R.id.m_x_recyclerview)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.m_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    public static String VILLAGE_ID = "the_village_id";
    public static String WHERE_CLICK = "where_click";
    private int where_click = 0;//1、店长点击管理；其他：村圈内点击查看


    private String vid;
    private String auth;
    private int REQUEST_CODE = 2344;


    private VillageMasterAdapter mAdapter = new VillageMasterAdapter();
    List<VillageMaster.DataBean.ListBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_info);
        ButterKnife.bind(this);

        initData();
        configXRecyclerView();//XRecyclerView配置
        getDataList();//获取List数据

        // 刷新时，指示器旋转后变化的颜色
        String theme = APP.getInstance().getTheme(this);
        int themeColorRes = getResources().getIdentifier(theme, "color", getPackageName());
        mRefreshLayout.setColorSchemeResources(themeColorRes);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setItem(null);
                mList.clear();
                getDataList();//获取List数据

            }
        });
    }

    private void initData() {
        auth = Hawk.get(APP.USER_AUTH);
        where_click = getIntent().getIntExtra(WHERE_CLICK, 0);
        if (where_click == 1) {
            vid = Hawk.get(APP.MANAGER_VID);
        } else {
            vid = getIntent().getStringExtra(VILLAGE_ID);
        }
        setToolbarTitle("村委");
    }

    private void configXRecyclerView() {
        //设置fab
        if (where_click == 1) {
            fab.setVisibility(View.VISIBLE);
            fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现
            int themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
            int themeColor2 = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark);
            fab.setColorNormal(themeColor);//fab背景颜色
            fab.setColorPressed(themeColor2);//fab点击后背景颜色
            fab.setColorRipple(themeColor2);//fab点击后涟漪颜色
        } else {
            fab.setVisibility(View.GONE);
        }
        //配置RecyclerView
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mAdapter.setOnItemClickListener(this);

        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
    }

    private void getDataList() {
        MyServiceClient.getService()
                .get_VillageMasterList(auth, vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VillageMaster>() {
                    @Override
                    public void onCompleted() {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VillageMaster villageMaster) {
                        mList.addAll(villageMaster.getData().getList());
                        if (mList.isEmpty() || mList == null) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_village_info);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(this, AddVillageMasterActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {//刷新
                mAdapter.setItem(null);
                mList.clear();
                getDataList();
            }
        }
    }
    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, final int position) {
        if (where_click == 1) {//店长管理里，长按删除
            MyDialog.Builder builder = new MyDialog.Builder(this);
            builder.setMessage("确定删除该条记录?")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteDate(position);
                                    dialog.dismiss();
                                }
                            })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
    }

    /**
     * 删除记录
     */
    private void deleteDate(final int position) {
        String id=mList.get(position).getId();
        String auth=Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .post_DelVillageMaster(auth,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result result) {
                        mList.remove(position);
                        mAdapter.notifyItemRemoved(position + 1);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}
