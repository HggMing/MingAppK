package com.study.mingappk.tab3.villagebbs.likeusers;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.model.bean.ZanList;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LikeUserListActivity extends BackActivity {

    public static String LIKE_USER_COUNT = "like_user_count";//点赞人总数
    public static String LIKE_USER_LIST = "like_user_list";//点赞人列表
    @Bind(R.id.x_recyclerview)
    RecyclerView mXRecyclerView;

    private LikeUserListAdapter mAdapter = new LikeUserListAdapter();
    List<ZanList.DataBean.ListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_user_list);
        ButterKnife.bind(this);
        int count = getIntent().getIntExtra(LIKE_USER_COUNT, 0);
        setToolbarTitle(count + "个人觉得很赞");

        //配置RecyclerView
        configXRecyclerView();

    }

    //配置RecyclerView
    private void configXRecyclerView() {
        List<ZanList.DataBean.ListBean> likeList=getIntent().getParcelableArrayListExtra(LIKE_USER_LIST);
        mList = new ArrayList<>();
        mList.addAll(likeList);
//        mAdapter.setOnItemClickListener(this);
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
        mXRecyclerView.addItemDecoration(new MyItemDecoration2(this));//添加分割线

        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
        mAdapter.setItem(mList);
    }
}
