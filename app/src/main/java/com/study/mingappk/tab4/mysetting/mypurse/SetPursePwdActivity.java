package com.study.mingappk.tab4.mysetting.mypurse;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SetPursePwdActivity extends BackActivity {

    @Bind(R.id.et_newpwd1)
    TintEditText etNewpwd1;
    @Bind(R.id.et_newpwd2)
    TintEditText etNewpwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_purse_pwd);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_set_purse_pwd);
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

            String pwd1 = etNewpwd1.getEditableText().toString();
            String pwd2 = etNewpwd2.getEditableText().toString();

            if (pwd1.equals("")) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                return true;
            }
            if (pwd2.equals("")) {
                Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_LONG).show();
                return true;
            }
            if (!pwd1.equals(pwd2)) {
                Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_LONG).show();
                return true;
            }
            if (pwd1.length() < 6) {
                Toast.makeText(this, "密码必须在6位", Toast.LENGTH_LONG).show();
                return true;
            }
            String auth = Hawk.get(APP.USER_AUTH);
            MyServiceClient.getService()
            .post_SetPursePWD(auth,pwd1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Result result) {
                            if(result.getErr()==0){
                                Toast.makeText(SetPursePwdActivity.this, "钱包密码设置成功！", Toast.LENGTH_SHORT).show();
                                //进入提现页面
                                Intent intent=new Intent(SetPursePwdActivity.this,TakeMoneyActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
