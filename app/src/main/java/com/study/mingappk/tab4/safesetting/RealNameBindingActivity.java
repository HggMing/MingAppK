package com.study.mingappk.tab4.safesetting;

import android.os.Bundle;
import android.widget.Switch;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class RealNameBindingActivity extends BackActivity {

    @Bind(R.id.is_real_name)
    Switch isRealName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_binding);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_real_name_binging);

        boolean isBinging=Hawk.get(APP.IS_REAL_NAME, false);
        if(isBinging){
            isRealName.setChecked(true);
        }else{
            isRealName.setChecked(false);
        }
    }

    @OnCheckedChanged(R.id.is_real_name)
    public void onChecked(boolean checked) {
        if (checked) {
            Hawk.put(APP.IS_REAL_NAME, true);
        } else {
            Hawk.put(APP.IS_REAL_NAME, false);
        }
        setResult(RESULT_OK);
    }
}
