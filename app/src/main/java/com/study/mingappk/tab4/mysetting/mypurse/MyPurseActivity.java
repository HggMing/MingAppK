package com.study.mingappk.tab4.mysetting.mypurse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.MoneyDetail;
import com.study.mingappk.model.bean.ResultOther;
import com.study.mingappk.model.service.MyServiceClient;
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

public class MyPurseActivity extends BackActivity {

    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    private MyPurseAdapter mAdapter;
    List<MoneyDetail.DataBean.ListBean> mList = new ArrayList<>();
    String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_purse);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_my_purse);

        configXRecyclerView();//XRecyclerView配置
        initDatas();//初始化数据
    }

    private void initDatas() {
        //显示账号余额
        auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_Money(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultOther>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultOther resultOther) {
                        money.setText(resultOther.getMoney());
                    }
                });
        //显示收支明细
        MyServiceClient.getService()
                .get_MoneyDetail(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoneyDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MoneyDetail moneyDetail) {
                        mList.addAll(moneyDetail.getData().getList());
                        if (mList.isEmpty()||mList==null) {
                            contentEmpty.setVisibility(View.VISIBLE);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        //设置布局和adapter
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyPurseAdapter();
        mXRecyclerView.setAdapter(mAdapter);

        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    //点击提现
    @OnClick(R.id.take_money)
    public void onClick() {
        //检测是否设置钱包密码
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_IsSetPWD(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultOther>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultOther resultOther) {
                        if (resultOther.getIs_pwd() == 1) {//已设置密码
//                            Toast.makeText(MyPurseActivity.this, "提现", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyPurseActivity.this, TakeMoneyActivity.class);
                            startActivity(intent);
                        } else {
                            //设置钱包密码
                            Intent intent = new Intent(MyPurseActivity.this, SetPursePwdActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    static class MyPurseAdapter extends BaseRecyclerViewAdapter<MoneyDetail.DataBean.ListBean, MyPurseAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab4_my_purse, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Context mContext = holder.itemView.getContext();
            MoneyDetail.DataBean.ListBean data = mList.get(position);

            //显示奖励类型
            int type = Integer.valueOf(data.getTypes());
            String[] titleName = mContext.getResources().getStringArray(R.array.money_details);
            holder.title.setText(titleName[type]);

            //显示时间
            String date = data.getCtime();
            String timeFormat = BaseTools.getTimeFormat(date);
            holder.time.setText(timeFormat);

            //显示获得方式备注
            holder.content.setText(data.getMemo());
            //显示收支
            if (type == 2 || type == 5) {
                holder.pointsChange.setText("-" + data.getMoney());
                holder.pointsChange.setTextColor(mContext.getResources().getColor(R.color.color08));
            } else {
                holder.pointsChange.setText("+" + data.getMoney());
                holder.pointsChange.setTextColor(mContext.getResources().getColor(R.color.font_green));
            }
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.title)
            TextView title;
            @Bind(R.id.time)
            TextView time;
            @Bind(R.id.content)
            TextView content;
            @Bind(R.id.pointsChange)
            TextView pointsChange;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}


