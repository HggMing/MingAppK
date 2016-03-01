package com.study.mingappk.net;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Base64;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

/*
 * 网络请求
 */
public class HttpUtility
{
	public final String HTTPMETHOD_POST = "POST";
	public final String HTTPMETHOD_GET = "GET";
	
	private final static int SET_CONNECTION_TIMEOUT = 10000; //链接超时的时间
    private final static int SET_SOCKET_TIMEOUT = 10000;//socket超时的时间
	
    private Boolean isNeedDecode =false;//是否需要加密
    
    public void SetisNeedDecode(Boolean boolean1)
    {
    	isNeedDecode = boolean1;
    }
    
    
    
    /**
     * 访问连接
     * @param context
     * @param url
     * @param method
     * @param params
     * @return
     */
	public String openUrl(Context context, String url, String method, JSONObject params)
	{
		String result = "";
		try 
		{
			HttpClient client = getHttpClient(context);
            HttpUriRequest request = null;
            ByteArrayOutputStream bos = null;
            if (method.equals(HTTPMETHOD_POST))
            {
            	HttpPost post = new HttpPost(url);
                byte[] data = null;
                bos = new ByteArrayOutputStream(1024 * 50);
                
                post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                String postParam = getParamStr(params);
                data = postParam.getBytes("UTF-8");
                bos.write(data);
                
                data = bos.toByteArray();
                bos.close();
                ByteArrayEntity formEntity = new ByteArrayEntity(data);
                post.setEntity(formEntity);
                request = post;
                
                HttpResponse response = client.execute(request);
                StatusLine status = response.getStatusLine();
                int statusCode = status.getStatusCode();
                if(statusCode!=200)
                {
                	return "";
                }
                result = read_1(response);
                return result;
            }
            else if (method.equals(HTTPMETHOD_GET))
            {
            	HttpGet get = new HttpGet(url);
                request = get;
                
                HttpResponse response = client.execute(request);
                StatusLine status = response.getStatusLine();
                int statusCode = status.getStatusCode();
                if(statusCode!=200)
                {
                	return "";
                }
                result = read(response);
                return result;
			}
		} 
		catch (Exception e)
		{
		}
		return "";
	}
	
	
	
	/**
     * 初始化HttpClient
     * @param context
     * @return 返回HttpClient
     */
    public HttpClient getHttpClient(Context context) {
        BasicHttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, SET_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParameters, SET_SOCKET_TIMEOUT);

        HttpClient client = new DefaultHttpClient(httpParameters);
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled())
        {
            String proxyHost = android.net.Proxy.getDefaultHost();
            int port = android.net.Proxy.getDefaultPort() == -1 ? 80 : android.net.Proxy.getDefaultPort();
            if (proxyHost != null) {
                HttpHost httpHost = new HttpHost(proxyHost, port, "http");
                client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, httpHost);
            } else {
                client.getParams().removeParameter(ConnRouteParams.DEFAULT_PROXY);
            }
        }

        return client;
    }

    /**
     * 读取返回内容
     * @param response
     * @return
     * @throws
     */
    private String read(HttpResponse response)
    {
        String result = "";
        HttpEntity entity = response.getEntity();
        InputStream inputStream;
        try {
            inputStream = entity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();

            Header header = response.getFirstHeader("Content-Encoding");
            if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
                inputStream = new GZIPInputStream(inputStream);
            }

            // Read response into a buffered stream
            int readBytes = 0;
            byte[] sBuffer = new byte[1024];
            while ((readBytes = inputStream.read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes);
            }
            // Return result from buffered stream
            
            result = new String(content.toByteArray());

            result = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
            
            return result;
        } 
        catch (Exception e)
        {
            
        } 
        return null;
    }
    
    private String read_1(HttpResponse response)
    {
        HttpEntity entity = response.getEntity();
        InputStream inputStream;
        try 
        {
        	inputStream = entity.getContent();
        	StringBuffer out = new StringBuffer();
        	byte[] b = new byte[1024]; 
        	int readBytes = 0;
        	while ((readBytes = inputStream.read(b))!= -1)
        	{   
                out.append(new String(b,0,readBytes));
        	}
        	
        	if (isNeedDecode)
        	{
        		return new String(Base64.decode(out.toString(), Base64.DEFAULT));
        	}
        	else 
        	{
        		return out.toString();
        	}
        	
		} 
        catch (Exception e)
        {
			
		}
        return null;
    }
    
    /**
     * 从json中拼接参数URL
     * **/    
    public String getParamStr(JSONObject parameters){
    	StringBuilder dataBfr = new StringBuilder();
    	Iterator it = parameters.keys();
    	for (;it.hasNext();) {
    		String key = it.next().toString();
	    	if (dataBfr.length() != 0) {
	    		dataBfr.append('&');
	    	}
	    	Object value = null;
			try {
				value = parameters.get(key);
				try {
					dataBfr.append(URLEncoder.encode(key.toString(), "UTF-8"));
					dataBfr.append('=').append(URLEncoder.encode(value.toString(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
	    	if (value == null) {
	    		value = "";
	    	}
    	}
    	if (isNeedDecode)
    	{
    		return "data="+ Base64.encodeToString(dataBfr.toString().getBytes(), Base64.DEFAULT);
    	}
    	else 
    	{
    		return dataBfr.toString();    	 
      	}
    }

}
