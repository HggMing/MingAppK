package com.study.mingappk.shop.shoptab2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintButton;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.base.BaseRecyclerViewAdapter;
import com.study.mingappk.common.widgets.alipay.PayUtils;
import com.study.mingappk.model.bean.RechargeOrderList;
import com.study.mingappk.shop.shoptab1.books.NoDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 话费充值订单
 */
public class PhoneRechargeOrderActivity extends BackActivity {

    @Bind(R.id.m_x_recyclerview)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.m_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.content_empty)
    TextView contentEmpty;

    private PhoneRechargeOrderAdapter mAdapter;
    private List<RechargeOrderList.DataBean.ListBean> mList = new ArrayList<>();

    private int page = 1;

    private String notify_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_phone_recharge_order);
        config();
        initData(page);

        // 刷新时，指示器旋转后变化的颜色
        String theme = APP.getInstance().getTheme(this);
        int themeColorRes = getResources().getIdentifier(theme, "color", getPackageName());
        mRefreshLayout.setColorSchemeResources(themeColorRes);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.setItem(null);
                mList.clear();
                page = 1;
                initData(page);
            }
        });
    }

    //支付宝调用后刷新订单界面
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAdapter.setItem(null);
        mList.clear();
        page = 1;
        initData(page);
    }

    private void config() {
        //设置recyclerview布局
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mXRecyclerView.addItemDecoration(new NoDecoration(this));//添加空白分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        //设置adapter
        mAdapter = new PhoneRechargeOrderAdapter();
        mXRecyclerView.setAdapter(mAdapter);

        mXRecyclerView.setPullRefreshEnabled(false);
        mXRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                initData(++page);
                mXRecyclerView.loadMoreComplete();
            }
        });

        //点击事件，支付
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RechargeOrderList.DataBean.ListBean data = mList.get(position);
                //图标及文字
                String goods_name = null;
                if ("0".equals(data.getRtype())) {//话费充值
                    goods_name = "话费充值：￥" + data.getAmount();
                } else {//流量充值
                    goods_name = "流量充值：" + data.getAmount();
                }

                PayUtils payUtils = new PayUtils(PhoneRechargeOrderActivity.this, 1);
                payUtils.pay(goods_name, "话费流量",
                        data.getMoney(), data.getId(), notify_url);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initData(final int page) {
        String vid = Hawk.get(APP.MANAGER_VID);
        MyServiceClient.getService()
                .get_RechargeList(vid, 9, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RechargeOrderList>() {
                    @Override
                    public void onCompleted() {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RechargeOrderList rechargeOrderList) {
                        notify_url=rechargeOrderList.getData().getUrl();
                        mList.addAll(rechargeOrderList.getData().getList());
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_orders);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    static class PhoneRechargeOrderAdapter extends BaseRecyclerViewAdapter<RechargeOrderList.DataBean.ListBean, PhoneRechargeOrderAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_tab2_recharge, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Context mContext = holder.itemView.getContext();
            RechargeOrderList.DataBean.ListBean data = mList.get(position);
            //购买人
            holder.tvBuyer.setText(data.getPhone());
            //付款状态：0：未付款 1：已付款 2：充值成功 3：充值失败 7:删除 9:全部(不含删除)
            String payStatus = "未付款";
            switch(data.getPay_status()){
                case "0":
                    payStatus="未付款";
                    holder.layoutButton.setVisibility(View.VISIBLE);
                    break;
                case "1":
                    payStatus="已付款";
                    holder.layoutButton.setVisibility(View.GONE);
                    break;
                case "2":
                    payStatus="充值成功";
                    holder.layoutButton.setVisibility(View.GONE);
                    break;
                case "3":
                    payStatus="充值失败";
                    holder.layoutButton.setVisibility(View.GONE);
                    break;
                case "7":
                    payStatus="删除";
                    holder.layoutButton.setVisibility(View.GONE);
                    break;
            }
            holder.tvStatus.setText(payStatus);
            //图标及文字
            if ("0".equals(data.getRtype())) {//话费充值
                holder.img.setImageResource(R.mipmap.ic_telephone);
                holder.tvContent.setText("话费充值：￥" + data.getAmount());
            } else {//流量充值
                holder.img.setImageResource(R.mipmap.ic_wifi);
                holder.tvContent.setText("流量充值：" + data.getAmount());
            }
            //价格显示
            holder.tvPrice.setText("￥" + data.getMoney());
            holder.tvTotalNumCost.setText("共1件商品，合计:￥" + data.getMoney());
            //支付点击
            holder.btnOrderSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.btnOrderSend, position);
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.tv_buyer)
            TextView tvBuyer;
            @Bind(R.id.tv_status)
            TextView tvStatus;
            @Bind(R.id.img)
            ImageView img;
            @Bind(R.id.tv_content)
            TextView tvContent;
            @Bind(R.id.tv_price)
            TextView tvPrice;
            @Bind(R.id.tv_total_num_cost)
            TextView tvTotalNumCost;
            @Bind(R.id.btn_order_send)
            TintButton btnOrderSend;
            @Bind(R.id.layout_button)
            RelativeLayout layoutButton;
            @Bind(R.id.m_item)
            RelativeLayout mItem;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
