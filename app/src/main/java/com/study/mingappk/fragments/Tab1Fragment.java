package com.study.mingappk.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.study.mingappk.R;
import com.study.mingappk.activity.LoginActivity;


public class Tab1Fragment extends Fragment {

    private AppCompatActivity mActivity;

    public Tab1Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_tab1);
        mActivity.setSupportActionBar(toolbar);


        initViews();//初始化组件
    }

    private void initViews() {
        Button button = (Button) getView().findViewById(R.id.button_tab1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LoginActivity.class);
                startActivity(intent);

            }
        });

    }
}
