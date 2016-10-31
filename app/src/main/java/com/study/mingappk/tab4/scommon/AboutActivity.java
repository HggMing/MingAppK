package com.study.mingappk.tab4.scommon;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.common.base.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/3/3.
 */
public class AboutActivity extends BackActivity {
    @Bind(R.id.version)
    TextView version;
    @Bind(R.id.about_update)
    TextView aboutUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_about);
       // getSupportActionBar().setTitle("关于我们");
        initAboutActivity();
    }

    /**
     * 用于显示程序版本号
     */
    final void initAboutActivity() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pInfo.versionName;

            String versionString = String.format("版本：%s", versionName);
            version.setText(versionString);

        } catch (Exception e) {
            errorLog(e);
        }
    }

    public static void errorLog(Exception e) {
        if (e == null) {
            return;
        }
        e.printStackTrace();
        Log.e("", "" + e);
    }
}

