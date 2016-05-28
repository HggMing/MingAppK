package com.study.mingappk.tab1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.mingappk.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class Tab1Fragment extends Fragment {
    AppCompatActivity mActivity;
    @Bind(R.id.toolbar_tab1)
    Toolbar toolbar1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
//        mActivity.setSupportActionBar(toolbar1);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
