package com.study.mingappk.tab3.affairs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.model.bean.NewsList;
import com.study.mingappk.tmain.BaseRecyclerViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsListAdapter extends BaseRecyclerViewAdapter<NewsList.DataBean.ListBean, NewsListAdapter.ViewHolder> {

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab3_ga_list, parent, false);
        return new ViewHolder(mView);
    }

    /**
     * 绑定ViewHoler，给item中的控件设置数据
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.item, position);
                }
            });
        }
        //显示消息标题
        String title=mList.get(position).getMaintitle();
        holder.newsMainTitle.setText(title);
        //消息时间
        String date = mList.get(position).getCtime();
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String time = dateFormat.format(new Date(Long.valueOf(date + "000")));
//            String time = BaseTools.getTimeFormatText(new Date(Long.valueOf(date + "000")));
            holder.newsCtime.setText(time);//最新动态时间
        } else {
            holder.newsCtime.setText("");
        }
    }

    static  class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_layout)
        RelativeLayout item;
        @Bind(R.id.news_main_title)
        TextView newsMainTitle;
        @Bind(R.id.news_ctime)
        TextView newsCtime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
