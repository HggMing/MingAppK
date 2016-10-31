package com.study.mingappk.tab4.safesetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.tab4.mysetting.mypurse.TakeMoneyActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SetPursePwdActivity extends BackActivity {

    @Bind(R.id.et_newpwd1)
    TintEditText etNewpwd1;
    @Bind(R.id.et_newpwd2)
    TintEditText etNewpwd2;
    public static String TYPE = "set_pruse_type";
    private int type = 0;//type=1为点击提现时进入此页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_purse_pwd);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_set_purse_pwd);

        type = getIntent().getIntExtra(TYPE, 0);
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

            if (StringTools.isEmpty(pwd1)) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (StringTools.isEmpty(pwd2)) {
                Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (!pwd1.equals(pwd2)) {
                Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (pwd1.length() < 6) {
                Toast.makeText(this, "密码必须在6位", Toast.LENGTH_SHORT).show();
                return true;
            }
            String auth = Hawk.get(APP.USER_AUTH);
            MyServiceClient.getService()
                    .post_SetPursePWD(auth, pwd1)
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
                        public void onNext(Result result) {
                            if (result.getErr() == 0) {
                                Toast.makeText(SetPursePwdActivity.this, "钱包密码设置成功！", Toast.LENGTH_SHORT).show();
                                //点击提现后，设置完密码，直接进入提现页面
                                if (type == 1) {
                                    Intent intent = new Intent(SetPursePwdActivity.this, TakeMoneyActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    finish();
                                }
                            }
                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
