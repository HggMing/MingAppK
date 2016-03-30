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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.study.mingappk.R;
import com.study.mingappk.common.app.MyApplication;

public class Dialog_UpdateSex extends Dialog implements DialogInterface
{
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
		
		private RadioGroup sex=null;
		private RadioButton male=null;
		private RadioButton female=null;
		
		
		public String sexStr="";

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
		public Dialog_UpdateSex create()
		{
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final Dialog_UpdateSex dialog = new Dialog_UpdateSex(context,
					R.style.MyDialog);

			View layout = inflater.inflate(R.layout.dialog_updatesex, null);
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
			
			sex=(RadioGroup) layout.findViewById(R.id.sex);
			male=(RadioButton) layout.findViewById(R.id.male);
			female=(RadioButton) layout.findViewById(R.id.female);
//			if("0".equals(MyApplication.getInstance().getUserInfo().getSex())){
			String mysex=dialog.getContext().getSharedPreferences("config",Context.MODE_PRIVATE).getString("MyInfo_Sex",null);
			if("0".equals(mysex)){
				male.setChecked(true);
				sexStr="男";
			}else{
				female.setChecked(true);
				sexStr="女";
			}
			sex.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					if(male.getId()==checkedId){
						sexStr="男";
					}
					else if(female.getId()==checkedId){
						sexStr="女";
					}
					
				}
			});
			

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
