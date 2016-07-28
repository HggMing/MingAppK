package com.study.mingappk.tab4;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.jude.utils.JUtils;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.ThemeHelper;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.views.dialog.CardPickerDialog;
import com.study.mingappk.common.views.dialog.Dialog_ChangePwd;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.ApplyInfo;
import com.study.mingappk.model.bean.CheckPhone;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.database.MyDB;
import com.study.mingappk.model.event.ChangeThemeColorEvent;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab4.mysetting.MySettingActivity;
import com.study.mingappk.tab4.safesetting.ListItem1;
import com.study.mingappk.tab4.safesetting.RealNameBindingActivity;
import com.study.mingappk.tab4.safesetting.SafeSettingActivity;
import com.study.mingappk.tab4.scommon.SettingCommonActivity;
import com.study.mingappk.tab4.selfinfo.UserDetailActivity;
import com.study.mingappk.tab4.shop.ApplyShopOwnerActivity;
import com.study.mingappk.tab4.shop.ShowApplyingActivity;
import com.study.mingappk.tmain.userlogin.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SettingFragment extends Fragment implements CardPickerDialog.ClickListener {
    AppCompatActivity mActivity;

    @Bind(R.id.icon_head)
    ImageView iconHead;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sex)
    ImageView sex;
    @Bind(R.id.store_manager)
    ImageView storeManager;
    @Bind(R.id.account_number)
    TextView accountNumber;
    @Bind(R.id.click_store_manager)
    ListItem1 clickShop;


    private boolean isUpdataMyInfo;//是否更新完个人信息
    private UserInfo.DataEntity dataEntity;

    private String auth;
    private int isShopOwner;//是否是店长,1是0不是
    private final int REQUEST_USER_INFO = 122;
    private final int REQUEST_APPLY_PASSED = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        auth = Hawk.get(APP.USER_AUTH);

        isShopOwner = Hawk.get(APP.IS_SHOP_OWNER);

        setHasOptionsMenu(true);
        getUserInfoDetail();//在线获取用户信息

        initView();//界面初始化
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        //是否为店长显示
        if (isShopOwner == 1) {
            clickShop.setText("我的店");
            clickShop.setIcon(R.mipmap.tab4_mystore);
            storeManager.setVisibility(View.VISIBLE);//店长图标
        } else {
            clickShop.setText("申请店长");
            clickShop.setIcon(R.mipmap.tab4_store_manager);
            storeManager.setVisibility(View.GONE);
        }
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_theme) {
            //更换主题
            CardPickerDialog dialog = new CardPickerDialog();
            dialog.setClickListener(this);
            dialog.show(mActivity.getSupportFragmentManager(), CardPickerDialog.TAG);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_USER_INFO:
                if (resultCode == Activity.RESULT_OK) {
                    //修改数据后在线更新个人信息
                    isUpdataMyInfo = Hawk.get(APP.IS_UPDATA_MY_INFO, false);
                    if (!isUpdataMyInfo) {
                        getUserInfoDetail();
                    }
                }
                break;
            case REQUEST_APPLY_PASSED:
                if (resultCode == Activity.RESULT_OK) {
                    isShopOwner = 1;
                    clickShop.setText("我的店");
                    clickShop.setIcon(R.mipmap.tab4_mystore);
                    storeManager.setVisibility(View.VISIBLE);//店长图标
                }
        }
    }

    /**
     * 修改密码,使用对话框方式
     */

    private void changePwd() {
        final Dialog_ChangePwd.Builder pwddialog = new Dialog_ChangePwd.Builder(mActivity);
        pwddialog.setTitle("修改登录密码");

        pwddialog
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldpwd = pwddialog.et_oldpwd.getEditableText()
                                .toString();
                        String newpwd1 = pwddialog.et_newpwd1.getEditableText()
                                .toString();
                        String newpwd2 = pwddialog.et_newpwd2.getEditableText()
                                .toString();

                        if (oldpwd.equals("")) {
                            Toast.makeText(mActivity, "旧密码不能为空",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (newpwd1.equals("")) {
                            Toast.makeText(mActivity, "新密码不能为空",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (newpwd2.equals("")) {
                            Toast.makeText(mActivity, "确认密码不能为空",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!newpwd1.equals(newpwd2)) {
                            Toast.makeText(mActivity, "两次输入密码不一致",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (newpwd1.length() < 6 || newpwd1.length() > 16) {
                            Toast.makeText(mActivity, "密码必须在6-16位",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                        MyServiceClient.getService().getCall_ChangePwd(auth, oldpwd, newpwd1)
                                .enqueue(new Callback<Result>() {
                                    @Override
                                    public void onResponse(Call<Result> call, Response<Result> response) {
                                        if (response.isSuccessful()) {
                                            Result changePwdResult = response.body();
                                            if (changePwdResult != null) {
                                                MyDialog.Builder builder2 = new MyDialog.Builder(mActivity);
                                                builder2.setTitle("提示")
                                                        .setCannel(false)
                                                        .setMessage(changePwdResult.getMsg())
                                                        .setNegativeButton("确定",
                                                                new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog,
                                                                                        int which) {
                                                                        dialog.dismiss();
                                                                    }

                                                                });
                                                if (!mActivity.isFinishing()) {
                                                    builder2.create().show();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Result> call, Throwable t) {
                                        Toast.makeText(mActivity, "修改密码失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /**
     * 点击退出登录
     */
    private void logout() {
        MyDialog.Builder builder = new MyDialog.Builder(mActivity);
        builder.setTitle("提示")
                .setMessage("确定退出登录？")
                .setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Hawk.chain()
                                        .put(APP.LOGIN_PASSWORD, "")
                                        .put(APP.IS_UPDATA_MY_INFO, false)
                                        .commit();
                                Hawk.remove(APP.ME_UID);
                                //停止个推SDK服务
                                PushManager.getInstance().stopService(mActivity.getApplicationContext());
                                //关闭数据库
                                MyDB.createDb(mActivity).close();
                                MyDB.setLiteOrm(null);

                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                                dialog.dismiss();
                            }
                        })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        if (!mActivity.isFinishing()) {
            builder.create().show();
        }
    }

    /**
     * 获取用户信息
     */
    public void getUserInfoDetail() {
        Call<UserInfo> call = MyServiceClient.getService().getCall_UserInfo(auth);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo userInfo = response.body();
                    if (userInfo != null && userInfo.getErr() == 0) {
                        dataEntity = userInfo.getData();
                        String headUrl = MyServiceClient.getBaseUrl() + dataEntity.getHead();
                        Hawk.put(APP.ME_HEAD, headUrl);
                        String uName = dataEntity.getUname();
                        String sexNumber = dataEntity.getSex();
                        String accountNo = dataEntity.getLogname();
                        //头像
                        Glide.with(mActivity)
                                .load(headUrl)
                                .bitmapTransform(new CropCircleTransformation(mActivity))
                                .error(R.mipmap.defalt_user_circle)
                                .into(iconHead);
                        //昵称
                        if (uName.isEmpty()) {
                            String iphone = dataEntity.getPhone();
                            String showName = iphone.substring(0, 3) + "****" + iphone.substring(7, 11);
                            name.setText(showName);
                        } else {
                            name.setText(uName);
                        }

                        //性别
                        if ("0".equals(sexNumber)) {
                            sex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_sex_boy));
                        } else {
                            sex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_sex_girl));
                        }
                        accountNumber.setText("账号：" + accountNo);

                        Hawk.put(APP.IS_UPDATA_MY_INFO, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                JUtils.Log("获取用户信息失败：" + t.getMessage());
            }
        });

    }

    @OnClick({R.id.click_user, R.id.click_safe_center, R.id.click_my_setting, R.id.click_setting_common, R.id.click_store_manager, R.id.click_loyout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_user:
                Intent intent1 = new Intent(mActivity, UserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(UserDetailActivity.USER_INFO, dataEntity);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, REQUEST_USER_INFO);
                break;
            case R.id.click_safe_center:
//                Toast.makeText(mActivity, "账号安全", Toast.LENGTH_SHORT).show();
                Intent intent5 = new Intent(mActivity, SafeSettingActivity.class);
                startActivity(intent5);
                break;
            case R.id.click_my_setting:
//                Toast.makeText(mActivity, "我的", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(mActivity, MySettingActivity.class);
                startActivity(intent2);
                break;
            case R.id.click_setting_common:
//                Toast.makeText(mActivity, "通用", Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(mActivity, SettingCommonActivity.class);
                startActivity(intent3);
                break;
            case R.id.click_store_manager:
                if (isShopOwner == 1) {
                    Toast.makeText(mActivity, "进入店长管理页面", Toast.LENGTH_SHORT).show();
                } else {
                    //获取申请店长，当前状态
                    getApplyStatus();
                }
                break;
            case R.id.click_loyout:
//                Toast.makeText(mActivity, "退出当前账号", Toast.LENGTH_SHORT).show();
                logout();
                break;
        }
    }

    private void getIsBinding() {
        //1)将除图片外的参数以及机构key组成一个字符串(注意顺序)
        String phone = Hawk.get(APP.LOGIN_NAME);
        String other = "compid=9&phone=" + phone;
        String str = other + "&key=69939442285489888751746749876227";
        //2)使用MD5算法加密上述字符串
        String sign = BaseTools.md5(str);
        //3)最终得到参数字符串：（注意，KEY参数不带到参数列表,sign参数加入参数列表）
        String str2 = other + "&sign=" + sign;
        //4)把上述字符串做base64加密，最终得到请求:
        String paraString = Base64.encodeToString(str2.getBytes(), Base64.NO_WRAP);
        RequestBody data = RequestBody.create(MediaType.parse("text/plain"), paraString);

        MyServiceClient.getService()
                .post_IsRealBinding(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String s = null;
                        try {
                            s = responseBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        CheckPhone result = gson.fromJson(new String(Base64.decode(s, Base64.DEFAULT)), CheckPhone.class);
                        if (result.getErr() == 0) {//已经实名认证,进入申请界面
                            Intent intent4 = new Intent(mActivity, ApplyShopOwnerActivity.class);
                            intent4.putExtra(ApplyShopOwnerActivity.USER_INFO, dataEntity);
                            startActivity(intent4);
                        }
                        if (result.getErr() == 1002) {//还没有实名认证
                            MyDialog.Builder builder1 = new MyDialog.Builder(mActivity);
                            builder1.setTitle("提示")
                                    .setMessage("你的账号尚未实名认证，请先进行实名认证。")
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            Intent intent5 = new Intent(mActivity, RealNameBindingActivity.class);
                                            startActivity(intent5);
                                            dialog.dismiss();
                                        }
                                    })
                                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            if (!mActivity.isFinishing()) {
                                builder1.create().show();
                            }
                        }
                    }
                });
    }

    private void getApplyStatus() {
        String vid = Hawk.get(APP.APPLY_INFO_VID + dataEntity.getUid());
        if (vid == null || vid.isEmpty()) {//假如没有申请过
            getIsBinding();//获取是否实名认证
        } else {
            MyServiceClient.getService()
                    .get_IsApply(auth, vid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ApplyInfo>() {
                        @Override
                        public void call(ApplyInfo applyInfo) {
                            ApplyInfo.DataBean data = applyInfo.getData();
                            if (data != null) {//已申请过，进入查看申请状态页面
                                Intent intent = new Intent(mActivity, ShowApplyingActivity.class);
                                intent.putExtra(ShowApplyingActivity.STATUS_APPLY, data.getStats());
                                startActivityForResult(intent, REQUEST_APPLY_PASSED);
                            }
                        }
                    });
        }
    }

    @Override
    public void onConfirm(int currentTheme) {
        if (ThemeHelper.getTheme(mActivity) != currentTheme) {
            ThemeHelper.setTheme(mActivity, currentTheme);
            ThemeUtils.refreshUI(mActivity, new ThemeUtils.ExtraRefreshable() {
                @Override
                public void refreshGlobal(Activity activity) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        ActivityManager.TaskDescription taskDescription = new ActivityManager
                                .TaskDescription(null, null, ThemeUtils.getThemeAttrColor(mActivity, android.R.attr.colorPrimary));
                        mActivity.setTaskDescription(taskDescription);
                        mActivity.getWindow().setStatusBarColor(ThemeUtils.getColorById(mActivity, R.color.theme_color_primary));
                    }
                }

                @Override
                public void refreshSpecificView(View view) {
                }
            });
            //通知MainActivity更换主题
            EventBus.getDefault().post(new ChangeThemeColorEvent(0));
        }
    }
}
