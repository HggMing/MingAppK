package com.study.mingappk.tab4;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jude.utils.JUtils;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.Dialog_ChangePwd;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab4.mysetting.MySettingActivity;
import com.study.mingappk.tab4.safesetting.ListItem1;
import com.study.mingappk.tab4.safesetting.RealNameBindingActivity;
import com.study.mingappk.tab4.safesetting.SafeSettingActivity;
import com.study.mingappk.tab4.scommon.SettingCommonActivity;
import com.study.mingappk.tab4.selfinfo.UserDetailActivity;
import com.study.mingappk.tab4.shop.ApplyShopOwnerActivity;
import com.study.mingappk.tmain.userlogin.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragment extends Fragment {
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
    private boolean isBinding;//是否实名认证
    private final int REQUEST_USER_INFO = 122;

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

        getUserInfoDetail();//在线获取用户信息

        initView();//界面初始化
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
                isBinding = Hawk.get(APP.IS_REAL_NAME, false);
                if (isShopOwner == 1) {
                    Toast.makeText(mActivity, "进入店长管理页面", Toast.LENGTH_SHORT).show();
                } else if (isBinding) {
                    Intent intent4 = new Intent(mActivity, ApplyShopOwnerActivity.class);
                    startActivity(intent4);
                } else {
                    MyDialog.Builder builder1 = new MyDialog.Builder(mActivity);
                    builder1.setTitle("提示")
                            .setCannel(false)
                            .setMessage("你的账号尚未实名认证，请先进行实名认证。")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent5 = new Intent(mActivity, RealNameBindingActivity.class);
                                    startActivity(intent5);
                                    dialog.dismiss();
                                }
                            });
                    if (!mActivity.isFinishing()) {
                        builder1.create().show();
                    }
                }
                break;
            case R.id.click_loyout:
//                Toast.makeText(mActivity, "退出当前账号", Toast.LENGTH_SHORT).show();
                logout();
                break;
        }
    }
}
