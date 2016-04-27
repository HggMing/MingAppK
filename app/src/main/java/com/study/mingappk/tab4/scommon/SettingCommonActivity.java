package com.study.mingappk.tab4.scommon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.tmain.BackActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingCommonActivity extends BackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_common);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_setting_common);
    }

    @OnClick({R.id.click_change_psw, R.id.click_check_version, R.id.click_clear_cache, R.id.click_advice, R.id.click_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_change_psw:
//                Toast.makeText(SettingCommonActivity.this, "修改密码", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, ChangePwdActivity.class);
                startActivity(intent1);
                break;
            case R.id.click_check_version:
                Toast.makeText(SettingCommonActivity.this, "检查新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_clear_cache:
                Toast.makeText(SettingCommonActivity.this, "清理缓存", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_advice:
//                Toast.makeText(SettingCommonActivity.this, "意见反馈", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(this, AdviceActivity.class);
                startActivity(intent4);
                break;
            case R.id.click_about:
//                Toast.makeText(SettingCommonActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(this, AboutActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
