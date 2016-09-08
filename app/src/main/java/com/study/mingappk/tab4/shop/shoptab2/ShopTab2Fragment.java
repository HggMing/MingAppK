package com.study.mingappk.tab4.shop.shoptab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.tab4.shop.shoptab1.express.ExpressSendActivity;
import com.study.mingappk.tab4.shop.shoptab1.express.ExpressTakeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 村况维护
 */
public class ShopTab2Fragment extends Fragment {
    AppCompatActivity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_tab2, container, false);
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

    @OnClick({R.id.item_1, R.id.item_3, R.id.item_4, R.id.item_5, R.id.item_6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_1:
                Toast.makeText(mActivity, "特产", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3:
                Toast.makeText(mActivity, "美食", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_4:
                Toast.makeText(mActivity, "活动", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_5:
                Toast.makeText(mActivity, "荣誉", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_6:
                Toast.makeText(mActivity, "村委", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
