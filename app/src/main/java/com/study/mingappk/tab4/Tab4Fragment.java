package com.study.mingappk.tab4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.mingappk.R;
import com.study.mingappk.main.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tab4Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.click_about)
    public void onClick() {

    }

    @OnClick({R.id.icon_head, R.id.click_changepwd, R.id.click_identity_binding,
            R.id.click_advice, R.id.click_check_version, R.id.click_about, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_head:
                break;
            case R.id.click_changepwd:
                Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);
                break;
            case R.id.click_identity_binding:
                break;
            case R.id.click_advice:
                break;
            case R.id.click_check_version:
                break;
            case R.id.click_about:
                Intent intent5 = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_exit:
                break;
        }
    }
}
