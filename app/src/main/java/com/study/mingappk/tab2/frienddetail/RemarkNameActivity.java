package com.study.mingappk.tab2.frienddetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.event.RefreshFriendList;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.baseactivity.BackActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RemarkNameActivity extends BackActivity {

    public static String OLD_ANAME = "取出旧的备注名";
    public static String UID = "要设置用户的ID";
    @Bind(R.id.et_aname)
    EditText etAname;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_name);
        ButterKnife.bind(this);

        setToolbarTitle(R.string.title_activity_remark_name);
        String text = getIntent().getStringExtra(OLD_ANAME);
        etAname.setText(text);
        etAname.setSelection(text.length());//光标移到文字最后
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
            String auth = Hawk.get(APP.USER_AUTH);
            String uid = getIntent().getStringExtra(UID);
            final String newName = etAname.getText().toString();

            MyServiceClient.getService().post_RemarkName(auth, uid, newName)
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
//                            Toast.makeText(RemarkNameActivity.this,  result.getMsg(), Toast.LENGTH_SHORT).show();
                            if (result.getErr() == 0) {
                                Toast.makeText(RemarkNameActivity.this, "备注成功", Toast.LENGTH_SHORT).show();
                                //刷新好友列表
                                EventBus.getDefault().post(new RefreshFriendList());
                                //更新详情页界面
                                Intent intent = new Intent();
                                intent.putExtra(FriendDetailActivity.NEW_NAME, newName);
                                setResult(RESULT_OK, intent);
                                finish();
                            }else{
                                Toast.makeText(RemarkNameActivity.this, result.getErr()+":"+result.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
