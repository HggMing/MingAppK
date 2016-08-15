package com.study.mingappk.common.views.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.study.mingappk.R;

public class Dialog_UpdateSex extends Dialog implements DialogInterface {
    public Dialog_UpdateSex(Context context) {
        super(context);
    }

    public Dialog_UpdateSex(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private int icon;

        private String mysex = "";//sex初始状况

        public RadioButton male;
        public RadioButton female;

        public LinearLayout maleAll;
        public LinearLayout femaleAll;


        public Builder setMysex(String mysex) {
            this.mysex = mysex;
            return this;
        }


        private DialogInterface.OnClickListener positiveButtonClickListener,
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

        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        @SuppressWarnings("deprecation")
        public Dialog_UpdateSex create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Dialog_UpdateSex dialog = new Dialog_UpdateSex(context, R.style.MyDialog);

            View layout = inflater.inflate(R.layout.dialog_updatesex, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            WindowManager windowManager = ((Activity) context).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = (int) (display.getWidth() - 80); // 设置宽度

            ((ImageView) layout.findViewById(R.id.icon)).setImageResource(icon);
            ((TextView) layout.findViewById(R.id.title)).setText(title);

            male = (RadioButton) layout.findViewById(R.id.male);
            female = (RadioButton) layout.findViewById(R.id.female);
            maleAll = (LinearLayout) layout.findViewById(R.id.male_all);
            femaleAll = (LinearLayout) layout.findViewById(R.id.female_all);

            //根据本身性别，决定radio的默认选择
            if ("0".equals(mysex)) {
                male.setChecked(true);
                female.setChecked(false);
            } else {
                female.setChecked(true);
                male.setChecked(false);
            }


            dialog.setContentView(layout);
            return dialog;
        }
    }


}
