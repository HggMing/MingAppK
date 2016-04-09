package com.study.mingappk.tab4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.Result;
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
        mSubmit();//提交
    }

    private void mSubmit() {
        String auth= APP.getInstance().getAuth();
        Call<Result> call = new MyServiceClient().getService().getCall_Advice(auth,content, contact);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result adviceResult = response.body();
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
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}
