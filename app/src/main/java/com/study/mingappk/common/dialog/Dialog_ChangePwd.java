package com.study.mingappk.common.dialog;


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
import android.widget.ImageView;
import android.widget.TextView;

import com.study.mingappk.R;


public class Dialog_ChangePwd extends Dialog implements DialogInterface
{
	public Dialog_ChangePwd(Context context) {
		super(context);
	}

	public Dialog_ChangePwd(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title;
		private String positiveButtonText;
		private String negativeButtonText;
		private int icon;

		public EditText et_oldpwd;
		public EditText et_newpwd1;
		public EditText et_newpwd2;

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
		public Dialog_ChangePwd create()
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Dialog_ChangePwd dialog = new Dialog_ChangePwd(context,
					R.style.MyDialog);

			View layout = inflater.inflate(R.layout.dialog_changepwd, null);
			dialog.addContentView(layout, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			WindowManager windowManager = ((Activity) context)
					.getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.width = (int) (display.getWidth() - 30); // 设置宽度
			((ImageView) layout.findViewById(R.id.icon)).setImageResource(icon);
			((TextView) layout.findViewById(R.id.title)).setText(title);

			et_oldpwd = (EditText) layout.findViewById(R.id.et_oldpwd);
			et_newpwd1 = (EditText) layout.findViewById(R.id.et_newpwd1);
			et_newpwd2 = (EditText) layout.findViewById(R.id.et_newpwd2);

			Button btn_left = (Button) layout.findViewById(R.id.btn_OK);
			btn_left.setText(negativeButtonText);
			btn_left.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					negativeButtonClickListener.onClick(dialog,
							DialogInterface.BUTTON_NEGATIVE);
				}
			});

			Button btn_right = (Button) layout.findViewById(R.id.btn_Cancel);
			btn_right.setText(positiveButtonText);
			btn_right.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					positiveButtonClickListener.onClick(dialog,
							DialogInterface.BUTTON_POSITIVE);
				}
			});

			dialog.setContentView(layout);
			return dialog;
		}
	}

}
