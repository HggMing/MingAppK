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
import android.widget.TextView;

import com.study.mingappk.R;

public class MyDialog extends Dialog {
	public MyDialog(Context context) {
		super(context);
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
	}

	public static class Builder {
		private Context context;
		private String title="提示";
		private String message;
		private boolean isCannel = true;  //点击对话框外，关闭对话框

		private String ok;
		private String cancel;
		private DialogInterface.OnClickListener positiveButtonClickListener, negativeButtonClickListener;

		public Builder setCannel(boolean isCannel) {
			this.isCannel = isCannel;
			return this;
		}


		public Builder(Context context) {
			this.context = context;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getMessage() {
			return this.message;
		}

		/**
		 * Set the Dialog message from resource
		 *
		 * @param message
		 * @return
		 */
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
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

		public Builder setPositiveButton(DialogInterface.OnClickListener listener) {
			this.ok = "确定";
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
			this.ok = (String) context.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
			this.ok = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton( DialogInterface.OnClickListener listener) {
			this.cancel = "取消";
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
			this.cancel = (String) context.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
			this.cancel = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		@SuppressWarnings("deprecation")
		public MyDialog create() {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final MyDialog dialog = new MyDialog(context, R.style.MyDialog);
			dialog.setCancelable(isCannel);

			View layout = inflater.inflate(R.layout.dialog_model, null);
			dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			WindowManager windowManager = ((Activity) context).getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.width = (int) (display.getWidth() - 80); // 设置宽度

			((TextView) layout.findViewById(R.id.title)).setText(title);

			View is_show = (View) layout.findViewById(R.id.is_show);

			Button btn_cancel = (Button) layout.findViewById(R.id.negativeButton);
			btn_cancel.setText(cancel);
			btn_cancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
				}
			});
			if (null == cancel) {
				is_show.setVisibility(View.GONE);
				btn_cancel.setVisibility(View.GONE);
			}

			Button btn_ok = (Button) layout.findViewById(R.id.positiveButton);
			btn_ok.setText(ok);
			btn_ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
				}
			});
			if (null == ok) {
				is_show.setVisibility(View.GONE);
				btn_ok.setVisibility(View.GONE);
			}
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}

}
