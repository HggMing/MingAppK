package com.study.mingappk.tab4.mysetting;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.model.databean.Test;
import com.study.mingappk.tmain.BackActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySettingActivity extends BackActivity {
    List<Test> tests;

    String name;
    String idnum;
    int i = 1;

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
            case R.id.click_my_order:
                Toast.makeText(MySettingActivity.this, "我的订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_shipping_address:
                Toast.makeText(MySettingActivity.this, "我的收货地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_my_purse:
                Toast.makeText(MySettingActivity.this, "我的钱包", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
