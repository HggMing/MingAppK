package com.study.mingappk.tab4.safesetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SafeSettingActivity extends BackActivity {

    @Bind(R.id.tv_is_binging)
    TextView tvIsBinging;
    @BindColor(android.R.color.holo_red_light)
    int red;
    @BindColor(android.R.color.holo_blue_light)
    int blue;

    private final int REQUEST_IS_REAL_NAME_BINGING=123;//请求实名认证
    private boolean isBinding;//是否实名认证


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_setting);
        ButterKnife.bind(this);

        setToolbarTitle(R.string.title_activity_safe_setting);

        isBinding = Hawk.get(APP.IS_REAL_NAME, false);
        //是否实名认证显示
        if (isBinding) {
            tvIsBinging.setText("已认证");
            tvIsBinging.setTextColor(blue);
        } else {
            tvIsBinging.setText("未认证");
            tvIsBinging.setTextColor(red);
        }
    }

    @OnClick({R.id.click_identity_binding, R.id.click_change_psw, R.id.click_purse_psw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_identity_binding:
                //Toast.makeText(mActivity, "实名认证", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RealNameBindingActivity.class);
                startActivityForResult(intent,REQUEST_IS_REAL_NAME_BINGING);
                break;
            case R.id.click_change_psw:
                //Toast.makeText(SettingCommonActivity.this, "修改登录密码", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, ChangePwdActivity.class);
                startActivity(intent1);
                break;
            case R.id.click_purse_psw:
                //Toast.makeText(SettingCommonActivity.this, "修改钱包密码", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, ChangePursePwdActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IS_REAL_NAME_BINGING:
                if (resultCode == RESULT_OK) {
                    //是否实名认证显示
                    isBinding = Hawk.get(APP.IS_REAL_NAME);
                    if (isBinding) {
                        tvIsBinging.setText("已认证");
                        tvIsBinging.setTextColor(blue);
                    } else {
                        tvIsBinging.setText("未认证");
                        tvIsBinging.setTextColor(red);
                    }
                }
                break;
        }
    }
}
