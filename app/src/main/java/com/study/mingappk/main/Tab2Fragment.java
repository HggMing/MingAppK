package com.study.mingappk.main;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.study.mingappk.R;
import com.study.mingappk.tab2.SlideShowView;


public class Tab2Fragment extends Fragment {

    private SlideShowView slideshow;
    private AppCompatActivity mActivity;

    public Tab2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_tab2);
        mActivity.setSupportActionBar(toolbar);

        super.onViewCreated(view, savedInstanceState);
        slideshow = (SlideShowView) view.findViewById(R.id.slideshow_tab2);
        WindowManager wm = getActivity().getWindowManager();
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int width = size.x;
        // int width = wm.getDefaultDisplay().getWidth();
        //int height = wm.getDefaultDisplay().getHeight();
        double i = width * 0.5;
        int a = (int) i;
        ViewGroup.LayoutParams lp = slideshow.getLayoutParams();
        lp.width = lp.MATCH_PARENT;
        lp.height = a;
        slideshow.setLayoutParams(lp);
    }
}
