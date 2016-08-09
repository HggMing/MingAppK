package com.study.mingappk.tab1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.database.InstantMsgModel;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.event.InstantMsgEvent;
import com.study.mingappk.tab2.message.ChatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 动态
 */
public class Tab1Fragment extends Fragment implements Tab1Adapter.OnItemClickListener {
    AppCompatActivity mActivity;
    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    private Tab1Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<InstantMsgModel> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

        configXRecyclerView();//XRecyclerView配置

        initDatas();
        EventBus.getDefault().register(this);
    }

    private void initDatas() {
        mList = MyDB.getQueryAll(InstantMsgModel.class);
        if (mList.isEmpty()) {
            contentEmpty.setVisibility(View.VISIBLE);
        } else {
            contentEmpty.setVisibility(View.GONE);
        }
        mAdapter.setItem(mList);
    }


    /**
     * 更新动态列表
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showInstantMsg(InstantMsgEvent event) {
        if (mList != null) {
            mList.clear();
        }
        mAdapter.clear();
        mList = MyDB.getQueryAll(InstantMsgModel.class);
        if (mList.isEmpty()) {
            contentEmpty.setVisibility(View.VISIBLE);
        } else {
            contentEmpty.setVisibility(View.GONE);
        }
        mAdapter.setItem(mList);
    }


    //配置RecyclerView
    private void configXRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mActivity);
        mAdapter = new Tab1Adapter();
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mAdapter.setOnItemClickListener(this);

        mXRecyclerView.addItemDecoration(new MyItemDecoration2(mActivity));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        //点击动态，进入聊天界面
        Intent intent2=new Intent(mActivity,ChatActivity.class);
        intent2.putExtra(ChatActivity.UID, mList.get(position).getUid());
        intent2.putExtra(ChatActivity.USER_NAME,mList.get(position).getUname());
        startActivity(intent2);
    }

    @Override
    public void onItemLongClick(View view, final int position) {
        //长按删除此条动态
        MyDialog.Builder builder = new MyDialog.Builder(mActivity);
        builder.setTitle("提示")
                .setMessage("删除该聊天？")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //数据库中删除此条动态
                        InstantMsgModel iMsg=new InstantMsgModel();
                        iMsg.setUid( mList.get(position).getUid());
                        MyDB.delete(iMsg);
                        //刷新动态
                        EventBus.getDefault().post(new InstantMsgEvent());
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        if (!mActivity.isFinishing()) {
            builder.create().show();
        }
    }
}
