package com.study.mingappk.userlogin.request;

import android.content.Context;

import com.google.gson.Gson;
import com.study.mingappk.common.app.info.UserInfo;
import com.study.mingappk.common.net.RequsetBase;
import com.study.mingappk.common.utils.AndroidUtils;

import org.json.JSONObject;

public class Request_UserLogin extends RequsetBase {
	private Context _context;
	private String _phone;
	private String _pwd;
	private String _imei;

	public UserInfo userInfo;

	public Request_UserLogin(Context Context, String phone, String pwd,
							 String imei) {
		super(Context);
		_context = Context;
		this._phone = phone;
		this._pwd = pwd;
		this._imei = imei;

		_url += "user/login";
	}

	public JSONObject DoBeforeSendData() {
		_requestJson = new JSONObject();
		try {
			_requestJson.put("logname", _phone);
			_requestJson.put("pwd", _pwd);
//			_requestJson.put("_imei", _imei);
//			_requestJson.put("_os", 0);
//			_requestJson.put("_ver", android.os.Build.VERSION.RELEASE);
//			_requestJson.put("_vers", MarketUtils.GetClientVersionName(_context));

		} catch (Exception e) {

		}
		return _requestJson;
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

//			String net = jsonObject.getString("net");
//			if (net != null) 
//			{
//				MyApplication.getInstance().set_Net(net);
//			}
//			
//			String push = jsonObject.getString("push");
//			if (net != null) 
//			{
//				MyApplication.getInstance().set_Push(push);
//			}
//			
//			String pop = jsonObject.getString("pop");
//			if (pop!=null)
//			{
//				MyApplication.getInstance().Set_pop(pop);
//			}
			
			
			//userInfo.bdyhk = AndroidUtils.getJsonInt(jsonObject, "card", 0);
			//JSONObject info = jsonObject.getJSONObject("info");

//			userInfo.uid = AndroidUtils.getJsonInt(info, "uid", 0);
//			userInfo.phone = AndroidUtils.getJsonString(info, "phone", "");
//			userInfo.cid = AndroidUtils.getJsonString(info, "cid", "");
//			userInfo.uname = AndroidUtils.getJsonString(info, "uname", "");
//			userInfo.sex = AndroidUtils.getJsonInt(info, "sex", 0);
//			userInfo.star = AndroidUtils.getJsonString(info, "star", "");
//			userInfo.headpic = AndroidUtils.getJsonString(info, "face", "");
//			userInfo.is_public = AndroidUtils.getJsonString(info, "is_public", "0");
			
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
