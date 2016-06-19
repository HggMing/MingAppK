package com.study.mingappk.tab3.affairs;

import android.os.Bundle;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.model.bean.NewsList;
import com.study.mingappk.tmain.BackActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BackActivity {

    public static String NEWS_DETAIL="news_detail";
    @Bind(R.id.main_title)
    TextView mainTitle;
    @Bind(R.id.sub_title)
    TextView subTitle;
    @Bind(R.id.conts)
    TextView conts;
    @Bind(R.id.inscribe)
    TextView inscribe;
    @Bind(R.id.ctime)
    TextView ctime;
    @Bind(R.id.mark)
    TextView mark;

    NewsList.DataBean.ListBean newsDetail;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        setToolbarTitle("新闻");

        initData();
    }

    private void initData() {
        newsDetail=getIntent().getParcelableExtra(NEWS_DETAIL);

        mainTitle.setText(newsDetail.getMaintitle());
        subTitle.setText(newsDetail.getSubtitle());
        conts.setText(newsDetail.getConts());
        inscribe.setText(newsDetail.getInscribe());
        mark.setText(newsDetail.getMark());

        //时间
        String date = newsDetail.getCtime();
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String time = dateFormat.format(new Date(Long.valueOf(date + "000")));
           ctime.setText(time);
        } else {
            ctime.setText("");
        }
    }
}
