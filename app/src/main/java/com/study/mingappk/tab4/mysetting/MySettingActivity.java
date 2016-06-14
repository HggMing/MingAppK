package com.study.mingappk.tab4.mysetting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.model.databean.Test;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MySettingActivity extends BackActivity {
    List<Test> tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ButterKnife.bind(this);
        setToolbarTitle(R.string.title_activity_mysetting);

        initData();
    }


    private void initData() {
        tests = new ArrayList<>();
//        tests.add(new Test("测试开始", "123456789123456789"));
//        tests.add(new Test("乐楷瑞", "532628197407285391"));
//        tests.add(new Test("严怡畅", "411525197002283432"));
//        tests.add(new Test("严雄逸", "120000198507247293"));
//        tests.add(new Test("卜博文", "411525198207268214"));
//        tests.add(new Test("云德辉", "41152519810322193X"));
        tests.add(new Test("陈明", "510922198809163618"));

        /*tests.add(new Test("朱泽洋", "51170219740419175X"));
        tests.add(new Test("罗嘉懿", "511702198504283656"));
        tests.add(new Test("蒋开霁", "511702197901178476"));
        tests.add(new Test("赵旭尧", "511702198604224530"));
        tests.add(new Test("尤杰伟", "511702197807102977"));
        tests.add(new Test("卫德辉", "45102519760724935X"));
        tests.add(new Test("康高爽", "451025198108207096"));
        tests.add(new Test("云熙茂", "451025198608159537"));
        tests.add(new Test("云俊伟", "45102519750311213X"));
        tests.add(new Test("葛豪健", "451025198905264798"));
        tests.add(new Test("方淳雅", "532628198705183398"));
        tests.add(new Test("鲁英杰", "411525197306176273"));
        tests.add(new Test("金雄博", "411525197805213614"));
        tests.add(new Test("吴伟祺", "411525198301104718"));
        tests.add(new Test("金旭尧", "411525197509212991"));
        tests.add(new Test("朱嘉志", "120000198401263519"));
        tests.add(new Test("张伟誉", "120000197409123030"));
        tests.add(new Test("周绍辉", "120000199001101216"));
        tests.add(new Test("岑嘉懿", "120000198101126010"));*/
    }

    @OnClick({R.id.click_my_order, R.id.click_shipping_address, R.id.click_my_purse})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.click_my_order:
//                Toast.makeText(MySettingActivity.this, "我的订单", Toast.LENGTH_SHORT).show();
                Toast.makeText(MySettingActivity.this, "GET请求", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < tests.size(); i++) {
                    final String name = tests.get(i).getName();
                    final String idnum = tests.get(i).getIdnum();
                    MyServiceClient.getService().get_Test(name, idnum, "1")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {
                                    Log.d("mm:", "姓名：" + name + "   身份证号：" + idnum + "    结果：" + "失败");
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(Result result) {
//                                Log.d("mm:", "姓名：" + name + "   身份证号：" + idnum + "    结果：" + result.getMsg());
                                }
                            });
                }
                break;
            case R.id.click_shipping_address:
                Toast.makeText(MySettingActivity.this, "我的收货地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.click_my_purse:
//                Toast.makeText(MySettingActivity.this, "我的钱包", Toast.LENGTH_SHORT).show();
                Toast.makeText(MySettingActivity.this, "POST请求", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < tests.size(); i++) {
                    final String name = tests.get(i).getName();
                    final String idnum = tests.get(i).getIdnum();
                    MyServiceClient.getService().post_Test(name, idnum, "1")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Result>() {
                                @Override
                                public void onCompleted() {
                                    Log.d("mm:", "姓名：" + name + "   身份证号：" + idnum + "    结果：" + "失败");
                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(Result result) {
//                                Log.d("mm:", "姓名：" + name + "   身份证号：" + idnum + "    结果：" + result.getMsg());
                                }
                            });
                }

                break;
        }
    }
}
