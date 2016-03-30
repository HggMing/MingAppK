package com.study.mingappk.tab3;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.Address1Result;
import com.study.mingappk.api.result.FollowVillageListResult;
import com.study.mingappk.common.app.MyApplication;
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
    RecyclerView tab3List;
    @Bind(R.id.tab3_freshlayout)
    SwipeRefreshLayout mFreshlayout;

    private RecyclerView.LayoutManager mLayoutManager;
    private Tab3Adapter mTab3Adapter;

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

        getFollowVillage();

        myRefresh();//刷新设置

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void myRefresh() {
        // 刷新时，指示器旋转后变化的颜色
        mFreshlayout.setColorSchemeResources(R.color.colorAccent, android.R.color.holo_green_dark);
        mFreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新时模拟数据的变化
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFreshlayout.setRefreshing(false);
                        //刷新具体操作
                        getFollowVillage();
                      //  mTab3Adapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
        });
    }

    private void getFollowVillage() {

        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_FollowList(auth,1,20)
                .enqueue(new Callback<FollowVillageListResult>() {
                    @Override
                    public void onResponse(Call<FollowVillageListResult> call, Response<FollowVillageListResult> response) {
                        if(response.isSuccess()){
                            FollowVillageListResult followVillageListResult=response.body();
                            if(followVillageListResult!=null&&followVillageListResult.getErr()==0){
                                List<FollowVillageListResult.DataEntity.ListEntity> folllowList= new ArrayList<>();
                                folllowList.addAll(followVillageListResult.getData().getList());

                                mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                                mTab3Adapter = new Tab3Adapter(mActivity,folllowList);
                                mTab3Adapter.setOnItemClickListener(Tab3Fragment.this);

                                tab3List.setLayoutManager(mLayoutManager);//设置布局管理器
                                tab3List.setAdapter(mTab3Adapter);//设置adapter
                                tab3List.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
                                tab3List.addItemDecoration(new MyItemDecoration(mActivity, MyItemDecoration.VERTICAL_LIST));//添加分割线
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
        Toast.makeText(mActivity,"点击选项操作",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        //长按选项操作
        Toast.makeText(mActivity,"长按选项操作",Toast.LENGTH_SHORT).show();

    }
}
