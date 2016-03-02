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
import android.widget.TextView;

import com.study.mingappk.R;

public class Dialog_Model extends Dialog {
	public Dialog_Model(Context context) {
		super(context);
	}

	public Dialog_Model(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		public String objInfo;
		private boolean isCannel = true;

		public void setCannel(boolean isCannel) {
			this.isCannel = isCannel;
		}

		// private String positiveButtonText;
		// private String negativeButtonText;
		private String ok;
		private String cannle;
		private DialogInterface.OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		public Builder(Context context) {
			this.context = context;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setObjInfo(String objInfo) {
			this.objInfo = objInfo;
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

		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.ok = (String) context.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.ok = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.cannle = (String) context.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.cannle = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

		@SuppressWarnings("deprecation")
		public Dialog_Model create() {

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Dialog_Model dialog = new Dialog_Model(context,
					R.style.MyDialog);
			dialog.setCancelable(isCannel);
			View layout = inflater.inflate(R.layout.dialog_model, null);
			dialog.addContentView(layout, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			WindowManager windowManager = ((Activity) context)
					.getWindowManager();
			Display display = windowManager.getDefaultDisplay();

			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.width = (int) (display.getWidth() - 60); // 设置宽度
			((TextView) layout.findViewById(R.id.title)).setText(title);
			View is_show = (View) layout.findViewById(R.id.is_show);

			Button btn_left = (Button) layout.findViewById(R.id.negativeButton);
			btn_left.setText(cannle);
			btn_left.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					negativeButtonClickListener.onClick(dialog,
							DialogInterface.BUTTON_NEGATIVE);
				}
			});
			if (null == cannle) {
				is_show.setVisibility(View.GONE);
				btn_left.setVisibility(View.GONE);
			}

			Button btn_right = (Button) layout
					.findViewById(R.id.positiveButton);
			btn_right.setText(ok);
			btn_right.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					positiveButtonClickListener.onClick(dialog,
							DialogInterface.BUTTON_POSITIVE);
				}
			});
			if (null == ok) {
				is_show.setVisibility(View.GONE);
				btn_left.setVisibility(View.GONE);
			}
			if (message != null) {
				((TextView) layout.findViewById(R.id.message)).setText(message);
			}
			dialog.setContentView(layout);
			return dialog;

		}
	}

}
