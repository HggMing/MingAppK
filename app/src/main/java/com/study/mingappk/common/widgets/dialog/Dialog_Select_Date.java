package com.study.mingappk.common.widgets.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.study.mingappk.R;
import com.study.mingappk.common.widgets.mydatepicker.DatePicker;
import com.study.mingappk.common.widgets.mydatepicker.Sound;


public class Dialog_Select_Date extends Dialog implements DialogInterface {
    public Dialog_Select_Date(Context context) {
        super(context);
    }

    public Dialog_Select_Date(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private int icon;

        private DatePicker datePicker;
        //日期
        public String date;//1999年11月11日

        private OnClickListener positiveButtonClickListener,
                negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        @SuppressWarnings("deprecation")
        public Dialog_Select_Date create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog_Select_Date dialog = new Dialog_Select_Date(context, R.style.MyDialog);

            View layout = inflater.inflate(R.layout.dialog_select_date, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth() - 80); // 设置宽度

            ((TextView) layout.findViewById(R.id.title)).setText(title);

            datePicker = (DatePicker) layout.findViewById(R.id.date_picker);

            //初始化DatePicker组件，初始化时指定监听器

            int themeColor = ThemeUtils.getColorById(context, R.color.theme_color_primary);
            Sound sound = new Sound(context);
//            sound.setCustomSound(R.raw.beep);
            datePicker.setSoundEffect(sound)
                    .setTextColor(Color.BLACK)
                    .setFlagTextColor(themeColor)
//                    .setTextSize(25)
//                    .setFlagTextSize(15)
                    .setBackground(Color.WHITE)
                    .setRowNumber(3)
                    .setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date = year + "年" + monthOfYear + "月" + dayOfMonth + "日";
                        }
                    }).setSoundEffectsEnabled(true);

            Button btn_left = (Button) layout.findViewById(R.id.btn_Cancel);
            btn_left.setText(negativeButtonText);
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                }
            });

            Button btn_right = (Button) layout.findViewById(R.id.btn_OK);
            btn_right.setText(positiveButtonText);
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                }
            });

            dialog.setContentView(layout);
            return dialog;
        }
    }

}
