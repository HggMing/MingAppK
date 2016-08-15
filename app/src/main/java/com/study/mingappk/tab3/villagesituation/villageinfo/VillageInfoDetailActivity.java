package com.study.mingappk.tab3.villagesituation.villageinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.study.mingappk.R;
import com.study.mingappk.common.utils.StringTools;
import com.study.mingappk.model.bean.VillageInfo;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.tmain.BackActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VillageInfoDetailActivity extends BackActivity {

    public static String VILLAGE_INFO_DETAIL = "village_info_detail";
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.image)
    ImageView mImage;
    @Bind(R.id.content)
    TextView mContent;
    @Bind(R.id.time)
    TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_info_detail);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        VillageInfo.DataBean data = getIntent().getParcelableExtra(VILLAGE_INFO_DETAIL);
        //toolbar标题
        switch (data.getItype()) {
            case "1":
                setToolbarTitle("荣誉室");
                break;
            case "2":
                setToolbarTitle("活动");
                break;
            case "4":
                setToolbarTitle("美食");
                break;
        }
        //标题
        String title = data.getTitle();
        mTitle.setText(title);
        //图片
        if (StringTools.isEmpty(data.getPic())) {
            mImage.setVisibility(View.GONE);
        } else {
            String imageUrl = MyServiceClient.getBaseUrl() + data.getPic();
            Glide.with(this).load(imageUrl)
                    .placeholder(R.mipmap.default_nine_picture)
                    .into(mImage);
        }
        //内容
        String content = data.getContent();
        mContent.setText(content);
        //时间
        String date = data.getCtime();
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            String time = dateFormat.format(new Date(Long.valueOf(date + "000")));
            mTime.setText(time);//最新动态时间
        } else {
            mTime.setText("");
        }
    }
}
