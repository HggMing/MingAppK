package com.study.mingappk.tab4.safesetting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangePursePwdActivity extends BackActivity {

    @Bind(R.id.et_oldpwd)
    EditText etOldpwd;
    @Bind(R.id.et_newpwd1)
    EditText etNewpwd1;
    @Bind(R.id.et_newpwd2)
    EditText etNewpwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_purse_pwd);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_change_purse_pwd);
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
            String auth = Hawk.get(APP.USER_AUTH);
            MyServiceClient.getService()
                    .post_ResetPursePWD(auth, oldpwd, newpwd1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Result>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(final Result result) {
                            if(result.getErr()==90003){
                                result.setMsg("原始交易密码错误，请重新输入");
                            }
                            MyDialog.Builder builder = new MyDialog.Builder(ChangePursePwdActivity.this);
                            builder.setTitle("提示")
                                    .setCannel(false)
                                    .setMessage(result.getMsg())
                                    .setNegativeButton("确定",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {
                                                    dialog.dismiss();
                                                    if (result.getErr() == 0) {
                                                        ChangePursePwdActivity.this.finish();
                                                    }
                                                }

                                            });
                            if (!isFinishing()) {
                                builder.create().show();
                            }
                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
