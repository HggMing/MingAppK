package com.study.mingappk.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.mingappk.R;


/**
 * listview item，带一个 icon 和一行文字
 */
public class CustomItem extends FrameLayout {

    private View mIcon;
    private TextView mText;

    public CustomItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.view_custom_item, this);
        mIcon = findViewById(R.id.icon);
        mText = (TextView) findViewById(R.id.title);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomItem);
        String title = array.getString(R.styleable.CustomItem_itemTitle);
        int icon = array.getResourceId(R.styleable.CustomItem_itemIcon, R.mipmap.user_home_project);
        boolean center = array.getBoolean(R.styleable.CustomItem_itemCenter, false);
        boolean showArrow=array.getBoolean(R.styleable.CustomItem_showArrow,true);
        array.recycle();

        if (title == null) title = "";
        mText.setText(title);
        mIcon.setBackgroundResource(icon);

        if(!showArrow){
            findViewById(R.id.arrow).setVisibility(GONE);
        }

        if (center) {
            findViewById(R.id.arrow).setVisibility(GONE);
            LinearLayout.LayoutParams layoutParams;
            layoutParams = (LinearLayout.LayoutParams) mText.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.weight = 0;
            ((LinearLayout) findViewById(R.id.clickProject)).setGravity(Gravity.CENTER);
        }
    }

    public void setText(String s) {
        if (s == null) {
            return;
        }
        mText.setText(s);
    }
    public void setIcon(int icon) {
        if (icon ==0) {
            return;
        }
        mIcon.setBackgroundResource(icon);
    }
}
