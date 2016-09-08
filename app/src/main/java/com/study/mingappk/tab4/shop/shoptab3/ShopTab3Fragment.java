package com.study.mingappk.tab4.shop.shoptab3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.study.mingappk.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 本村帖子
 */
public class ShopTab3Fragment extends Fragment {
    AppCompatActivity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_tab3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.item_1, R.id.item_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_1:
                Toast.makeText(mActivity, "帖子自审查", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_2:
                Toast.makeText(mActivity, "违规帖处理", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
