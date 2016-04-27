package com.study.mingappk.tab4.selfinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateIdcardActivity extends BackActivity {

    @Bind(R.id.et_idcard)
    EditText etIdcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_idcard);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_update_idcard);
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
            String auth = APP.getInstance().getAuth();
            final String newIdcard = etIdcard.getText().toString();
            if (!BaseTools.checkIdcard(newIdcard)) {
                Toast.makeText(UpdateIdcardActivity.this, "请输入正确的身份证号！！", Toast.LENGTH_SHORT).show();
            } else {
                MyServiceClient.getService().postCall_UpdateInfo(auth, null, null, newIdcard, null)
                        .enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                if (response.isSuccessful()) {
                                    Result result = response.body();
                                    if (result != null) {
                                        Toast.makeText(UpdateIdcardActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                        if (result.getErr() == 0) {
                                            Intent intent = new Intent();
                                            intent.putExtra(UserDetailActivity.NEW_IDCARD, newIdcard);
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {

                            }
                        });
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
