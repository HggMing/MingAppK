package com.study.mingappk.tab4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.mingappk.R;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.test.TestActivity;
import com.study.mingappk.userlogin.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tab4Fragment extends Fragment {
    AppCompatActivity mActivity;
    @Bind(R.id.toolbar_tab4)
    Toolbar toolbar4;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        sp = mActivity.getSharedPreferences("config", 0);
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar(toolbar4);

        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout_tab4);
        //mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.BLUE);//设置还没收缩时状态下字体颜色
         mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.icon_head, R.id.click_changepwd, R.id.click_identity_binding,
            R.id.click_advice, R.id.click_check_version, R.id.click_about, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_head:
                break;
            case R.id.click_changepwd:
                Intent intent = new Intent(mActivity, TestActivity.class);
                startActivity(intent);
                break;
            case R.id.click_identity_binding:
                break;
            case R.id.click_advice:
                intent = new Intent();
                intent.setClass(mActivity, AdviceActivity.class);
                startActivity(intent);
                break;
            case R.id.click_check_version:
                break;
            case R.id.click_about:
                Intent intent5 = new Intent(mActivity, AboutActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_exit:
                logout();
                break;
        }
    }

    /**
     * 点击退出登录
     */
    private void logout() {
        Dialog_Model.Builder builder = new Dialog_Model.Builder(mActivity);
        builder.setTitle("提示");
        builder.setMessage("确定退出登录？");
        builder.setNegativeButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // MyApplication.getInstance().set_userInfo(null);
                        spEditor = sp.edit();
                        spEditor.putString("loginpwd", "");
                        spEditor.commit();

                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        startActivity(intent);
                        mActivity.finish();
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (!mActivity.isFinishing()) {
            builder.create().show();
        }
    }
}
