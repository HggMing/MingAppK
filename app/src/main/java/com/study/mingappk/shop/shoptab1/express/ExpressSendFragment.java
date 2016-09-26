package com.study.mingappk.shop.shoptab1.express;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MyItemDecoration2;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.ExpressList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BaseRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/8/22.
 */
public class ExpressSendFragment extends Fragment implements BaseRecyclerViewAdapter.OnItemClickListener {
    AppCompatActivity mActivity;
    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.m_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private ExpressSendAdapter mAdapter = new ExpressSendAdapter();
    private List<ExpressList.DataBean.ListBean> mList0 = new ArrayList<>();
    private List<ExpressList.DataBean.ListBean> mList1 = new ArrayList<>();

    private int expressStatus;
    private final int REQUEST_CODE = 1231;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4_express_send, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

        configXRecyclerView();//XRecyclerView配置
        initData();//获取数据

        // 刷新时，指示器旋转后变化的颜色
        String theme = APP.getInstance().getTheme(mActivity);
        int themeColorRes = getResources().getIdentifier(theme, "color", mActivity.getPackageName());
        mRefreshLayout.setColorSchemeResources(themeColorRes);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    //配置RecyclerView
    private void configXRecyclerView() {
        mAdapter.setOnItemClickListener(ExpressSendFragment.this);
        mXRecyclerView.setAdapter(mAdapter);//设置adapter
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));//设置布局管理器

        mXRecyclerView.addItemDecoration(new MyItemDecoration2(mActivity));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    public void initData() {
        String vid = Hawk.get(APP.MANAGER_VID);
        expressStatus = getArguments().getInt(ExpressSendActivity.EXPRESS_STATUS);
        if (expressStatus == 0) {//待寄送快递
            mList0.clear();
            MyServiceClient.getService()
                    .get_ExpressList(vid, "5")
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<ExpressList, Observable<ExpressList.DataBean.ListBean>>() {
                        @Override
                        public Observable<ExpressList.DataBean.ListBean> call(ExpressList expressList) {
                            return Observable.from(expressList.getData().getList());
                        }
                    })
                    .filter(new Func1<ExpressList.DataBean.ListBean, Boolean>() {
                        @Override
                        public Boolean call(ExpressList.DataBean.ListBean listBean) {
                            return StringTools.isEmpty(listBean.getNumber());//筛选：没有快递单号
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ExpressList.DataBean.ListBean>() {
                        @Override
                        public void onCompleted() {
                            if (mList0.isEmpty()) {
                                contentEmpty.setVisibility(View.VISIBLE);
                                contentEmpty.setText(R.string.empty_express_send_0);
                            } else {
                                contentEmpty.setVisibility(View.GONE);
                            }
                            mAdapter.setItem(mList0);
                            mRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ExpressList.DataBean.ListBean listBean) {
                            mList0.add(listBean);
                        }
                    });
        } else {//已寄送快递
            mList1.clear();
            MyServiceClient.getService()
                    .get_ExpressList(vid, "5")
                    .subscribeOn(Schedulers.io())
                    .flatMap(new Func1<ExpressList, Observable<ExpressList.DataBean.ListBean>>() {
                        @Override
                        public Observable<ExpressList.DataBean.ListBean> call(ExpressList expressList) {
                            return Observable.from(expressList.getData().getList());
                        }
                    })
                    .filter(new Func1<ExpressList.DataBean.ListBean, Boolean>() {
                        @Override
                        public Boolean call(ExpressList.DataBean.ListBean listBean) {
                            return !StringTools.isEmpty(listBean.getNumber());//已有快递单号，则为已寄送
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ExpressList.DataBean.ListBean>() {
                        @Override
                        public void onCompleted() {
                            if (mList1.isEmpty()) {
                                contentEmpty.setVisibility(View.VISIBLE);
                                contentEmpty.setText(R.string.empty_express_send_1);
                            } else {
                                contentEmpty.setVisibility(View.GONE);
                            }
                            mAdapter.setItem(mList1);
                            mRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ExpressList.DataBean.ListBean listBean) {
                            mList1.add(listBean);
                        }
                    });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mActivity, EditExpressSendActivity.class);
        if (expressStatus == 0) {
            intent.putExtra(EditExpressSendActivity.EXPRESS_SEND_DATA, mList0.get(position));
            getActivity().startActivityForResult(intent, ExpressSendActivity.REQUEST_CODE);//刷新整个activity
        } else {
            intent.putExtra(EditExpressSendActivity.EXPRESS_SEND_DATA, mList1.get(position));
            startActivityForResult(intent, REQUEST_CODE);//只刷新当前fragment
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                initData();
            }
        }
    }

    static class ExpressSendAdapter extends BaseRecyclerViewAdapter<ExpressList.DataBean.ListBean, ExpressSendAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_tab1_express_send, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Context mContext = holder.itemView.getContext();
            ExpressList.DataBean.ListBean data = mList.get(position);

            boolean waitSend = StringTools.isEmpty(data.getNumber());//待寄快递

            if (waitSend) {//待寄件显示，身份证姓名
                holder.mTitle.setText("寄件人: " + data.getUname());
                holder.mTitle2.setVisibility(View.GONE);
            } else {//已寄件，显示快递公司+单号
                holder.mTitle.setText(data.getShip() + "\t\t单号：" + data.getNumber());
                holder.mTitle2.setVisibility(View.VISIBLE);
                holder.mTitle2.setText("寄件人: " + data.getUname());
            }
            //显示寄件信息发布时间
            String date = data.getCtime();
            String timeFormat = BaseTools.formatDateByFormat(date, "yyyy-MM-dd HH:mm");
            holder.mTime.setText(timeFormat);

            //点击编辑
            holder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.mItem, position);
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.title)
            TextView mTitle;
            @Bind(R.id.title2)
            TextView mTitle2;
            @Bind(R.id.time)
            TextView mTime;
            @Bind(R.id.item)
            LinearLayout mItem;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
