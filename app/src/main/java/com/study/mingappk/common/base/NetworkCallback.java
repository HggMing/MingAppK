package com.study.mingappk.common.base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 网络回调接口
 */
public interface NetworkCallback {
    /**
     * Json解析
     * @param code
     * @param respanse  json响应
     * @param tag
     * @param pos
     * @param data
     * @throws JSONException
     */
    void parseJson(int code, JSONObject respanse, String tag, int pos, Object data) throws JSONException;
    void getNetwork(String uri, String tag);
}
