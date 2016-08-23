package com.study.mingappk.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.study.mingappk.R;


/**
 * myshop item，带一个 icon 和一行文字
 */
public class MyShopItem extends FrameLayout {

    private TextView mIcon;
    private TextView mText;

    public MyShopItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.view_my_shop_item, this);
        mIcon = (TextView) findViewById(R.id.icon);
        mText = (TextView) findViewById(R.id.title);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyShopItem);
        String title = array.getString(R.styleable.MyShopItem_itemFunTitle);
        String iconName = array.getString(R.styleable.MyShopItem_itemIconName);
        array.recycle();

        if (title == null) title = "";
        if (iconName == null) iconName = "";
        mText.setText(title);
        mIcon.setText(iconName);
    }

    public void setText(String s) {
        if (s == null) {
            return;
        }
        mText.setText(s);
    }

    public void setIcon(String icon) {
        if (icon == null) {
            return;
        }
        mIcon.setText(icon);
    }
}
