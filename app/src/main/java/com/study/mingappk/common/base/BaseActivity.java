package com.study.mingappk.common.base;

//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AlertDialog;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.PopupWindow;

//import com.loopj.android.http.RequestParams;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

//import com.study.mingappk.common.base.FootUpdate;
//import com.study.mingappk.R;
//import com.study.mingappk.common.base.DialogUtil;
//import com.study.mingappk.common.base.Global;
//import com.study.mingappk.common.base.GlobalSetting;
//import com.study.mingappk.common.base.ImageLoadTool;
//import com.study.mingappk.common.base.StartActivity;
//import com.study.mingappk.common.base.UnreadNotify;
//import com.study.mingappk.common.base.network.NetworkCallback;
//import com.study.mingappk.common.base.network.NetworkImpl;
//import com.study.mingappk.common.base.umeng.UmengActivity;
//import com.study.mingappk.common.base.util.SingleToast;
//import com.study.mingappk.model.RequestData;
//import com.study.mingappk.user.UserDetailActivity_;


import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cc191954 on 14-8-16.
 * 封装了图片下载并缓存
 */
public class BaseActivity extends AppCompatActivity implements  NetworkCallback,StartActivity {

    //*******************************************************************************************************************************************
    @Override
    public void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException {

    }

    @Override
    public void getNetwork(String uri, String tag) {
    }
    //*******************************************************************************************************************************************
}
