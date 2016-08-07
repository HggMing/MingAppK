package com.study.mingappk.tab4.mysetting;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintEditText;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.bean.ResultOther;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class AddCardsActivity extends BackActivity {

    public static String TYPE="type";//之前无银行卡，则为-1，之前已有银行卡，则为0.
    @Bind(R.id.et_name)
    TintEditText etName;
    @Bind(R.id.et_number)
    TintEditText etNumber;
    @Bind(R.id.et_bank)
    TintEditText etBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cards);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_add_cards);
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

            String bank_true_name = etName.getEditableText().toString();
            String bank_no = etNumber.getEditableText().toString();
            String bank_name = etBank.getEditableText().toString();

            if (bank_true_name.equals("")) {
                Toast.makeText(this, "请输入开户人姓名!", Toast.LENGTH_LONG).show();
                return true;
            }
            if (bank_no.equals("")) {
                Toast.makeText(this, "请输入银行卡号", Toast.LENGTH_LONG).show();
                return true;
            }
            if (bank_name.equals("")) {
                Toast.makeText(this, "请输入开户银行名称！", Toast.LENGTH_LONG).show();
                return true;
            }

            String auth = Hawk.get(APP.USER_AUTH);
            MyServiceClient.getService()
                    .post_AddCard(auth, bank_name, bank_no, bank_true_name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<ResultOther>() {
                        @Override
                        public void call(ResultOther resultOther) {
                            //添加的卡在最上方，则选择的序号+1
                            int type=getIntent().getIntExtra(TYPE,-1);
                            int mSelect=Hawk.get(APP.SELECTED_CARD,type);
                            Hawk.put(APP.SELECTED_CARD,mSelect+1);

                            setResult(RESULT_OK);
                            finish();
                        }
                    });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
