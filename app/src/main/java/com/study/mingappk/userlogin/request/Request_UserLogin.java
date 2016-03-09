package com.study.mingappk.userlogin.request;

import android.content.Context;

import com.google.gson.Gson;
import com.study.mingappk.common.app.info.UserInfo;
import com.study.mingappk.common.net.RequsetBase;
import com.study.mingappk.common.utils.AndroidUtils;

import org.json.JSONObject;

public class Request_UserLogin extends RequsetBase {
	private String _phone;
	private String _pwd;

	public UserInfo userInfo;

	public Request_UserLogin(Context Context, String phone, String pwd) {
		super(Context);
		this._phone = phone;
		this._pwd = pwd;
		_url += "user/login";
	}


	@Override
	public JSONObject getRequestJson() {
		_requestJson = new JSONObject();
		try {
			_requestJson.put("logname", _phone);
			_requestJson.put("pwd", _pwd);

		} catch (Exception e) {

		}
		return super.getRequestJson();
	}



	@Override
	public ResultPacket DoResponseData(String data) {
		ResultPacket result = new ResultPacket();
		userInfo = new UserInfo();
		try {
			JSONObject jsonObject = new JSONObject(data);

			int errorCode = jsonObject.getInt("err");
			if (errorCode == 2003)// 手机号未注册
			{
				result.setIsError(true);
				result.setResultCode("98");
				result.setDescription(AndroidUtils.getJsonString(jsonObject,
						"msg", ""));
				return result;
			}
			if (errorCode != 0) {
				result.setIsError(true);
				result.setResultCode("99");
				result.setDescription(AndroidUtils.getJsonString(jsonObject,
						"msg", ""));
				return result;
			}

			
			String infoString =   AndroidUtils.getJsonString(jsonObject, "info", "");
			
			Gson gson = new Gson();
			userInfo = gson.fromJson(infoString, UserInfo.class);
			userInfo.auth = AndroidUtils.getJsonString(jsonObject, "auth", "");

			return result;
		} catch (Exception e) {
			result.setIsError(true);
			result.setResultCode("99");
			result.setDescription("连接服务器失败，请检查网络是否正常");
			return result;
		}
	}

}
