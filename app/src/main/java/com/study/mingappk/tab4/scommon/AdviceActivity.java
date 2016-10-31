package com.study.mingappk.tab4.scommon;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.dialog.MyDialog;
import com.study.mingappk.model.bean.Result;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AdviceActivity extends BackActivity {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.et_contact)
    EditText etContact;

    private String content;//意见反馈
    private String contact;//联系方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_advice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit2) {
            content = etContent.getText().toString();
            contact = etContact.getText().toString();
            if(StringTools.isEmpty(content)){
                Toast.makeText(AdviceActivity.this, "请输入您的建议再提交！", Toast.LENGTH_SHORT).show();
                return true;
            }
            mSubmit();//提交
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mSubmit() {
        String auth = Hawk.get(APP.USER_AUTH);
        MyServiceClient.getService()
                .get_Advice(auth, content, contact)
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
                        MyDialog.Builder builder = new MyDialog.Builder(AdviceActivity.this);
                        builder.setCannel(false)
                                .setMessage(result.getMsg())
                                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
                });
    }
}
