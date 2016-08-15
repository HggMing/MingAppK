package com.study.mingappk.tab3.addfollow;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.RecommendVillage;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.databean.FollowTreeData;
import com.study.mingappk.model.service.MyServiceClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class FollowR1Fragment extends Fragment implements FollowR1Adapter.OnItemClickListener {
    @Bind(R.id.tree_recycleview)
    RecyclerView mXRecyclerView;

    AppCompatActivity mActivity;
    private String auth;

    private FollowR1Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<FollowTreeData> mList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3_r1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (AppCompatActivity) getActivity();
        auth = Hawk.get(APP.USER_AUTH);

        configXRecyclerView();//XRecyclerView配置
        getDataList();//获取推荐村圈Country
    }

    private void getDataList() {
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_RecommendVillage(auth)
                .flatMap(new Func1<RecommendVillage, Observable<RecommendVillage.DataBean>>() {
                    @Override
                    public Observable<RecommendVillage.DataBean> call(RecommendVillage recommendVillage) {
                        return Observable.from(recommendVillage.getData());
                    }
                })
                .map(new Func1<RecommendVillage.DataBean, FollowTreeData>() {
                    @Override
                    public FollowTreeData call(RecommendVillage.DataBean dataBean) {
                        FollowTreeData followTreeData = new FollowTreeData();
                        followTreeData.setParent_name(dataBean.getName());
                        followTreeData.setCountry_id(dataBean.getVid());
                        followTreeData.setType(FollowTreeData.ITEM_TYPE_PARENT);
                        return followTreeData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FollowTreeData>() {
                    @Override
                    public void onCompleted() {
                        mAdapter.setItem(mList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FollowTreeData followTreeData) {
                        mList.add(followTreeData);
                    }
                });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new FollowR1Adapter();
        }
        mAdapter.setOnItemClickListener(FollowR1Fragment.this);
        mAdapter.setOnScrollToListener(new FollowR1Adapter.OnScrollToListener() {

            @Override
            public void scrollTo(int position) {
                mXRecyclerView.scrollToPosition(position);
            }
        });
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mXRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
        //设置Item增加、移除动画
//        mXRecyclerView.getItemAnimator().setAddDuration(100);
//        mXRecyclerView.getItemAnimator().setRemoveDuration(100);
//        mXRecyclerView.getItemAnimator().setMoveDuration(200);
//        mXRecyclerView.getItemAnimator().setChangeDuration(100);
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

//        mXRecyclerView.addItemDecoration(new MyItemDecoration(mActivity));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, final int position) {
        String villageName = mList.get(position).getChild_name();
        MyDialog.Builder builder = new MyDialog.Builder(mActivity);
        builder.setTitle("提示")
                .setMessage("是否要关注" + villageName + "?")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                followVillage(position);
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

    private void followVillage(int position) {
        String vid = mList.get(position).getVillage_id();
        MyServiceClient.getService().postCall_FollowVillage(auth, vid)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (response.isSuccessful()) {
                            Result result = response.body();
                            if (result != null) {
                                if (result.getErr() == 0) {
                                    Toast.makeText(mActivity, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    mActivity.setResult(Activity.RESULT_OK);
                                    mActivity.finish();
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
}
