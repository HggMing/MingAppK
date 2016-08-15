package com.study.mingappk.tmain.userlogin;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.tmain.MainActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class SplashActivity extends AppCompatActivity {

    public static final int MAX_WAITING_TIME = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseTools.setFullScreen(this);//隐藏状态栏

        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);

        //设置动画
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);//透明度从0.3到不透明变化
        aa.setDuration(MAX_WAITING_TIME);//动画持续时长
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                //android6.0 获取运行时权限
                RequestPermission();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });

    }

    private void RequestPermission() {
        performCodeWithPermission("为了你能正常体验软件，请进行必要的授权！", new PermissionCallback() {
            @Override
            public void hasPermission() {
                //执行获得权限后相关代码
                init();
            }

            @Override
            public void noPermission() {
                MyDialog.Builder builder = new MyDialog.Builder(SplashActivity.this);
                builder.setTitle("提示")
                        .setMessage("我们需要获得储存空间，为你储存个人信息；否则，你将无法正常使用本软件。")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestPermission();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                dialog.dismiss();
                            }
                        });
                if (!isFinishing()) {
                    builder.create().show();
                }
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA);
    }

    private void init() {

        final String loginname = Hawk.get(APP.LOGIN_NAME, "");
        final String loginpwd = Hawk.get(APP.LOGIN_PASSWORD, "");

        if (!loginname.equals("") && !loginpwd.equals("")) {
            goLoginAuto();
        } else {
            goLogin();
        }
        //延迟4s后执行
        /*Observable.timer(4, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (!loginname.equals("") && !loginpwd.equals("")) {
                            goLoginAuto();
                        } else {
                            goLogin();
                        }
                    }
                });*/
    }


    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        this.finish();
    }

    private void goLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 自动登录
     */
    private void goLoginAuto() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.putExtra(LoginActivity.IS_AUTO_LOGIN, true);
        startActivity(intent);
        finish();
    }

    //**************** Android M Permission (Android 6.0权限控制代码封装)*****************************************************
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable;

    public interface PermissionCallback {
        void hasPermission();

        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     *
     * @param permissionDes 权限描述
     * @param runnable      请求权限回调
     * @param permissions   请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions) {
        if (permissions.length == 0) return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            if (permissionRunnable != null) {
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        } else {
            //permission has not been granted.
            requestPermission(permissionDes, permissionRequestCode, permissions);
        }

    }

    private boolean checkPermissionGranted(String[] permissions) {
        boolean flag = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(String permissionDes, final int requestCode, final String[] permissions) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example, if the request has been denied previously.

//            Snackbar.make(getWindow().getDecorView(), requestName,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.common_ok, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ActivityCompat.requestPermissions(BaseAppCompatActivity.this,
//                                    permissions,
//                                    requestCode);
//                        }
//                    })
//                    .show();
            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限

            MyDialog.Builder builder = new MyDialog.Builder(this);
            builder.setTitle("提示")
                    .setCannel(false)
                    .setMessage(permissionDes)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashActivity.this, permissions, requestCode);
                            dialog.dismiss();
                        }
                    });
            if (!isFinishing()) {
                builder.create().show();
            }

        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, requestCode);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                if (permissionRunnable != null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
//                Toast.makeText(SplashActivity.this, "正常体验软件，请在系统设置中，为本APP授权：存储空间！", Toast.LENGTH_SHORT).show();
                if (permissionRunnable != null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //********************** END Android M Permission ****************************************

}
