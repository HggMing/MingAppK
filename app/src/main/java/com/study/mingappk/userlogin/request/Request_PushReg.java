package com.study.mingappk.userlogin.request;

import android.content.Context;

import com.study.mingappk.common.net.RequsetBase;
import com.study.mingappk.userlogin.request.ResultPacket;

import org.json.JSONObject;

public class Request_PushReg extends RequsetBase
{
	private String _me;
	private String _app;
	private String _cid;

	public Request_PushReg(Context Context, String me, String app, String cid)
	{
		super(Context);
		this._me = me;
		this._app = app;
		this._cid = cid;
		//_url = MyApplication.getInstance().get_Push()+"/client/register";
		_url = "http://push.traimo.com/client/register";
		
		super.SetIsNeedCode(false);
	}
	
	public JSONObject getJSONObject()
	{
		_requestJson = new JSONObject();
		try 
		{
			_requestJson.put("me", _me);
			_requestJson.put("os", "1");
			_requestJson.put("app", _app);
			_requestJson.put("cid", _cid);
		} 
		catch (Exception e)
		{
			
		}
		return _requestJson;
	}

	@Override
	public ResultPacket DoResponseData(String data)
	{
		ResultPacket result = new ResultPacket();
		try 
		{
			JSONObject jsonObject = new JSONObject(data);

            int error = jsonObject.getInt("err");
            if(error != 0)
            {
            	result.setIsError(true);
                result.setResultCode("99");
                result.setDescription(jsonObject.getString("msg"));
                return result;
            }
            
            return result;
		} 
		catch (Exception e)
		{
			result.setIsError(true);
            result.setResultCode("99");
            result.setDescription("连接服务器失败，请检查网络是否正常");
            return result;
		}
	}

}
