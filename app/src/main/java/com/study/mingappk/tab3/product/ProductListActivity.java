package com.study.mingappk.tab3.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintButton;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.base.BaseRecyclerViewAdapter;
import com.study.mingappk.common.base.WebViewActivity;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.MyItemDecoration;
import com.study.mingappk.model.bean.ProductList;
import com.study.mingappk.model.event.ProductBuyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Ming on 2016/11/2.
 */

public class ProductListActivity extends BackActivity {
    @Bind(R.id.m_x_recyclerview)
    XRecyclerView mXRecyclerView;
    @Bind(R.id.m_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.tv_price_all)
    TextView tvPriceAll;
    @Bind(R.id.bt_pay)
    TintButton btPay;
    @Bind(R.id.layout_all)
    RelativeLayout layoutAll;
    @Bind(R.id.badge)
    TextView mBadge;

    public static final String VILLAGE_ID = "village_id";

    private String vid;//村id

    private List<ProductList.DataBean.ListBean> mList = new ArrayList<>();
    private ProductListAdapter mAdapter;

    private int page = 1;
    final private static int PAGE_SIZE = 10;

    private SparseArray<ProductList.DataBean.ListBean> buyProductList;//已买商品列表
    private BigDecimal pricePay;//支付价格

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_product_list);
        EventBus.getDefault().register(this);

        vid = getIntent().getStringExtra(VILLAGE_ID);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void config() {
        //设置recyclerview布局
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        //设置adapter
        mAdapter = new ProductListAdapter(this);
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
    }

    private void initData(final int page) {
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_ProductList(auth, vid, page, PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductList>() {
                    @Override
                    public void onCompleted() {
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProductList productList) {
                        mList.addAll(productList.getData().getList());
                        if (mList.isEmpty()) {
                            contentEmpty.setVisibility(View.VISIBLE);
                            contentEmpty.setText(R.string.empty_products);
                        } else {
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(mList);
                    }
                });
    }

    @OnClick(R.id.bt_pay)
    public void onClick() {
        EventBus.getDefault().postSticky(new ProductBuyEvent(pricePay,buyProductList));
        Intent intent = new Intent(this, ProductPayActivity.class);
        startActivity(intent);
        finish();
    }

    //获取购物列表
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getProductBuyList(ProductBuyEvent event) {
        buyProductList = event.getBuyList();
        pricePay = event.getPriceAll();
        String price = pricePay.toString();
        if ("0.00".equals(price)) {
            tvPriceAll.setText("");
            btPay.setEnabled(false);
            mBadge.setVisibility(View.INVISIBLE);
        } else {
            tvPriceAll.setText("￥" + price);
        }
    }

    /**
     * 添加购物商品的动画
     *
     * @param v
     */
    private void BuyGoodAnimation(View v) {
        final ImageView view = new ImageView(this);
        view.setImageResource(R.mipmap.item_image_add);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        int[] location = new int[2];
        v.getLocationOnScreen(location);

        int startx = location[0];
        int starty = location[1] - BaseTools.dip2px(this, 90);

        layoutParams.topMargin = starty;
        layoutParams.leftMargin = startx;
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

        view.setLayoutParams(layoutParams);
        layoutAll.addView(view);

        int[] locationtx = new int[2];

        tvPriceAll.getLocationOnScreen(locationtx);


        Animation translateAnimation = new TranslateAnimation(0, -startx + BaseTools.dip2px(this, 25), 0, 0);
        translateAnimation.setDuration(620);//设置动画持续时间
        translateAnimation.setInterpolator(this, android.R.anim.accelerate_interpolator);//设置动画插入器

        Animation translateAnimation2 = new TranslateAnimation(0, 0, 0, locationtx[1] - starty - BaseTools.dip2px(this, 80));
        translateAnimation2.setDuration(620);//设置动画持续时间
        translateAnimation2.setStartOffset(100);
        translateAnimation2.setInterpolator(this, android.R.anim.accelerate_interpolator);//设置动画插入器

        AnimationSet animatorSet = new AnimationSet(true);
        animatorSet.addAnimation(translateAnimation);
        animatorSet.addAnimation(translateAnimation2);
        animatorSet.setFillAfter(false);//设置动画执行后保持最后状态
        animatorSet.setFillBefore(false);//设置动画执行后不回到原来状态
        view.startAnimation(animatorSet);

        animatorSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
    }

    static class ProductListAdapter extends BaseRecyclerViewAdapter<ProductList.DataBean.ListBean, ProductListAdapter.ViewHolder> {
        ProductListActivity mActivity;

        private SparseArray<ProductList.DataBean.ListBean> buyList = new SparseArray<>();//已买商品列表
        private BigDecimal priceAll = new BigDecimal(0);//商品总价

        ProductListAdapter(ProductListActivity mActivity) {
            this.mActivity = mActivity;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_product, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            Context mContext = holder.itemView.getContext();
            final ProductList.DataBean.ListBean data = mList.get(position);
            //商品名称
            holder.tvName.setText(data.getTitle());
            //月售商品数
            holder.tvMonth.setText("月售" + data.getSale_num());
            //商品价格
            holder.tvPrice.setText("￥" + data.getPrice());
            //商品库存
            holder.tvStock.setText("库存" + data.getNum());
            //星级显示
            int stars = Integer.valueOf(data.getScore());
            holder.ratingBar.setRating(stars);
            //商品图片显示
            String imageUrl = MyServiceClient.getBaseUrl() + data.getPicurl_1();
            Glide.with(mContext).load(imageUrl)
                    .centerCrop()
//                .placeholder(R.mipmap.default_nine_picture)
                    .into(holder.imgProduct);
            //点击item,，进入特产详情页面
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.KEY_TITLE, data.getTitle());
                    intent.putExtra(WebViewActivity.KEY_URL, APP.BASE_URL + data.getLinkurl());
                    mActivity.startActivity(intent);
                }
            });
            //添加购物按钮
            final int productCount = Integer.parseInt(data.getNum());//库存
            if (productCount <= 0) {
                holder.ibAddcount.setVisibility(View.INVISIBLE);
            }

            holder.ibAddcount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.btPay.setEnabled(true);
                    mActivity.BuyGoodAnimation(v);

                    Observable.timer(600, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    int num;
                                    if ("".equals(holder.tvProductCount.getText().toString())) {
                                        num = 0;
                                    } else {
                                        num = Integer.parseInt(holder.tvProductCount.getText().toString());
                                    }
                                    if (num < productCount) {
                                        num++;
                                        mActivity.mBadge.setVisibility(View.VISIBLE);//显示小红点
                                        //总价增加
                                        priceAll = priceAll.add(new BigDecimal(data.getPrice()));
                                    }
                                    if (num == productCount) {
                                        holder.ibAddcount.setVisibility(View.INVISIBLE);
                                    }
                                    holder.ibDelcount.setVisibility(View.VISIBLE);


                                    if (num > 0) {
                                        data.setBuyNum(num);
                                        buyList.put(position, data);
                                    }

                                    holder.tvProductCount.setText(num + "");
                                    EventBus.getDefault().post(new ProductBuyEvent(priceAll, buyList));
                                }
                            });
                }
            });
            //点击减少购物
            holder.ibDelcount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num;
                    if ("".equals(holder.tvProductCount.getText().toString())) {
                        num = 0;
                    } else {
                        num = Integer.parseInt(holder.tvProductCount.getText().toString());
                    }
                    if (num > 0) {
                        num--;
                        holder.ibAddcount.setVisibility(View.VISIBLE);
                        //总价减少
                        priceAll = priceAll.subtract(new BigDecimal(data.getPrice()));
                    }
                    if (num == 0) {
                        holder.ibDelcount.setVisibility(View.INVISIBLE);
                        holder.tvProductCount.setText("");
                        //从购物车删除商品
                        buyList.remove(position);
                    } else {
                        holder.tvProductCount.setText(num + "");
                        //减少购物车中当前商品数量
                        data.setBuyNum(num);
                        buyList.put(position, data);
                    }
                    EventBus.getDefault().post(new ProductBuyEvent(priceAll, buyList));
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.item)
            RelativeLayout item;
            @Bind(R.id.img_product)
            ImageView imgProduct;
            @Bind(R.id.ib_delcount)
            ImageView ibDelcount;
            @Bind(R.id.tv_product_count)
            TextView tvProductCount;
            @Bind(R.id.ib_addcount)
            ImageView ibAddcount;
            @Bind(R.id.tv_name)
            TextView tvName;
            @Bind(R.id.rating_bar)
            RatingBar ratingBar;
            @Bind(R.id.tv_price)
            TextView tvPrice;
            @Bind(R.id.tv_month)
            TextView tvMonth;
            @Bind(R.id.tv_stock)
            TextView tvStock;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}