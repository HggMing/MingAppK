package com.study.mingappk.tab4.mysetting.mypurse;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.melnykov.fab.FloatingActionButton;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.CardList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BindCardActivity extends BackActivity {

    @Bind(R.id.m_x_recyclerview)
    RecyclerView mXRecyclerView;
    @Bind(R.id.content_empty)
    TextView contentEmpty;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private BindCardAdapter mAdapter;
    private static final int REFRESH = 1100;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_card);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_bind_card);

        config();
        initData();
    }

    private void config() {
        //设置fab
        fab.attachToRecyclerView(mXRecyclerView);//fab随recyclerView的滚动，隐藏和出现
        int themeColor = ThemeUtils.getColorById(this, R.color.theme_color_primary);
        int themeColor2 = ThemeUtils.getColorById(this, R.color.theme_color_primary_dark);
        fab.setColorNormal(themeColor);//fab背景颜色
        fab.setColorPressed(themeColor2);//fab点击后背景颜色
        fab.setColorRipple(themeColor2);//fab点击后涟漪颜色
        //设置recyclerview布局和adapter
        mXRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BindCardAdapter(this);
        mXRecyclerView.setAdapter(mAdapter);
//        mXRecyclerView.addItemDecoration(new MyItemDecoration(this));//添加分割线
        mXRecyclerView.setHasFixedSize(true);//保持固定的大小,这样会提高RecyclerView的性能
        mXRecyclerView.setItemAnimator(new DefaultItemAnimator());//设置Item增加、移除动画
    }

    private void initData() {
        //设置数据
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_CardList(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CardList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CardList cardList) {
                        if (cardList.getData().isEmpty()||cardList.getData()==null) {
                            type = -1;
                            contentEmpty.setVisibility(View.VISIBLE);
                        } else {
                            type = 0;
                            contentEmpty.setVisibility(View.GONE);
                        }
                        mAdapter.setItem(cardList.getData());
                    }
                });
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Intent intent = new Intent(this, AddCardsActivity.class);
        intent.putExtra(AddCardsActivity.TYPE, type);
        startActivityForResult(intent, REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REFRESH) {
            if (resultCode == RESULT_OK) {
                initData();
                setResult(RESULT_OK);
            }
        }
    }


    static class BindCardAdapter extends BaseRecyclerViewAdapter<CardList.DataBean, BindCardAdapter.ViewHolder> {

        Activity mActivity;

        public BindCardAdapter(Activity mActivity) {
            this.mActivity = mActivity;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab4_cards, parent, false);
            return new ViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final CardList.DataBean data = mList.get(position);

            //显示开户行
            String bank_name = data.getBank_name();
            holder.bankName.setText(bank_name);
            //显示卡号后4位
            String bank_no = data.getBank_no();
            if (bank_no.length() >= 4) {
                bank_no = bank_no.substring(bank_no.length() - 4, bank_no.length());
            }
            holder.bankNumber.setText("**** **** **** " + bank_no);

            final int mSelectedItem = Hawk.get(APP.SELECTED_CARD, 0);
            holder.chose.setChecked(position == mSelectedItem);
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Hawk.put(APP.SELECTED_CARD, position);
                    notifyItemRangeChanged(0, mList.size());

                    mActivity.setResult(Activity.RESULT_OK);
                    mActivity.finish();
                }
            });
            holder.item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //长按解除银行卡绑定
                    MyDialog.Builder builder = new MyDialog.Builder(mActivity);
                    builder.setTitle("提示")
                            .setMessage("解除与该银行卡的绑定？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String auth = Hawk.get(APP.USER_AUTH);
                                    MyServiceClient.getService()
                                            .post_DelCard(auth, data.getId())
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<Result>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onNext(Result result) {
                                                    mList.remove(position);
                                                    notifyItemRemoved(position);
                                                    notifyItemRangeRemoved(position, mList.size() - position);
                                                    //删除卡后，选择的卡序号改变
                                                    if (mSelectedItem > position) {
                                                        Hawk.put(APP.SELECTED_CARD, mSelectedItem - 1);
                                                    } else if (mSelectedItem == position) {
                                                        Hawk.remove(APP.SELECTED_CARD);
                                                        mActivity.setResult(Activity.RESULT_OK);
                                                    }
                                                }
                                            });
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    if (!mActivity.isFinishing()) {
                        builder.create().show();
                    }
                    return true;
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.icon)
            View icon;
            @Bind(R.id.bank_name)
            TextView bankName;
            @Bind(R.id.bank_number)
            TextView bankNumber;
            @Bind(R.id.item)
            CardView item;
            @Bind(R.id.chose)
            RadioButton chose;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

}


