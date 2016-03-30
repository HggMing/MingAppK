package com.study.mingappk.tab4.selfinfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.main.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUnameActivity extends BackActivity {

    @Bind(R.id.et_uname)
    EditText etUname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_uname);
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
            String auth = MyApplication.getInstance().getAuth();
            final String newName=etUname.getText().toString();
            new MyNetApi().getService().getCall_UpdateInfo(auth,newName,null,null,null )
                    .enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccess()) {
                                Result result = response.body();
                                if ( result != null) {
                                    Toast.makeText(UpdateUnameActivity.this,  result.getMsg(), Toast.LENGTH_SHORT).show();
                                    if( result.getErr()==0){
                                        Intent intent=new Intent();
                                        intent.putExtra("newName",newName);
                                        setResult(11,intent);
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
