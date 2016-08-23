package com.study.mingappk.tab4.shop.shoptab1.express;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.ExpressList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.AddListActivity;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ExpressTakeActivity extends AddListActivity {

    private ExpressTakeAdapter mAdapter;
    private List<ExpressList.DataBean.ListBean> mList = new ArrayList<>();
    private final int REQUEST_CODE = 12308;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(R.string.title_activity_express_take);

        config();
        initData();
    }

    private void config() {
        //设置adapter
        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mAdapter = new ExpressTakeAdapter();
        mXRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        String vid = Hawk.get(APP.MANAGER_VID);
        MyServiceClient.getService()
                .get_ExpressList(vid, "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ExpressList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ExpressList expressList) {
                        mList.clear();
                        mList.addAll(expressList.getData().getList());
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_express_take);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @Override
    public void onClick() {
        super.onClick();
        Intent intent = new Intent(this, EditExpressTakeActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                initData();
            }
        }
    }

    static class ExpressTakeAdapter extends BaseRecyclerViewAdapter<ExpressList.DataBean.ListBean, ExpressTakeAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoptab1_express_take, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ExpressList.DataBean.ListBean data = mList.get(position);
            //显示待取件快递单号(圆通  单号：123）
            holder.mTitle.setText(data.getShip() + "\t\t单号：" + data.getNumber());
            //显示代取件发布时间
            String date = data.getCtime();
            String timeFormat = BaseTools.formatDateByFormat(date, "yyyy-MM-dd HH:mm");
            holder.mTime.setText(timeFormat);
            //显示状态信息
            holder.eStatus.setText("未领取");
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.title)
            TextView mTitle;
            @Bind(R.id.time)
            TextView mTime;
            @Bind(R.id.item_layout)
            RelativeLayout mItem;
            @Bind(R.id.express_status)
            TextView eStatus;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
