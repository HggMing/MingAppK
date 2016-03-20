package com.study.mingappk.tab4.selfinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.common.dialog.Dialog_UpdateSex;
import com.study.mingappk.main.BackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends BackActivity {

    @Bind(R.id.icon_head2)
    CircleImageView iconHead2;
    @Bind(R.id.get_name)
    TextView getName;
    @Bind(R.id.get_sex)
    TextView getSex;
    @Bind(R.id.get_id_card)
    TextView getIdCard;
    @Bind(R.id.get_address)
    TextView getAddress;
    @Bind(R.id.get_phone)
    TextView getPhone;
    @Bind(R.id.get_login_time)
    TextView getLoginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //头像
        String headUrl = (MyApplication.getBaseUrl() + MyApplication.getInstance().getUserInfo().getHead());
        Glide.with(this).load(headUrl).into(iconHead2);
        //姓名
        getName.setText(MyApplication.getInstance().getUserInfo().getUname());
        //性别
        String sex = MyApplication.getInstance().getUserInfo().getSex();
        if (sex.equals("0")) {
            getSex.setText("男");
        } else if (sex.equals("1")) {
            getSex.setText("女");
        } else {
            getSex.setText("未知");
        }
        //身份证号
        getIdCard.setText(MyApplication.getInstance().getUserInfo().getCid());
        //地址:"province_name":"四川省", "city_name":"遂宁市", "county_name":"蓬溪县", "town_name":"红江镇", "village_name":"永益村"
        String address = (MyApplication.getInstance().getUserInfo().getProvince_name() +
                MyApplication.getInstance().getUserInfo().getCity_name() +
                MyApplication.getInstance().getUserInfo().getCounty_name() +
                MyApplication.getInstance().getUserInfo().getTown_name() +
                MyApplication.getInstance().getUserInfo().getVillage_name());
        getAddress.setText(address);
        //手机号
        getPhone.setText(MyApplication.getInstance().getUserInfo().getPhone());
        // 最后登录时间
    }

    @OnClick({R.id.icon_head2, R.id.set_name, R.id.set_sex, R.id.set_id_card, R.id.set_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_head2:
                break;
            case R.id.set_name:
                Intent intent1 = new Intent(this, UpdateUnameActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.set_sex:
                updateSex();
                break;
            case R.id.set_id_card:
                Intent intent2 = new Intent(this, UpdateIdcardActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case R.id.set_address:
                Intent intent3 = new Intent(this, UpdateAdressActivity.class);
                startActivityForResult(intent3,1);
                break;
        }
    }

    /**
     * 修改性别
     */
    private void updateSex() {
        final Dialog_UpdateSex.Builder sexDialog = new Dialog_UpdateSex.Builder(UserDetailActivity.this);
        sexDialog.setTitle("修改性别");
        sexDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSex.setText(sexDialog.sexStr); //修改本页性别显示
                //将修改post到服务器
                String sexNo;
                if (sexDialog.sexStr.equals("男")) {
                    sexNo = "0";
                } else {
                    sexNo = "1";
                }
                String auth = MyApplication.getInstance().getAuth();
                new MyNetApi().getService().getCall_UpdateInfo(auth, null, sexNo, null, null)
                        .enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                if (response.isSuccess()) {
                                    Result result = response.body();
                                    if (result != null) {
                                        Toast.makeText(UserDetailActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {
                            }
                        });
                dialog.dismiss();
                ;
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 11:
                String result = data.getExtras().getString("newName");
                getName.setText(result);
                break;
            case 22:
                String result2 = data.getExtras().getString("newIdcard");
                getIdCard.setText(result2);
                break;
            case 33:
                String result3 = data.getExtras().getString("newAddress");
                getAddress.setText(result3);
                break;
            default:
                break;
        }
    }
}
