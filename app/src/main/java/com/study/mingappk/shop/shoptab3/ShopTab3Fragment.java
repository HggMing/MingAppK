package com.study.mingappk.shop.shoptab3;

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
import com.study.mingappk.tab3.villagesituation.villageinfo.VillageInfoActivity;
import com.study.mingappk.tab3.villagesituation.villagemaster.VillageMasterActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 本村维护
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

    @OnClick({R.id.item_1, R.id.item_2, R.id.item_3, R.id.item_4, R.id.item_5, R.id.item_6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_1:
                Toast.makeText(mActivity, "帖子审查", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_2:
                Toast.makeText(mActivity, "特产管理", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_3://美食
                Intent intent3 = new Intent(mActivity, VillageInfoActivity.class);
                intent3.putExtra(VillageInfoActivity.TYPE, 4);
                intent3.putExtra(VillageInfoActivity.WHERE_CLICK, 1);
                startActivity(intent3);
                break;
            case R.id.item_4://活动
                Intent intent4 = new Intent(mActivity, VillageInfoActivity.class);
                intent4.putExtra(VillageInfoActivity.TYPE, 2);
                intent4.putExtra(VillageInfoActivity.WHERE_CLICK, 1);
                startActivity(intent4);
                break;
            case R.id.item_5://荣誉
                Intent intent5 = new Intent(mActivity, VillageInfoActivity.class);
                intent5.putExtra(VillageInfoActivity.TYPE, 1);
                intent5.putExtra(VillageInfoActivity.WHERE_CLICK, 1);
                startActivity(intent5);
                break;
            case R.id.item_6://村委
                Intent intent6 = new Intent(mActivity, VillageMasterActivity.class);
                intent6.putExtra(VillageInfoActivity.WHERE_CLICK, 1);
                startActivity(intent6);
                break;
        }
    }
}
