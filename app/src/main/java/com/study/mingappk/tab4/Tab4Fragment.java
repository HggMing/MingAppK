package com.study.mingappk.tab4;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.api.result.UserInfoResult;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.common.dialog.Dialog_ChangePwd;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.tab4.selfinfo.UserDetailActivity;
import com.study.mingappk.test.TestActivity;
import com.study.mingappk.userlogin.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab4Fragment extends Fragment {
    AppCompatActivity mActivity;
    @Bind(R.id.toolbar_tab4)
    Toolbar toolbar4;
    @Bind(R.id.icon_head)
    CircleImageView iconHead;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sex)
    ImageView sex;
    @Bind(R.id.account_number)
    TextView accountNumber;
    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (AppCompatActivity) getActivity();
        sp = mActivity.getSharedPreferences("config", 0);
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity mActivity = (AppCompatActivity) getActivity();
        // mActivity.setSupportActionBar(toolbar4);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar_layout_tab4);
        //mCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.blue_setting));//设置还没收缩时状态下字体颜色
        // mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        getUserInfoDetail();//获取用户信息
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.click_user, R.id.click_changepwd, R.id.click_identity_binding,
            R.id.click_advice, R.id.click_check_version, R.id.click_about, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_user:
                Intent intent1 = new Intent(mActivity, UserDetailActivity.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.click_changepwd:
                changePwd();
                break;
            case R.id.click_identity_binding:
                Intent intent2 = new Intent(mActivity, ChangePwdActivity.class);
                startActivity(intent2);
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
                if (resultCode == mActivity.RESULT_OK) {
                    //修改后更新数据
                    getUserInfoDetail();
                }
                break;
        }
    }

    /**
     * 修改密码
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
                        String auth = MyApplication.getInstance().getAuth();
                        new MyNetApi().getService().getCall_ChangePwd(auth, oldpwd, newpwd1)
                                .enqueue(new Callback<Result>() {
                                    @Override
                                    public void onResponse(Call<Result> call, Response<Result> response) {
                                        if (response.isSuccess()) {
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
                        // MyApplication.getInstance().set_userInfo(null);
                        spEditor = sp.edit();
                        spEditor.putString("loginpwd", "");
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

    public void getUserInfoDetail() {
        String auth = MyApplication.getInstance().getAuth();
        Call<UserInfoResult> call = new MyNetApi().getService().getCall_UserInfo(auth);
        call.enqueue(new Callback<UserInfoResult>() {
            @Override
            public void onResponse(Call<UserInfoResult> call, Response<UserInfoResult> response) {
                if (response.isSuccess()) {
                    UserInfoResult userInfoResult = response.body();
                    if (userInfoResult != null && userInfoResult.getErr() == 0) {
                        UserInfoResult.DataEntity dataEntity = userInfoResult.getData();
                        MyApplication.getInstance().setUserInfo(dataEntity);
                        String headUrl = (MyApplication.getBaseUrl() + dataEntity.getHead());
                        Glide.with(mActivity).load(headUrl).into(iconHead);
                        name.setText(MyApplication.getInstance().getUserInfo().getUname());
                        String sexNumber = MyApplication.getInstance().getUserInfo().getSex();
                        if (sexNumber.equals("0")) {
                            sex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_sex_boy));
                        } else {
                            sex.setImageDrawable(getResources().getDrawable(R.mipmap.ic_sex_girl));
                        }
                        String accountNo = MyApplication.getInstance().getUserInfo().getLogname();
                        accountNumber.setText("账号：" + accountNo);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResult> call, Throwable t) {

            }
        });

    }
}
