package com.study.mingappk.test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.study.mingappk.R;

/**
 * Created by Ming on 2016/3/30.
 */
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

     TextView mTextView;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.id_textview);

    }
}
