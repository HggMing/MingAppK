package com.study.mingappk.common.widgets.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.study.mingappk.R;


public class Dialog_InputPwd extends Dialog implements DialogInterface {
    public Dialog_InputPwd(Context context) {
        super(context);
    }

    public Dialog_InputPwd(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private int icon;

        public   EditText et_pwd;

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
        public Dialog_InputPwd create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog_InputPwd dialog = new Dialog_InputPwd(context, R.style.MyDialog);

            View layout = inflater.inflate(R.layout.dialog_input_pwd, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth() - 80); // 设置宽度

            ((TextView) layout.findViewById(R.id.title)).setText(title);
            et_pwd = (EditText) layout.findViewById(R.id.et_pwd);

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
