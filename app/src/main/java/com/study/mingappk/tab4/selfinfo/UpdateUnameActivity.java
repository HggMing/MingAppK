package com.study.mingappk.tab4.selfinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUnameActivity extends BackActivity {

    public static String OLD_NAME="oldName";
    @Bind(R.id.et_uname)
    EditText etUname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_uname);
        ButterKnife.bind(this);
        String text=getIntent().getStringExtra(OLD_NAME);
        etUname.setText(text);
        etUname.setSelection(text.length());//光标移到文字最后
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
            final String newName=etUname.getText().toString();
            MyServiceClient.getService().postCall_UpdateInfo(auth,newName,null,null,null )
                    .enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                Result result = response.body();
                                if ( result != null) {
                                    Toast.makeText(UpdateUnameActivity.this,  result.getMsg(), Toast.LENGTH_SHORT).show();
                                    if( result.getErr()==0){
                                        Intent intent=new Intent();
                                        intent.putExtra(UserDetailActivity.NEW_NAME,newName);
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
