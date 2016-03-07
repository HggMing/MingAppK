package com.study.mingappk.userlogin;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.igexin.sdk.PushManager;
import com.study.mingappk.R;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.main.MainActivity;
import com.study.mingappk.userlogin.request.ResultPacket;
import com.study.mingappk.common.app.info.UserInfo;
import com.study.mingappk.userlogin.request.Request_PushReg;
import com.study.mingappk.userlogin.request.Request_UserLogin;
import com.study.mingappk.common.utils.MarketUtils;


public class LoginActivity extends Activity implements OnClickListener {
    private EditText et_name;
    private EditText et_pwd;
    private ImageView img_jzmm;
    private TextView tv_reg;
    private Button btn_login;
    private TextView tv_forgetpwd;

    private String loginname;
    private String loginpwd;

    private boolean status_jzmm;// 是否记住密码

    private String imei;

    private MyApplication application;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private ProgressDialog progressDialog = null;

    private Boolean isresetpassword = false;

    private Boolean isAutoLogin = false;// 是否自动登录
    private Boolean isSetPwd = false;// 是否设置手势密码

    private ImageView img_loginload;//
    private ImageView img_loginload2;
    LayoutParams laParams;

    private ImageView img_left;
    private ImageView img_right;
    int btn_widht = 0;

    private boolean hasMeasured;

    private int imgTop;
    private int imgLeft;
    private int[] imgLoaction = new int[2];

    private TextView tv_login;

    private Button btn_facelogin;

    public void onCreate(Bundle savedInstanceState) {

        isresetpassword = getIntent().getBooleanExtra("resetpassword", false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        MyApplication.getInstance().addActivity(this);

      //  PushManager.getInstance().initialize(this.getApplicationContext());


        sp = getSharedPreferences("setting", MODE_PRIVATE);
        status_jzmm = sp.getBoolean("status_jzmm", true);
        if (!isresetpassword) {
            loginname = sp.getString("loginname", "");
            loginpwd = sp.getString("loginpwd", "");
        } else {
            loginname = "";
            loginpwd = "";
        }

        isAutoLogin = getIntent().getBooleanExtra("isAutoLogin", false);
        isSetPwd = getIntent().getBooleanExtra("isSetPwd", false);

        application = MyApplication.getInstance();
        imei = MarketUtils.getTelImei(this);//targetSdkVersion 23  打开此activity软件会弹窗退出

        initView();
        setView();
        laParams = (LayoutParams) btn_login.getLayoutParams();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在登录,请稍候");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);

    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        img_jzmm = (ImageView) findViewById(R.id.img_jzmm);
        tv_reg = (TextView) findViewById(R.id.tv_reg);
        //btn_login = (Button) findViewById(R.id.btn_login);
        tv_forgetpwd = (TextView) findViewById(R.id.tv_forgetpwd);
        img_loginload = (ImageView) findViewById(R.id.img_loginload);
        img_loginload2 = (ImageView) findViewById(R.id.img_loginload2);
        img_jzmm.setOnClickListener(this);
        tv_reg.setOnClickListener(this);
        //btn_login.setOnClickListener(this);
        tv_forgetpwd.setOnClickListener(this);
        img_left = (ImageView) findViewById(R.id.imgview_btnleft);
        img_right = (ImageView) findViewById(R.id.imgview_btnright);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_facelogin = (Button) findViewById(R.id.btn_facelogin);
        btn_facelogin.setOnClickListener(this);

    }

    private void setView() {
        et_name.setText(loginname);

        if (status_jzmm) {
            img_jzmm.setBackgroundResource(R.drawable.agree);
            et_pwd.setText(loginpwd);
        } else {
            img_jzmm.setBackgroundResource(R.drawable.agree_no);
        }

    }



    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    if (progressDialog != null) {
                        progressDialog.dismiss(); // 关闭进度�?
                        progressDialog.hide();
                    }

                    SetCanEdit(true);

                    UserInfo userInfo = (UserInfo) msg.obj;
                    application.set_userInfo(userInfo);
                    editor = sp.edit();
                    editor.putString("mobile", loginname);

                    PushReg();

                    if (isresetpassword) {
                        // editor.putBoolean("regOK", true);
                        // 是否按了重置密码来重新设置密码
                        editor.putBoolean("isResetPwd", false);
                    }

                    if (status_jzmm) {

                        editor.putString("loginname", loginname);

                        editor.putString("loginpwd", loginpwd);
                        editor.commit();
                    } else {

                        editor.putString("loginname", "");
                        editor.putString("loginpwd", "");
                        editor.commit();
                    }

                    if (isresetpassword) {
                        setResult(RESULT_OK);
                        finish();
                    } else if (isSetPwd) {


                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        LoginActivity.this.finish();

                    }

                    super.handleMessage(msg);
                    break;

                case 444:
                    onClick(btn_login);

                    img_loginload.getLocationInWindow(imgLoaction);

                    MarginLayoutParams margin = new MarginLayoutParams(img_loginload2.getLayoutParams());
                    margin.setMargins(imgLoaction[0], imgLoaction[1] - 40, imgLoaction[0] + margin.width, imgLoaction[1]);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
                    img_loginload2.setLayoutParams(layoutParams);

                    break;


                case 998:
                    if (progressDialog != null) {
                        progressDialog.dismiss(); // 关闭进度�?
                        progressDialog.hide();
                    }
                    SetCanEdit(true);
                    img_loginload.clearAnimation();
                    img_loginload.setVisibility(View.GONE);

                    Animation scaleAnimation2 = new ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    scaleAnimation2.setDuration(10);
                    scaleAnimation2.setFillAfter(true);

                    TranslateAnimation animationLeft = new TranslateAnimation(0,
                            1, 0, 0);
                    animationLeft.setDuration(10);
                    animationLeft.setRepeatCount(0);
                    animationLeft.setFillAfter(true);

                    TranslateAnimation animationRight = new TranslateAnimation(0,
                            -1, 0, 0);
                    animationRight.setDuration(10);
                    animationRight.setRepeatCount(0);
                    animationRight.setFillAfter(true);

                    img_left.startAnimation(animationLeft);
                    img_right.startAnimation(animationRight);

                    btn_login.setText("登录");
                    btn_login.startAnimation(scaleAnimation2);

                    Dialog_Model.Builder builder1 = new Dialog_Model.Builder(
                            LoginActivity.this);
                    builder1.setTitle("提示");
                    builder1.setCannel(false);
                    builder1.setMessage(msg.obj.toString());
                    builder1.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    btn_login.setClickable(true);
                                    btn_login.setText("登录");

                                    Intent intent = new Intent();
                                    //ming		intent.setClass(LoginActivity.this, Activity_CheckPhone.class);
                                    intent.putExtra("loginname", loginname);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }

                            });
                    if (!isFinishing()) {
                        builder1.create().show();
                    }
                    super.handleMessage(msg);
                    break;
                case 999:
                    try {
                        if (progressDialog != null) {
                            progressDialog.dismiss(); // 关闭进度�?
                            progressDialog.hide();
                        }
                        SetCanEdit(true);

                        img_loginload.clearAnimation();
                        img_loginload.setVisibility(View.GONE);

                        Animation scaleAnimation3 = new ScaleAnimation(0f, 1f, 0f,
                                1f, Animation.RELATIVE_TO_SELF, 0.5f,
                                Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation3.setDuration(10);
                        scaleAnimation3.setFillAfter(true);
                        btn_login.setText("登录");

                        TranslateAnimation animationLeft1 = new TranslateAnimation(
                                0, 1, 0, 0);
                        animationLeft1.setDuration(10);
                        animationLeft1.setRepeatCount(0);
                        animationLeft1.setFillAfter(true);

                        TranslateAnimation animationRight1 = new TranslateAnimation(
                                0, -1, 0, 0);
                        animationRight1.setDuration(10);
                        animationRight1.setRepeatCount(0);
                        animationRight1.setFillAfter(true);

                        img_left.startAnimation(animationLeft1);
                        img_right.startAnimation(animationRight1);

                        btn_login.startAnimation(scaleAnimation3);

                        Dialog_Model.Builder builder = new Dialog_Model.Builder(
                                LoginActivity.this);
                        builder.setTitle("提示");
                        builder.setCannel(false);
                        builder.setMessage(msg.obj.toString());
                        builder.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        btn_login.setClickable(true);
                                        btn_login.setText("登录");
                                        dialog.dismiss();
                                    }

                                });
                        if (!isFinishing()) {
                            builder.create().show();
                        }
                    } catch (Exception e) {

                    }

                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private int viewwidth;

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.btn_login:
                btn_login.setClickable(false);
                btn_login.setText("登录中...");

                loginname = et_name.getEditableText().toString();
                if (loginname.equals("")) {
                    btn_login.setClickable(true);
                    btn_login.setText("登录");
                    Toast.makeText(LoginActivity.this, "登录名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!MarketUtils.checkPhone(loginname)) {
                    btn_login.setClickable(true);
                    btn_login.setText("登录");
                    Toast.makeText(LoginActivity.this, "登录名不是标准的手机号码格式", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginpwd = et_pwd.getEditableText().toString();
                if (loginpwd.equals("")) {
                    btn_login.setClickable(true);
                    btn_login.setText("登录");
                    Toast.makeText(LoginActivity.this, "登录密码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                loginThread();
                break;
            case R.id.img_jzmm:
                if (status_jzmm) {
                    status_jzmm = false;
                    editor = sp.edit();
                    editor.putBoolean("status_jzmm", status_jzmm);
                    editor.commit();
                    img_jzmm.setBackgroundResource(R.drawable.agree_no);
                } else {
                    status_jzmm = true;
                    editor = sp.edit();
                    editor.putBoolean("status_jzmm", status_jzmm);
                    editor.commit();
                    img_jzmm.setBackgroundResource(R.drawable.agree);
                }
                break;

//		case R.id.tv_reg:
//			intent = new Intent();
//			intent.setClass(LoginActivity.this, Activity_CheckPhone.class);
//			intent.putExtra("type", 1);
//			startActivity(intent);
//			break;
//		case R.id.tv_forgetpwd:
//			intent = new Intent();
//			intent.setClass(LoginActivity.this, Activity_CheckPhone.class);
//			intent.putExtra("type", 2);
//			startActivity(intent);
//			break;


//		case R.id.btn_facelogin:
//			Intent intent2 = new Intent();
//			intent2.setClass(LoginActivity.this, Activity_FaceLogin.class);
//			startActivity(intent2);
//			overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
//			break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        int[] location = new int[2];
        img_loginload.getLocationOnScreen(location);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                location[0], location[1]);
        //img_loginload2.setLayoutParams(layoutParams);
        //img_loginload2.postInvalidate();
        if (isAutoLogin) {
            Message message = new Message();
            message.what = 444;
            handler.sendMessageDelayed(message, 500);
        }
    }

    /**
     * 界面点击返回键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MyApplication.getInstance().exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void loginThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ResultPacket resultPacket;
                Request_UserLogin request = new Request_UserLogin(
                        LoginActivity.this, loginname, loginpwd, imei);
                resultPacket = request.DealProcess();
                if (!resultPacket.getIsError()) {//如果没有错误，
                    Thread.currentThread().interrupt();//在线程受到阻塞时抛出一个中断信号，这样线程就得以退出 阻塞的状态。
                    Message message = new Message();
                    message.what = 100;
                    message.obj = request.userInfo;
                    handler.sendMessage(message);
                } else {
                    Thread.currentThread().interrupt();
                    if (resultPacket.getResultCode().equals("99")) {
                        Message message = new Message();
                        message.what = 999;
                        message.obj = resultPacket.getDescription();
                        handler.sendMessage(message);
                    } else if (resultPacket.getResultCode()
                            .equals("98")) {
                        Message message = new Message();
                        message.what = 998;
                        message.obj = resultPacket.getDescription();
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();
    }


    private void PushReg() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                ResultPacket resultPacket = new ResultPacket();
                if (MyApplication.getInstance().get_userInfo() != null && MyApplication.getInstance().get_ClientID() != null && MyApplication.getInstance().get_ClientID().length() > 0) {
                    Request_PushReg request = new Request_PushReg(
                            LoginActivity.this, String.valueOf(MyApplication.getInstance()
                            .get_userInfo().uid), "yxj",
                            MyApplication.getInstance().get_ClientID());
                    resultPacket = request.DealProcess();
                }
            }

        }).start();
    }

    private void SetCanEdit(boolean bool) {
        et_name.setEnabled(bool);
        et_name.setClickable(bool);
        et_pwd.setEnabled(bool);
        et_pwd.setClickable(bool);
    }
}
