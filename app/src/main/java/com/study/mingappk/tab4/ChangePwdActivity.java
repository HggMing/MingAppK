package com.study.mingappk.tab4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.main.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdActivity extends BackActivity {

    @Bind(R.id.et_oldpwd)
    EditText etOldpwd;
    @Bind(R.id.et_newpwd1)
    EditText etNewpwd1;
    @Bind(R.id.et_newpwd2)
    EditText etNewpwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // 将菜单图标添加到toolbar
            getMenuInflater().inflate(R.menu.menu_submit, menu);
            return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_submit) {

            String oldpwd = etOldpwd.getEditableText().toString();
            String newpwd1 = etNewpwd1.getEditableText().toString();
            String newpwd2 = etNewpwd2.getEditableText().toString();

            if (oldpwd.equals("")) {
                Toast.makeText(this, "旧密码不能为空", Toast.LENGTH_LONG).show();
                return true;
            }
            if (newpwd1.equals("")) {
                Toast.makeText(this, "新密码不能为空", Toast.LENGTH_LONG).show();
                return true;
            }
            if (newpwd2.equals("")) {
                Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_LONG).show();
                return true;
            }
            if (!newpwd1.equals(newpwd2)) {
                Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
                return true;
            }
            if (newpwd1.length() < 6 || newpwd1.length() > 16) {
                Toast.makeText(this, "密码必须在6-16位", Toast.LENGTH_LONG).show();
                return true;
            }
            new MyNetApi().getCallChangePwd(oldpwd, newpwd1).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccess()) {
                        Result changePwdResult = response.body();
                        if (changePwdResult != null) {
                            Dialog_Model.Builder builder2 = new Dialog_Model.Builder(ChangePwdActivity.this);
                            builder2.setTitle("提示");
                            builder2.setCannel(false);
                            builder2.setMessage(changePwdResult.getMsg());
                            builder2.setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            dialog.dismiss();
                                            ChangePwdActivity.this.finish();
                                        }

                                    });
                            if (!isFinishing()) {
                                builder2.create().show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Toast.makeText(ChangePwdActivity.this, "修改密码失败", Toast.LENGTH_LONG).show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
