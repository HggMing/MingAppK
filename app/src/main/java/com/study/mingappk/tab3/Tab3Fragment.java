package com.study.mingappk.tab3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.FollowVillageListResult;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.common.utils.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Tab3Fragment extends Fragment implements Tab3Adapter.OnItemClickListener {
    AppCompatActivity mActivity;
    @Bind(R.id.toolbar_tab3)
    Toolbar toolbar3;
    @Bind(R.id.tab3_list)
    XRecyclerView mXRecyclerView;

    private XRecyclerView.LayoutManager mLayoutManager;
    private Tab3Adapter mTab3Adapter;
    List<FollowVillageListResult.DataEntity.ListEntity> followList;
    private int cnt;//关注村圈数
    final private static int PAGE_SIZE = 20;
    private int nowPage = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar3);

        configXRecyclerView();//XRecyclerView配置
    }

    private void configXRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
//        mXRecyclerView.addItemDecoration(new MyItemDecoration(mActivity, MyItemDecoration.VERTICAL_LIST));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
//        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mXRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        //  mXRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);//自定义下拉刷新箭头图标

        followList = new ArrayList<>();
        getFollowVillage();//获取followList数据和cnt值
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                String auth = MyApplication.getInstance().getAuth();
                new MyNetApi().getService().getCall_FollowList(auth, 1, PAGE_SIZE)
                        .enqueue(new Callback<FollowVillageListResult>() {
                            @Override
                            public void onResponse(Call<FollowVillageListResult> call, Response<FollowVillageListResult> response) {
                                if (response.isSuccess()) {
                                    FollowVillageListResult followVillageListResult = response.body();
                                    if (followVillageListResult != null && followVillageListResult.getErr() == 0) {
                                        followList.clear();
                                        followList.addAll(followVillageListResult.getData().getList());
                                        mTab3Adapter.notifyDataSetChanged();
                                        mXRecyclerView.refreshComplete();
                                        nowPage = 2;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<FollowVillageListResult> call, Throwable t) {
                            }
                        });
            }


            @Override
            public void onLoadMore() {
                int pages = (int) (cnt / PAGE_SIZE + 1);
                if (nowPage <= pages) {
                    String auth = MyApplication.getInstance().getAuth();
                    new MyNetApi().getService().getCall_FollowList(auth, nowPage, PAGE_SIZE)
                            .enqueue(new Callback<FollowVillageListResult>() {
                                @Override
                                public void onResponse(Call<FollowVillageListResult> call, Response<FollowVillageListResult> response) {
                                    if (response.isSuccess()) {
                                        FollowVillageListResult followVillageListResult = response.body();
                                        if (followVillageListResult != null && followVillageListResult.getErr() == 0) {
                                            followList.addAll(followVillageListResult.getData().getList());
                                            mTab3Adapter.notifyDataSetChanged();
                                            mXRecyclerView.loadMoreComplete();
                                            nowPage++;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<FollowVillageListResult> call, Throwable t) {

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

        mTab3Adapter = new Tab3Adapter(mActivity, followList);
        mTab3Adapter.setOnItemClickListener(Tab3Fragment.this);
        mXRecyclerView.setAdapter(mTab3Adapter);//设置adapter
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getFollowVillage() {

        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_FollowList(auth, 1, PAGE_SIZE)
                .enqueue(new Callback<FollowVillageListResult>() {
                    @Override
                    public void onResponse(Call<FollowVillageListResult> call, Response<FollowVillageListResult> response) {
                        if (response.isSuccess()) {
                            FollowVillageListResult followVillageListResult = response.body();
                            if (followVillageListResult != null && followVillageListResult.getErr() == 0) {
                                followList.addAll(followVillageListResult.getData().getList());
                                cnt = Integer.parseInt(followVillageListResult.getData().getCnt());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FollowVillageListResult> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onItemClick(View view, int position) {
        //点击选项操作
        Toast.makeText(mActivity, "点击选项操作", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        //长按选项操作
        LongClick(position);
    }

    /**
     * 长按村，取消关注
     */
    private void LongClick(final int position) {
        String villageName = followList.get(position).getVillage_name();
        Dialog_Model.Builder builder = new Dialog_Model.Builder(mActivity);
        builder.setTitle("提示");
        builder.setMessage("取消关注" + villageName + "?");
        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFromServer(position);
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (!mActivity.isFinishing()) {
            builder.create().show();
        }
    }

    /**
     * 发送请求，从服务器取消关注村圈
     *
     * @param position 点击项
     */
    private void removeFromServer(final int position) {
        String vid = followList.get(position).getVillage_id();
        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_DelFollowList(auth, vid).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccess()) {
                    Result result = response.body();
                    if (result != null && result.getErr() == 0) {
                        // Toast.makeText(mActivity, result.getMsg(), Toast.LENGTH_SHORT).show();
                        mTab3Adapter.notifyItemRemoved(position+1);
                        followList.remove(position);
                        mTab3Adapter.notifyItemRangeChanged(position, mTab3Adapter.getItemCount());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }
}
