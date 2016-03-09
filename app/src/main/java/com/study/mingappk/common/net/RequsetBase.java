package com.study.mingappk.common.net;


import android.content.Context;
import android.util.Log;

import com.study.mingappk.R;
import com.study.mingappk.common.utils.MarketUtils;
import com.study.mingappk.userlogin.request.ResultPacket;

import org.json.JSONObject;

public abstract class RequsetBase {
    protected Context _contContext;
    public String _url;

    protected JSONObject _requestJson;
    public Boolean isNeedCode = false;//是否需要加密

    public void SetIsNeedCode(Boolean boolean1) {
        isNeedCode = boolean1;
    }

    public JSONObject getRequestJson() {
        return _requestJson;
    }

    public RequsetBase(Context Context) {
        _contContext = Context;
        _url = Context.getResources().getString(R.string.postUrl);
    }


    public abstract ResultPacket DoResponseData(String data);


    public ResultPacket DealProcess() {
        try {
            ResultPacket result = new ResultPacket();
            if (!MarketUtils.checkNetWorkStatus(_contContext)) {
                result.setIsError(true);
                result.setResultCode("99");
                result.setDescription("很抱歉，您的网络已经中断，请检查是否连接。");
                return result;
            }
            getRequestJson();//获取手机号和密码

            //添加统一参数
            _requestJson.put("_imei", MarketUtils.getTelImei(_contContext));
            _requestJson.put("_os", 0);
            _requestJson.put("_ver", MarketUtils.GetClientVersionCode(_contContext));
            _requestJson.put("_vers", MarketUtils.GetClientVersionName(_contContext));


            HttpUtility conn = new HttpUtility();
            conn.SetisNeedDecode(isNeedCode);
            String responseString = conn.openUrl(_contContext, _url, "POST", _requestJson);
            if (responseString == null || responseString.equals("")) {
                result.setIsError(true);
                result.setResultCode("99");
                result.setDescription("亲，网络不给力啊,请检查网络");
                return result;
            }
            result = DoResponseData(responseString);
            return result;

        } catch (Exception e) {

            return new ResultPacket(true, "99", e.getMessage());

        }
    }

}
