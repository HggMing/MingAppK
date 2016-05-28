package com.study.mingappk.tmain;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.study.mingappk.R;

import butterknife.Bind;

public class BaseActivity extends AppCompatActivity {

    TextView toolbarTitle;
    Toolbar toolbar;

    public void setToolbarTitle(@StringRes int resid) {
        toolbarTitle.setText(resid);
    }

    public void setToolbarTitle(String s) {
        toolbarTitle.setText(s);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);

        toolbar = (Toolbar) findViewById(R.id.toolbar_activity_base);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        assert toolbar != null;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    private LinearLayout parentLayout;//把父类activity和子类activity的view都add到这里

    /**
     * 初始化contentview
     */
    private void initContentView(int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        if (viewGroup != null) {
            viewGroup.addView(parentLayout);
        }
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LayoutInflater.from(this).inflate(layoutResID, parentLayout, true);
    }

    @Override
    public void setContentView(View view) {
        parentLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        parentLayout.addView(view, params);
    }
}
