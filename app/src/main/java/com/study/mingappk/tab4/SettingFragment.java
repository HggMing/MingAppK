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
import com.study.mingappk.common.views.dialog.Dialog_Model;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab4.mysetting.MySettingActivity;
import com.study.mingappk.tab4.scommon.SettingCommonActivity;
import com.study.mingappk.tab4.selfinfo.UserDetailActivity;
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
    @Bind(R.id.account_number)
    TextView accountNumber;
    private boolean isUpdataMyInfo;//是否更新完个人信息
    private UserInfo.DataEntity dataEntity;

    private String auth;

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
        isUpdataMyInfo = Hawk.get(APP.IS_UPDATA_MY_INFO, false);

        getUserInfoDetail();//在线获取用户信息
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
            case 0:
                if (resultCode == Activity.RESULT_OK) {
                    //修改数据后在线更新个人信息
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
                                                Dialog_Model.Builder builder2 = new Dialog_Model.Builder(mActivity);
                                                builder2.setTitle("提示");
                                                builder2.setCannel(false);
                                                builder2.setMessage(changePwdResult.getMsg());
                                                builder2.setPositiveButton("确定",
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
        Dialog_Model.Builder builder = new Dialog_Model.Builder(mActivity);
        builder.setTitle("提示");
        builder.setMessage("确定退出登录？");
        builder.setNegativeButton("确定",
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
                });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
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
                        String uName = dataEntity.getUname();
                        String sexNumber = dataEntity.getSex();
                        String accountNo = dataEntity.getLogname();

                        Glide.with(mActivity)
                                .load(headUrl)
                                .bitmapTransform(new CropCircleTransformation(mActivity))
                                .into(iconHead);
                        name.setText(uName);
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

    @OnClick({R.id.click_user, R.id.click_identity_binding, R.id.click_my_setting, R.id.click_setting_common, R.id.click_store_manager, R.id.click_loyout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_user:
                Intent intent1 = new Intent(mActivity, UserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(UserDetailActivity.USER_INFO, dataEntity);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 0);
                break;
            case R.id.click_identity_binding:
                Toast.makeText(mActivity, "实名认证", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(mActivity, "申请店长", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_loyout:
//                Toast.makeText(mActivity, "退出当前账号", Toast.LENGTH_SHORT).show();
                logout();
                break;
        }
    }
}
