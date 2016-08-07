package com.study.mingappk.tab4.mysetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.ResultOther;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MySettingActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_mysetting);
    }

    @OnClick({R.id.click_my_order, R.id.click_shipping_address, R.id.click_my_purse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_my_purse:
//                Toast.makeText(MySettingActivity.this, "我的钱包", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MySettingActivity.this, MyPurseActivity.class);
                startActivity(intent);
                break;
            case R.id.click_my_order:
                Toast.makeText(MySettingActivity.this, "我的订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_shipping_address:
                Toast.makeText(MySettingActivity.this, "我的收货地址", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
