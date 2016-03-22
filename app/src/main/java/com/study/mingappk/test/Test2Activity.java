package com.study.mingappk.test;

import android.os.Bundle;
import android.widget.ImageView;

import com.study.mingappk.R;
import com.study.mingappk.main.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Test2Activity extends BackActivity {

    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button2)
    public void onClick() {

    }
}
