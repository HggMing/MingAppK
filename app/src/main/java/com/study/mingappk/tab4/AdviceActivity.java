package com.study.mingappk.tab4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.AdviceResult;
import com.study.mingappk.api.result.LoginResult;
import com.study.mingappk.api.result.PhoneResult;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.common.dialog.Dialog_Model;
import com.study.mingappk.main.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdviceActivity extends BackActivity {

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.et_contact)
    EditText etContact;
    @Bind(R.id.btn_advice)
    Button btnAdvice;

    String content;//意见反馈
    String contact;//联系方式
    String auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_advice)
    public void onClick() {
        content = etContent.getText().toString();
        contact = etContact.getText().toString();
        auth = MyApplication.getInstance().getUserInfo().getAuth();
        mSubmit();//提交
    }

    private void mSubmit() {
        Call<AdviceResult> call = new MyNetApi().getCall(auth, content, contact);
        call.enqueue(new Callback<AdviceResult>() {
            @Override
            public void onResponse(Call<AdviceResult> call, Response<AdviceResult> response) {
                if (response.isSuccess()) {
                    AdviceResult adviceResult = response.body();
                    if (adviceResult != null ) {
                        Dialog_Model.Builder builder = new Dialog_Model.Builder(AdviceActivity.this);
                        builder.setTitle("提示");
                        builder.setCannel(false);
                        builder.setMessage(adviceResult.getMsg());
                        builder.setPositiveButton("确定",
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
            public void onFailure(Call<AdviceResult> call, Throwable t) {

            }
        });
    }
}
