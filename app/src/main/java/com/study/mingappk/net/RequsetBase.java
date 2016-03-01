package com.study.mingappk.net;


import android.content.Context;

import com.study.mingappk.R;
import com.study.mingappk.utils.MarketUtils;

import org.json.JSONObject;

public abstract class RequsetBase {

	protected Context _contContext;
	private String _responseData;
	public String _url;
	
	
	public Boolean isNeedCode=false;//是否需要加密
	public void SetIsNeedCode(Boolean boolean1)
	{
		isNeedCode=boolean1;
	}
	
    protected JSONObject _requestJson;
    
    public JSONObject getRequestList() {
        return _requestJson;
    }
    
    protected JSONObject _responseList = new JSONObject();
    
    public RequsetBase(Context Context)
    {
        _contContext = Context;
        _url = Context.getResources().getString(R.string.postUrl);
    }
    
    public String getResponseData() {
        return _responseData;
    }
    
    public JSONObject DoBeforeSendData()
    {
    	return _requestJson;
    }
    
    public abstract ResultPacket DoResponseData(String data);
    
    public ResultPacket DealProcess()
    {
    	try 
    	{
    		 ResultPacket result = new ResultPacket();
             if (!MarketUtils.checkNetWorkStatus(_contContext)) {
                 result.setIsError(true);
                 result.setResultCode("99");
                 result.setDescription("很抱歉，您的网络已经中断，请检查是否连接。");
                 return result;
             }
             DoBeforeSendData();             
             
             //添加统一参数
 			_requestJson.put("_imei", MarketUtils.getTelImei(_contContext));
 			_requestJson.put("_os", 0);
 			_requestJson.put("_ver", MarketUtils.GetClientVersionCode(_contContext));
 			_requestJson.put("_vers", MarketUtils.GetClientVersionName(_contContext));
             
             
             HttpUtility  conn = new HttpUtility();
             conn.SetisNeedDecode(isNeedCode);      
          
//             //用来区别是否是易办客  1：易办客
//             _requestJson.put("app_type", "1");
// 			if (MyApplication.getInstance().get_Bid()!=null && MyApplication.getInstance().get_Bid().length()>1)
//             {            	
//            	 _requestJson.put("bid", MyApplication.getInstance().get_Bid());
//             }
//             else if(MyApplication.getInstance().get_userInfo()!=null && MyApplication.getInstance().get_userInfo().bid!=null &&MyApplication.getInstance().get_userInfo().bid.length()>1)
//             {
//            	 _requestJson.put("bid", MyApplication.getInstance().get_userInfo().bid);            	
//             }
//             
             
             String responseString = conn.openUrl(_contContext, _url, "POST", _requestJson);
             if(responseString == null || responseString.equals(""))
 			 {
 				result.setIsError(true);
 				result.setResultCode("99");
 				result.setDescription("亲，网络不给力啊,请检查网络或拨打客服热线400-009-2006");
 				return result;
 			 }
             result = DoResponseData(responseString);

             return result;
		} 
    	catch (Exception e)
    	{
    		return new ResultPacket(true, "99", e.getMessage());
		}
    }
    
    public ResultPacket DealProcess(String PostType)
    {
    	try 
    	{
    		 ResultPacket result = new ResultPacket();
             //判断网上释放连接、
             if (!MarketUtils.checkNetWorkStatus(_contContext)) {
                 result.setIsError(true);
                 result.setResultCode("99");
                 result.setDescription("很抱歉，您的网络已经中断，请检查是否连接。");
                 return result;
             }
             DoBeforeSendData();
             HttpUtility conn = new HttpUtility();
             String responseString = conn.openUrl(_contContext, _url, PostType, _requestJson);
             result = DoResponseData(responseString);
        	 
             return result;
		} 
    	catch (Exception e)
    	{
    		return new ResultPacket(true, "99", e.getMessage());
		}
    }
}
