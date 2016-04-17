package com.study.mingappk.tab4;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.Dialog_ChangePwd;
import com.study.mingappk.common.views.dialog.Dialog_Model;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.bean.UserInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tab4.selfinfo.UserDetailActivity;
import com.study.mingappk.test.Test3Activity;
import com.study.mingappk.test.TestActivity;
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
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private boolean isUpdataMyInfo;//是否更新完个人信息
    private UserInfo.DataEntity dataEntity;

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

        sp = mActivity.getSharedPreferences("config", 0);
        spEditor = sp.edit();
        isUpdataMyInfo = sp.getBoolean("isUpdataMyInfo", false);

        getUserInfoDetail();//在线获取用户信息
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.click_user, R.id.click_changepwd, R.id.click_my_order, R.id.click_identity_binding,
            R.id.click_advice, R.id.click_check_version, R.id.click_about, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_user:
                Intent intent1 = new Intent(mActivity, UserDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(UserDetailActivity.USER_INFO, dataEntity);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 0);
                break;
            case R.id.click_changepwd:
                Intent intent2 = new Intent(mActivity, ChangePwdActivity.class);
                startActivity(intent2);
                break;
            case R.id.click_my_order:
                Intent intent3 = new Intent(mActivity, Test3Activity.class);
                startActivity(intent3);
                break;
            case R.id.click_identity_binding:
                break;
            case R.id.click_advice:
                Intent intent = new Intent(mActivity, AdviceActivity.class);
                startActivity(intent);
                break;
            case R.id.click_check_version:
                Intent intent4 = new Intent(mActivity, TestActivity.class);
                startActivity(intent4);
                break;
            case R.id.click_about:
                Intent intent5 = new Intent(mActivity, AboutActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_exit:
                logout();
                break;
        }
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
                        String auth = APP.getInstance().getAuth();
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
                        // APP.getInstance().set_userInfo(null);
                        spEditor.putString("loginpwd", "");
                        spEditor.putBoolean("isUpdataMyInfo", false);
                        spEditor.commit();

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
        String auth = APP.getInstance().getAuth();
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

                        spEditor.putBoolean("isUpdataMyInfo", true);
                        spEditor.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                JUtils.Log("获取用户信息失败：" + t.getMessage());
            }
        });

    }
}
