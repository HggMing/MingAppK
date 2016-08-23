package com.study.mingappk.tab4.mysetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.tab4.mysetting.mypurse.MyPurseActivity;
import com.study.mingappk.tab4.mysetting.shoppingaddress.ShoppingAddressActivity;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySettingActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_mysetting);
    }

    @OnClick({R.id.click_my_order, R.id.click_shopping_address, R.id.click_my_purse})
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
            case R.id.click_shopping_address:
//                Toast.makeText(MySettingActivity.this, "我的收货地址", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(MySettingActivity.this,ShoppingAddressActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
