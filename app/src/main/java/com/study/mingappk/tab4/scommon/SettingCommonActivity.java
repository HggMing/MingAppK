package com.study.mingappk.tab4.scommon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.Dialog_Model;
import com.study.mingappk.tmain.BackActivity;
import com.study.mingappk.tmain.userlogin.LoginActivity;

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
                Dialog_Model.Builder builder = new Dialog_Model.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("清除程序数据和缓存（不会删除下载的图片）？");
                builder.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cacheSize = "5.6MB";
                                try {
                                    cacheSize = DataCleanManager.getFormatSize(
                                            DataCleanManager.getFolderSize(getFilesDir())
                                                    + DataCleanManager.getFolderSize(getCacheDir()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(SettingCommonActivity.this, "清理缓存数据" + cacheSize, Toast.LENGTH_SHORT).show();
                                DataCleanManager.cleanApplicationData(SettingCommonActivity.this);
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                if (!isFinishing()) {
                    builder.create().show();
                }

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
