package com.study.mingappk.tab4.scommon;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.common.views.dialog.MyDialog;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            mSubmit();//提交
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mSubmit() {
        String auth = Hawk.get(APP.USER_AUTH);
        Call<Result> call = MyServiceClient.getService().getCall_Advice(auth, content, contact);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result adviceResult = response.body();
                    if (adviceResult != null) {
                        MyDialog.Builder builder = new MyDialog.Builder(AdviceActivity.this);
                        builder.setTitle("提示")
                                .setCannel(false)
                                .setMessage(adviceResult.getMsg())
                                .setNegativeButton("确定",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {
                                                dialog.dismiss();
                                            }
                                        });
                        if (!isFinishing()) {
                            builder.create().show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
