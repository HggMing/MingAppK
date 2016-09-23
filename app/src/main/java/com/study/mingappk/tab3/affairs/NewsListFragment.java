package com.study.mingappk.tab3.affairs;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.NewsList;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 政务相关：
 */
public class NewsListFragment extends Fragment implements NewsListAdapter.OnItemClickListener {
    @Bind(R.id.g_affairs_list)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.m_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    AppCompatActivity mActivity;

    private NewsListAdapter mAdapter = new NewsListAdapter();
    List<NewsList.DataBean.ListBean> mList = new ArrayList<>();

    final private static int PAGE_SIZE = 20;
    private int page = 1;
    private int type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3_ga, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (AppCompatActivity) getActivity();

        configXRecyclerView();//XRecyclerView配置
        getDataList(page);//获取followList数据和cnt值

        // 刷新时，指示器旋转后变化的颜色
        String theme = APP.getInstance().getTheme(mActivity);
        int themeColorRes = getResources().getIdentifier(theme, "color", mActivity.getPackageName());
        mRefreshLayout.setColorSchemeResources(themeColorRes);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setItem(null);
                mList.clear();
                page = 1;
                getDataList(page);
            }
        });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//设置布局管理器
        mXRecyclerView.addItemDecoration(new MyItemDecoration(mActivity));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mAdapter.setOnItemClickListener(NewsListFragment.this);

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
        String vid = this.getArguments().getString(GovernmentAffairsActivity.VILLAGE_ID);
        type = this.getArguments().getInt(GovernmentAffairsActivity.FRAGMENT_TYPE);
        MyServiceClient.getService()
                .get_NewsList(vid, type, page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsList>() {
                    @Override
                    public void onCompleted() {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsList newsList) {
                        mList.addAll(newsList.getData().getList());
                        if (mList.isEmpty()||mList==null) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            String s = null;
                            switch (type) {
                                case 1:
                                    s="新闻";
                                    break;
                                case 2:
                                    s="政策";
                                    break;
                                case 3:
                                    s="服务";
                                    break;
                                case 4:
                                    s="资讯";
                                    break;
                            }
                            contentEmpty.setText("该村还没有"+s+"相关内容");
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mActivity, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.NEWS_ID, mList.get(position).getId());
        intent.putExtra(NewsDetailActivity.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
