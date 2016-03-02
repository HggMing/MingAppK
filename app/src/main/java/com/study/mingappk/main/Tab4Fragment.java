package com.study.mingappk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.study.mingappk.R;
import com.study.mingappk.tab4.TestActivity;


public class Tab4Fragment extends Fragment {

	private AppCompatActivity mActivity;

	public Tab4Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_tab4, container, false);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		/*mActivity = (AppCompatActivity) getActivity();
		super.onViewCreated(view, savedInstanceState);
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_tab4);
		mActivity.setSupportActionBar(toolbar);*/

		initViews();//初始化组件
	}

	private void initViews() {
		View mLogout = getView().findViewById(R.id.btn_exit);
		mLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Class跳转：应用内部跳转
				Intent intent = new Intent(getActivity(), TestActivity.class);
				// intent.getExtras("key","value");//跳转时传的参数
				startActivity(intent);
			}
		});
	}
}
