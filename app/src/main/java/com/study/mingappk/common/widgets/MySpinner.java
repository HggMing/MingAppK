package com.study.mingappk.common.widgets;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.study.mingappk.R;


/**
 * 自定义外观spinner
 */
public class MySpinner extends FrameLayout {
    private Spinner mSpinner;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<CharSequence> mCAdapter;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.view_my_spinner, this);
        LinearLayout mSpAll = (LinearLayout) findViewById(R.id.sp_all);
        mSpinner = (Spinner) findViewById(R.id.sp_in);

        //点击组合布局，触发spinner的点击
        mSpAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpinner.performClick();
            }
        });
    }

    public Spinner getSpinner() {
        return mSpinner;
    }
    public ArrayAdapter<String> getAdapter(Context context) {
        if (mAdapter == null) {
            //将可选内容与ArrayAdapter连接起来
            mAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Adapter与Spinner控件绑定
            mSpinner.setAdapter(mAdapter);
            mSpinner.setSelection(0, true);//默认选择第一条
        }
        return mAdapter;
    }
    public ArrayAdapter<CharSequence> getAdapter(Context context, @ArrayRes int textArrayResId) {
        if (mCAdapter == null) {
            //将可选内容与ArrayAdapter连接起来
            mCAdapter =  ArrayAdapter.createFromResource(context, textArrayResId, android.R.layout.simple_spinner_item);
            //设置下拉列表的风格
            mCAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Adapter与Spinner控件绑定
            mSpinner.setAdapter(mCAdapter);
            mSpinner.setSelection(0, true);//默认选择第一条
        }
        return mCAdapter;
    }
}
