package com.study.mingappk.shop.shoptab1.express;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.app.api.service.MyServiceClient;
import com.study.mingappk.common.base.BackActivity;
import com.study.mingappk.common.utils.BaseTools;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.common.widgets.MySpinner;
import com.study.mingappk.common.widgets.scanner.MyScannerActivity;
import com.study.mingappk.model.bean.ExpressFirm;
import com.study.mingappk.model.bean.ResultOther;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class EditExpressTakeActivity extends BackActivity {

    @Bind(R.id.et_num)
    TintEditText etNum;
    @Bind(R.id.my_spinner)
    MySpinner mySp;
    @Bind(R.id.et_name)
    TintEditText etName;
    @Bind(R.id.et_phone)
    TintEditText etPhone;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.scan)
    ImageView mScan;


    private String expressFirm;
    private String auth;

    private ArrayAdapter<String> mAdapter;
    private final int REQUEST_CODE = 12554;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_express_take);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_edit_express_take);

        auth = Hawk.get(APP.USER_AUTH);
        //选择设置快递公司
        settingExpressFrim();
    }

    private void settingExpressFrim() {
        mAdapter = mySp.getAdapter(this);
        //可选数据列表
        MyServiceClient.getService()
                .get_ExpressFirm(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExpressFirm>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ExpressFirm expressFirm) {
                        mAdapter.clear();
                        mAdapter.add("请选择快递公司");
                        for (ExpressFirm.DataBean firm : expressFirm.getData()) {
                            mAdapter.add(firm.getShipname());
                        }
                    }
                });
        //选择监听
        mySp.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    expressFirm = mAdapter.getItem(position);
                } else {
                    expressFirm = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

            String number = etNum.getEditableText().toString();
            String name = etName.getEditableText().toString();
            String phone = etPhone.getEditableText().toString();

            if (StringTools.isEmpty(number)) {
                Toast.makeText(this, "快递单号不能为空，可扫码快速添加！", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (StringTools.isEmpty(expressFirm)) {
                Toast.makeText(this, "请选择快递公司！", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (!StringTools.isEmpty(phone) && !BaseTools.checkPhone(phone)) {
                Toast.makeText(this, "输入的手机号码格式不正确！", Toast.LENGTH_SHORT).show();
                return true;
            }
            String vid = Hawk.get(APP.MANAGER_VID);
            MyServiceClient.getService()
                    .post_EditExpressTake(auth, vid, number, "2", expressFirm, name, phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResultOther>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ResultOther resultOther) {
                            if (resultOther.getErr() == 0) {
                                setResult(RESULT_OK);
                                finish();
                            }
                            Toast.makeText(EditExpressTakeActivity.this, resultOther.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    });


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.scan)
    public void onClick() {
        Intent intent = new Intent(this, MyScannerActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String numberScan = data.getStringExtra(MyScannerActivity.SCAN_RESULT);
                etNum.setText(numberScan);
            }
        }
    }
}
