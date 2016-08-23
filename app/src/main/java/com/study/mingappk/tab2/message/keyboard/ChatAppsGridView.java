package com.study.mingappk.tab2.message.keyboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.study.mingappk.R;
import com.study.mingappk.model.bean.ChatAppBean;

import java.util.ArrayList;

public class ChatAppsGridView extends RelativeLayout {
    protected View view;

    public ChatAppsGridView(Context context) {
        this(context, null);
    }

    public ChatAppsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_keyboard_apps, this);
        init();
    }

    protected void init(){
        GridView gv_apps = (GridView) view.findViewById(R.id.gv_apps);
        gv_apps.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gv_apps.setNumColumns(2);
        ArrayList<ChatAppBean> mAppBeanList = new ArrayList<>();
        mAppBeanList.add(new ChatAppBean(R.mipmap.chatting_photo, "图片"));
        mAppBeanList.add(new ChatAppBean(R.mipmap.chatting_camera, "拍照"));
        ChatAppsAdapter adapter = new ChatAppsAdapter(getContext(), mAppBeanList);
        gv_apps.setAdapter(adapter);
    }
}
