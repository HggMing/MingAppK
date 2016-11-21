package com.study.mingappk.tab3.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.bilibili.magicasakura.widgets.TintRadioButton;
import com.bumptech.glide.Glide;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.base.BaseRecyclerViewAdapter;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.alipay.PayUtils;
import com.study.mingappk.common.widgets.dialog.Dialog_InputPwd;
import com.study.mingappk.model.bean.OrderInfo;
import com.study.mingappk.model.bean.ProductList;
import com.study.mingappk.model.bean.ProductNewOrder;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.ResultOther;
import com.study.mingappk.model.bean.ShoppingAddress;
import com.study.mingappk.model.event.ProductBuyEvent;
import com.study.mingappk.tab1.BrowserActivity;
import com.study.mingappk.tab4.mysetting.shoppingaddress.EditShoppingAdressActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ProductPayActivity extends BackActivity {
    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.img_choose_add)
    ImageView imgChooseAdd;
    @Bind(R.id.img_edit_add)
    ImageView imgEditAdd;
    @Bind(R.id.tv_phone_name)
    TextView tvPhoneName;
    @Bind(R.id.tv_add)
    TextView tvAdd;
    @Bind(R.id.tv_orderprice)
    TextView tvOrderprice;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.tv_yu)
    TextView tvYu;
    @Bind(R.id.tv_yu_less)
    TextView tvYuLess;
    @Bind(R.id.rb_pay_yu)
    TintRadioButton rbPayYu;
    @Bind(R.id.ly_pay_yu)
    LinearLayout lyPayYu;
    @Bind(R.id.rb_pay_online)
    TintRadioButton rbPayOnline;
    @Bind(R.id.ly_pay_online)
    LinearLayout lyPayOnline;
    @Bind(R.id.m_scroll)
    NestedScrollView mScroll;
    @Bind(R.id.et_beizhu)
    TintEditText etBeizhu;

    private static final int REQUEST_ADD = 123;

    private ShoppingAddress.DataBean userAddrInfo;//用户地址信息
    public static String KEY_USER_ADDR_INFO = "key_user_addr_info";

    private SparseArray<ProductList.DataBean.ListBean> buyProductList;//已买商品列表
    private BigDecimal pricePay;//支付价格

    private ProductPayAdapter mAdapter;
    private String product;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_pay);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_product_pay);
        EventBus.getDefault().register(this);

        initAddress();//收货人信息
    }

    private void initPay() {
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_Money(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResultOther>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResultOther resultOther) {
                        tvYu.setText("账户余额：￥" + resultOther.getMoney());
                        BigDecimal yuE = new BigDecimal(resultOther.getMoney());
                        if (yuE.compareTo(pricePay) == -1) {//余额小于商品总价
                            tvYuLess.setVisibility(View.VISIBLE);//显示余额不足
                            rbPayOnline.setChecked(true);
                            rbPayYu.setChecked(false);
                            lyPayYu.setClickable(false);
                            lyPayOnline.setClickable(false);
                        } else {
                            tvYuLess.setVisibility(View.GONE);
                            rbPayYu.setChecked(true);
                            rbPayOnline.setChecked(false);
                        }
                    }
                });
    }

    //获取购物列表
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getProductBuyList(ProductBuyEvent event) {
        buyProductList = event.getBuyList();
        pricePay = event.getPriceAll();
        //初始化支付方式
        initPay();

        tvOrderprice.setText("总价￥" + pricePay.toString());
        //配置RecyclerView
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
//        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画

        mAdapter = new ProductPayAdapter();
        mXRecyclerView.setAdapter(mAdapter);//设置adapter

        List<ProductList.DataBean.ListBean> mList = new ArrayList<>();

        StringBuilder productBuilder = new StringBuilder();
        for (int i = 0; i < buyProductList.size(); i++) {
            mList.add(buyProductList.valueAt(i));
            productBuilder.append(mList.get(i).getId()).append(",").append(mList.get(i).getBuyNum()).append("|");
        }
        productBuilder.setLength(productBuilder.length() - 1);
        product = productBuilder.toString();

        mAdapter.setItem(mList);
        mScroll.smoothScrollTo(0, 20);//解决初始显示的可滚动内容没有滚到顶部
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initAddress() {
        //设置数据
        tvPhoneName.setText("用户默认收货信息为空！");
        tvAdd.setText("请点击左边按钮选择已有地址，右边按钮添加新地址。");

        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_ShoppingAddress(auth)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ShoppingAddress, Observable<ShoppingAddress.DataBean>>() {
                    @Override
                    public Observable<ShoppingAddress.DataBean> call(ShoppingAddress shoppingAddress) {
                        return Observable.from(shoppingAddress.getData());
                    }
                })
                .filter(new Func1<ShoppingAddress.DataBean, Boolean>() {//筛选出默认地址
                    @Override
                    public Boolean call(ShoppingAddress.DataBean dataBean) {
                        return "1".equals(dataBean.getIs_def());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShoppingAddress.DataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ShoppingAddress.DataBean dataBean) {
                        if (dataBean != null) {
                            userAddrInfo = dataBean;
                            String phoneName = dataBean.getTel() + "  " + dataBean.getUname();
                            String address = dataBean.getAddr();
                            tvPhoneName.setText(phoneName);
                            tvAdd.setText(address);
                        }
                    }
                });
    }

    @OnClick({R.id.img_choose_add, R.id.img_edit_add, R.id.ly_pay_yu, R.id.ly_pay_online, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_choose_add://选择收货地址
                Intent intent = new Intent(this, ChooseAddressActivity.class);
                startActivityForResult(intent, REQUEST_ADD);
                break;
            case R.id.img_edit_add://添加收货地址
                Intent intent2 = new Intent(this, EditShoppingAdressActivity.class);
                startActivityForResult(intent2, REQUEST_ADD);
                break;
            case R.id.ly_pay_yu://余额支付方式
                rbPayYu.setChecked(true);
                rbPayOnline.setChecked(false);
                break;
            case R.id.ly_pay_online://在线支付方式
                rbPayOnline.setChecked(true);
                rbPayYu.setChecked(false);
                break;
            case R.id.btn_commit://确认下单
                if (StringTools.isEmpty(userAddrInfo.getAddr())) {
                    Toast.makeText(this, "请选择或新增收货人信息！", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String auth = Hawk.get(APP.USER_AUTH);
                String uname = userAddrInfo.getUname();
                String addr = userAddrInfo.getAddr();
                String zCode = userAddrInfo.getZipcode();
                String phone = userAddrInfo.getTel();
                String postscript = etBeizhu.getEditableText().toString();
                String vid = buyProductList.valueAt(0).getVid();

                MyServiceClient.getService()
                        .post_NewOrder(auth, uname, product, addr, addr, addr, addr, zCode, phone, postscript, vid, vid, vid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ProductNewOrder>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ProductNewOrder productNewOrder) {
                                ProductNewOrder.DataBean data = productNewOrder.getData();
                                if (rbPayOnline.isChecked()) {
                                    payOnline(data);//在线支付
                                } else {
                                    payByYu(auth, data.getNo(), data.getTol() + "");//余额支付
                                }
                            }
                        });
                break;
        }
    }

    private void payOnline(ProductNewOrder.DataBean data) {
        PayUtils payUtils = new PayUtils(ProductPayActivity.this, -1);
        payUtils.pay("村特产", "村特产订单", String.valueOf(data.getTol()), data.getNo(), data.getUrl());
    }

    private void payByYu(final String auth, final String orderNo, final String money) {
        final Dialog_InputPwd.Builder pwdDialog = new Dialog_InputPwd.Builder(this);
        pwdDialog.setTitle("交易密码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String pwd = pwdDialog.et_pwd.getEditableText().toString();
                        if (StringTools.isEmpty(pwd)) {
                            Toast.makeText(ProductPayActivity.this, "交易密码不能为空", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        MyServiceClient.getService()
                                .post_PayByYuer(auth, orderNo, pwd, money)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Result>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Result result) {
                                        Toast.makeText(ProductPayActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                        if (result.getErr() == 0) {
                                            //TODO 支付成功，进入我的订单页面
                                        }
                                        dialog.dismiss();
                                    }
                                });
                    }
                }).create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD://新增收货地址or选择收货地址后，界面显示
                    userAddrInfo = data.getParcelableExtra(KEY_USER_ADDR_INFO);
                    String phoneName = userAddrInfo.getTel() + "  " + userAddrInfo.getUname();
                    String address = userAddrInfo.getAddr();
                    tvPhoneName.setText(phoneName);
                    tvAdd.setText(address);
                    break;
            }
        }
    }


    static class ProductPayAdapter extends BaseRecyclerViewAdapter<ProductList.DataBean.ListBean, ProductPayAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_pay, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Context mContext = holder.itemView.getContext();
            ProductList.DataBean.ListBean data = mList.get(position);
            //商品图片显示
            String imageUrl = MyServiceClient.getBaseUrl() + data.getPicurl_1();
            Glide.with(mContext).load(imageUrl)
                    .centerCrop()
//                .placeholder(R.mipmap.default_nine_picture)
                    .into(holder.img);
            //商品名称
            holder.tvContent.setText(data.getTitle());
            //商品价格
            holder.tvPrice.setText("￥" + data.getPrice());
            //购买商品数量
            holder.tvNumber.setText("x" + data.getBuyNum());
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.img)
            ImageView img;
            @Bind(R.id.tv_content)
            TextView tvContent;
            @Bind(R.id.tv_price)
            TextView tvPrice;
            @Bind(R.id.tv_number)
            TextView tvNumber;
            @Bind(R.id.m_item)
            RelativeLayout mItem;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}


