package com.study.mingappk.common.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.mingappk.R;


/**
 * 弹出框工具类
 *
 * @author yangzhen
 */
public class DialogUtil {
    private static final String TAG = DialogUtil.class.getSimpleName();

    private static Animation loadingLogoAnimation;
    private static Animation loadingRoundAnimation;

    public static void errorLog(Exception e) {
        if (e == null) {
            return;
        }

        e.printStackTrace();
        Log.e("", "" + e);
    }

    /**
     * 初始化进度条dialog
     *
     * @param activity
     * @return
     */
    public static LoadingPopupWindow initProgressDialog(Activity activity, OnDismissListener onDismissListener) {
        if (activity == null || activity.isFinishing()) {
            return null;
        }

        // 获得背景（6个图片形成的动画）
        //AnimationDrawable animDance = (AnimationDrawable) imgDance.getBackground();

        //final PopupWindow popupWindow = new PopupWindow(popupView, RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
        final LoadingPopupWindow popupWindow = new LoadingPopupWindow(activity);
        ColorDrawable cd = new ColorDrawable(0000);
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(onDismissListener);

        popupWindow.setFocusable(true);
        //animDance.start();
        return popupWindow;
    }

    /**
     * 显示进度条对话框
     *
     * @param activity
     * @param popupWindow
     * @param title
     */
    public static void showProgressDialog(Activity activity, LoadingPopupWindow popupWindow, String title) {
        if ((activity == null || activity.isFinishing()) || (popupWindow == null)) {
            return;
        }

        final LoadingPopupWindow tmpPopupWindow = popupWindow;
        View popupView = popupWindow.getContentView();
        if (popupView != null) {
            TextView tvTitlename = (TextView) popupView.findViewById(R.id.tv_titlename);
            if (tvTitlename != null && !title.isEmpty()) {
                tvTitlename.setText(title);
            }
        }

        if (popupWindow != null && !popupWindow.isShowing()) {
            final View rootView1 = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView1.post(new Runnable() {

                @Override
                public void run() {
                    try {
                        tmpPopupWindow.showAtLocation(rootView1, Gravity.CENTER, 0, 0);
                        tmpPopupWindow.startAnimation();
                    } catch (Exception e) {
                       //(mg) Global.errorLog(e);
                        errorLog(e);
                    }
                }
            });

        }
    }

    /**
     * 隐藏对话框
     *
     * @param popupWindow
     */
    public static void hideDialog(final PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.getContentView().post(new Runnable() {
                @Override
                public void run() {
                    if (popupWindow != null && popupWindow.isShowing())
                        try {
                            popupWindow.dismiss();
                        } catch (Exception e) {
                        }
                }
            });
        }
    }

    /**
     * 立即关闭对话框， 在对话框是用来确认是否关闭某个Activity的时候上面的方法有概率会报错
     *
     * @param popupWindow
     */
    public static void hideDialogNow(PopupWindow popupWindow) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public static class LoadingPopupWindow extends PopupWindow {

        ImageView loadingLogo;
        ImageView loadingRound;

        public LoadingPopupWindow(Activity activity) {
            super(activity.getLayoutInflater().inflate(R.layout.common_loading, null), RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, false);
            this.loadingLogo = (ImageView) getContentView().findViewById(R.id.loading_logo);
            this.loadingRound = (ImageView) getContentView().findViewById(R.id.loading_round);

            if (loadingLogoAnimation == null) {
                loadingLogoAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading_alpha);
            }
            if (loadingRoundAnimation == null) {
                loadingRoundAnimation = AnimationUtils.loadAnimation(activity, R.anim.loading_rotate);
            }
        }

        public void startAnimation() {
            loadingRoundAnimation.setStartTime(500L);//不然会跳帧
            loadingRound.setAnimation(loadingRoundAnimation);
            loadingLogo.startAnimation(loadingLogoAnimation);
        }
    }

}
