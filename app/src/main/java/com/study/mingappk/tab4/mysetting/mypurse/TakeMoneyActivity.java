package com.study.mingappk.tab4.mysetting.mypurse;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.dialog.Dialog_InputPwd;
import com.study.mingappk.model.bean.CardList;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.ResultOther;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TakeMoneyActivity extends BackActivity {

    @Bind(R.id.bank_name)
    TextView bankName;
    @Bind(R.id.bank_number)
    TextView bankNumber;
    @Bind(R.id.line_12)
    View line12;
    @Bind(R.id.total_money)
    TextView totalMoney;
    @Bind(R.id.et_money)
    TintEditText etMoney;
    @Bind(R.id.btn_ok)
    Button btnOk;

    private String auth;
    private String bank_name;
    private String bank_no;
    private String bank_user_name;
    private String total_money;
    private String pwd;

    private int mSelectedCard;//默认选择第一张卡
    private int CARD_POINT = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_money);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_take_money);

        selectedCard();
        initData();
    }

    //选择提现到的银行卡
    private void selectedCard() {
        auth = Hawk.get(APP.USER_AUTH);
        mSelectedCard = Hawk.get(APP.SELECTED_CARD, 0);
        //获取银行卡信息
        MyServiceClient.getService()
                .get_CardList(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CardList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CardList cardList) {
                        if (!cardList.getData().isEmpty()&&cardList.getData()!=null) {
                            CardList.DataBean dataBean = cardList.getData().get(mSelectedCard);
                            //显示开户行
                            bank_name = dataBean.getBank_name();
                            bankName.setText(bank_name);
                            //显示卡号后4位
                            bank_no = dataBean.getBank_no();
                            if (bank_no.length() >= 4) {
                                bank_no = bank_no.substring(bank_no.length() - 4, bank_no.length());
                            }
                            bankNumber.setText("尾号" + bank_no + "   的卡");
                            //开户人
                            bank_user_name = dataBean.getBank_true_name();
                        } else {
                            bank_name = "";//用于判定未选卡
                            bankName.setText("未绑定银行卡");
                            bankNumber.setText("请点击此处，添加银行卡");
                        }
                    }
                });
    }

    private void initData() {
        //获取余额信息
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
                        total_money = resultOther.getMoney();
                        totalMoney.setText("当前余额" + total_money + "元");
                    }
                });
        //无输入金额时，按钮无效
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("".equals(s.toString()))
                    btnOk.setEnabled(false);
                else
                    btnOk.setEnabled(true);

            }
        });
    }

    @OnClick({R.id.chose_card, R.id.btn_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chose_card:
//                Toast.makeText(TakeMoneyActivity.this, "选择银行卡", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BindCardActivity.class);
                startActivityForResult(intent, CARD_POINT);
                break;
            case R.id.btn_ok:
//                Toast.makeText(TakeMoneyActivity.this, "确定提现", Toast.LENGTH_SHORT).show();
                if (StringTools.isEmpty(bank_name)) {
                    Toast.makeText(TakeMoneyActivity.this, "请添加并选择要提现到的银行卡", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String money = etMoney.getEditableText().toString();
                double a = Double.valueOf(money);
                if (a <= 0) {
                    Toast.makeText(TakeMoneyActivity.this, "提现金额须大于0元", Toast.LENGTH_SHORT).show();
                    return;
                }
                double b = Double.valueOf(total_money);//剩余总金额
                if (b <= 1000) {
                    if (a > b) {
                        Toast.makeText(TakeMoneyActivity.this, "提现金额不能大于余额" + total_money + "元", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    if (a > 1000) {
                        Toast.makeText(TakeMoneyActivity.this, "提现金额不能大于单次最大限额1000元", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                final Dialog_InputPwd.Builder pwdDialog = new Dialog_InputPwd.Builder(this);
                pwdDialog.setTitle("请输入钱包密码")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pwd = pwdDialog.et_pwd.getEditableText().toString();

                                if (StringTools.isEmpty(pwd)) {
                                    Toast.makeText(TakeMoneyActivity.this, "钱包密码不能为空", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                MyServiceClient.getService()
                                        .post_TakeMoney(auth, money, pwd, bank_name, bank_no, bank_user_name)
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
                                                Toast.makeText(TakeMoneyActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                                if (result.getErr() == 0) {
                                                    finish();
                                                }
                                            }
                                        });
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CARD_POINT) {
            if (resultCode == RESULT_OK) {
                selectedCard();
            }
        }
    }
}
