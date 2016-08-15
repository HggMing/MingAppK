package com.study.mingappk.tab3.villagesituation.villagemaster;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.VillageInfo;
import com.study.mingappk.model.bean.VillageMaster;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 村委
 */
public class VillageMasterActivity extends BackActivity {

    @Bind(R.id.m_x_recyclerview)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    public static String VILLAGE_ID = "the_village_id";
    private String vid;
    private String auth;

    private VillageMasterAdapter mAdapter = new VillageMasterAdapter();
    private XRecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    List<VillageMaster.DataBean.ListBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_master);
        ButterKnife.bind(this);

        initData();
        configXRecyclerView();//XRecyclerView配置
        getDataList();//获取List数据
    }

    private void initData() {
        auth= Hawk.get(APP.USER_AUTH);
        vid = getIntent().getStringExtra(VILLAGE_ID);
        setToolbarTitle("村委");
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
    }

    private void getDataList() {
        MyServiceClient.getService()
                .get_VillageMasterList(auth,vid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VillageMaster>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VillageMaster villageMaster) {
                        mList.addAll(villageMaster.getData().getList());
                        if (mList.isEmpty()||mList==null) {
                            contentEmpty.setVisibility(View.VISIBLE);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }
}
