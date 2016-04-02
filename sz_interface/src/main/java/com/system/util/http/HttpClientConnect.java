package com.system.util.http;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.system.util.encode.EncodeUtil;
import com.system.util.encode.UnicodeUtil;

import net.sf.json.util.JSONUtils;

/**
 * HttpClient工具类
* @ClassName: HttpClientConnect 
* @Description: TODO 
* @author 杨功平 852704764@qq.com 
* @date 2014年8月8日 上午9:37:39 
*
 */
public class HttpClientConnect {
	
	public static final String RES_HEADER = "resHeaders";
	public static final String RES_CONTENT = "resContent";
	
	public static final String PARAM_TYPE_STRING = "StringEntity";
	public static final String PARAM_TYPE_FORM = "UrlEncodedFormEntity";

	// 获得ConnectionManager，设置相关参数 
	protected static PoolingClientConnectionManager manager = new PoolingClientConnectionManager();
	private static final int connectionTimeOut = 3000;	//连接超时时间
	private static final int socketTimeOut = 18000;		//读取返回数据时间。默认改回6秒 
	private static final int maxConnectionPerHost = 100;	// 
	private static final int maxTotalConnections = 200;	//最大连接数
	
	protected static HttpParams params = new BasicHttpParams(); 

	// 标志初始化是否完成的flag
	protected static boolean initialed = false;

	// 初始化PoolingClientConnectionManager的方法
	public static void SetPara() {
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,connectionTimeOut);  
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeOut);
 
        manager.setMaxTotal(maxTotalConnections);
		manager.setDefaultMaxPerRoute(maxConnectionPerHost);
		
		initialed = true;
	}
	
	/**
	* 通过post方法获取响应流中的响应报头
	* 一般用于模拟登录
	* @Title: getResHeadWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @return Header[]
	 */
	public static Header[] getResHeadWithHClientPost(String url,String encode) {
		return getResHeadWithHClientPost(url, encode, null);
	}

	/**
	* 通过post方法获取响应流中的响应报头
	* 一般用于模拟登录
	* @Title: getResHeadWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param data
	* @return Header[]
	 */
	public static Header[] getResHeadWithHClientPost(String url,String encode,String data) {
		return getResHeadWithHClientPost(url, encode, data, null);
	}

	/**
	* 通过post方法获取响应流中的响应报头
	* 一般用于模拟登录
	* @Title: getResHeadWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param data
	* @param headersReq
	* @return Header[]
	 */
	public static Header[] getResHeadWithHClientPost(String url,String encode,String data,Header[] headersReq) {
		return (Header[])getResWithHClientPost(url, encode, data, headersReq).get(RES_HEADER);
	}
	
	
	/**
	 * 通过post方法获取响应流中的响应包体
	* @Title: getResContentWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @return String
	 */
	public static String getResContentWithHClientPost(String url, String encode) {
		return getResContentWithHClientPost(url, encode, null);
	}
	
	
	/**
	 * 通过post方法获取响应流中的响应包体
	* @Title: getResContentWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param data
	* @return String
	 */
	public static String getResContentWithHClientPost(String url,String encode,String data) {
		return getResContentWithHClientPost(url, encode, data, null);
	}

	/**
	 * 通过post方法获取响应流中的响应包体
	* @Title: getResContentWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param data
	* @param headersReq
	* @return String
	 */
	public static String getResContentWithHClientPost(String url,String encode,String data,Header[] headersReq) {
		return getResWithHClientPost(url, encode, data, headersReq).get(RES_CONTENT).toString();
	}
	
	/**
	 * 通过post方法获取返回信息，包括响应报头和响应包体
	* @Title: getResWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param data
	* @param headersReq
	* @return Map
	 */
	public static Map getResWithHClientPost(String url,String encode,String strParams,Header[] headersReq) {
		return getResWithHClientPost(url, encode, PARAM_TYPE_STRING, strParams, headersReq);
	}
	
	/**
	 * 通过post方法获取返回信息，包括响应报头和响应包体
	* @Title: getResWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param formParams
	* @param headersReq
	* @return Map
	 */
	public static Map getResWithHClientPost(String url,String encode,List<NameValuePair> formParams,Header[] headersReq) {
		return getResWithHClientPost(url, encode, PARAM_TYPE_FORM, formParams, headersReq);
	}
	
	/**
	 * 通过post方法获取返回信息，包括响应报头和响应包体
	* @Title: getResWithHClientPost 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param paramType 参数组装类型 参考：PARAM_TYPE_STRING和PARAM_TYPE_FORM
	* @param paramVal 参数内容，具体类型需要和paramType匹配
	* @param headersReq 请求包头
	* @return Map
	 */
	public static Map getResWithHClientPost(String url,String encode,String paramType,Object paramVal,Header[] headersReq) {
		Map<String,Object> resMap = new HashMap<String,Object>();
		Header[] resHeaders = null;
		String resContent = null;
		
		if(StringUtils.isBlank(encode)){
			encode = EncodeUtil.ENCODE;
		}
		DefaultHttpClient client = new DefaultHttpClient(manager,params);
		client.getParams().setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET,encode);
		
		if(!initialed){
			HttpClientConnect.SetPara();
		}
		HttpPost post = new HttpPost(url);
		
		
		try {
			if(headersReq != null && headersReq.length > 0){				
				for(Header header : headersReq){
					post.setHeader(header.getName(), header.getValue());
				}
			}
			
			if(paramVal != null){	
				if(PARAM_TYPE_STRING.equals(paramType)){
					post.setEntity(new StringEntity((String)paramVal));
				}else if(PARAM_TYPE_FORM.equals(paramType)){
					UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity((List<NameValuePair>)paramVal, "UTF-8");
					post.setEntity(formEntity);
				}
			}
			
			HttpResponse response = client.execute(post);
			
			resHeaders = response.getAllHeaders();
			HttpEntity entity = response.getEntity();
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			
			StringBuffer resultBuffer = new StringBuffer();
			String lineData = "";
			while((lineData = br.readLine()) != null){
				resultBuffer.append(new String(lineData.getBytes(),encode)).append("\n");
			}
			br.close(); 
			EntityUtils.consume(entity);
			
			resContent = resultBuffer.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			resContent = "";
		} finally {
			post.releaseConnection();
		}
		
		resMap.put(RES_HEADER, resHeaders==null?new Header[]{}:resHeaders);
		resMap.put(RES_CONTENT, resContent==null?"":resContent);
		
		return resMap;
	}
	
	
	/**
	 * 通过get方法获取响应流中的响应报头
	* @Title: getResHeaderWithHClientGet 
	* @Description: TODO 
	* @param url
	* @param encode
	* @return Header[]
	 */
	public static Header[] getResHeaderWithHClientGet(String url, String encode) {
		return (Header[])getResContentWithHClientGet(url, encode, null).get(RES_HEADER);
	}

	/**
	 * 通过get方法获取响应流中的响应包体
	* @Title: getResContentWithHClientGet 
	* @Description: TODO 
	* @param url
	* @param encode
	* @return String
	 */
	public static String getResContentWithHClientGet(String url, String encode) {
		return getResContentWithHClientGet(url, encode, null).get(RES_CONTENT).toString();
	}
	
	/**
	 * 通过get方法获取返回信息，包括响应报头和响应包体
	* @Title: getResContentWithHClientGet 
	* @Description: TODO 
	* @param url
	* @param encode
	* @param headersReq
	* @return Map
	 */
	public static Map getResContentWithHClientGet(String url, String encode, Header[] headersReq) {
		Map<String,Object> resMap = new HashMap<String,Object>();
		Header[] resHeaders = null;
		String resContent = null;
		
		DefaultHttpClient client = new DefaultHttpClient(manager, params);
		client.getParams().setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET, encode);

		if (!initialed) {
			HttpClientConnect.SetPara();
		}

		HttpGet get = new HttpGet(url);
		try {
			if(headersReq != null && headersReq.length > 0){				
				for(Header header : headersReq){
					get.setHeader(header.getName(), header.getValue());
				}
			}
			
			HttpResponse response = client.execute(get);
			
			
			resHeaders = response.getAllHeaders();
			
			HttpEntity entity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuffer resultBuffer = new StringBuffer();
            String lineData = "";
            while((lineData = br.readLine()) != null){
            	resultBuffer.append(new String(lineData.getBytes(),encode)).append("\n");
            }
            br.close(); 
            EntityUtils.consume(entity);
            
            resContent = resultBuffer.toString();

		} catch (Exception e) {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()) + " httpclient error:::" + url);
			e.printStackTrace();
			resContent = "";
		} finally {
			get.releaseConnection();
		}

		resMap.put(RES_HEADER, resHeaders==null?new Header[]{}:resHeaders);
		resMap.put(RES_CONTENT, resContent==null?"":resContent);
		return resMap;
	}
	
	

	// 编码转换
	protected static String ConverterStringCode(String source, String srcEncode,String destEncode) {
		if (source != null) {
			try {
				if (!srcEncode.equals("") && !destEncode.equals("")) {
					return new String(source.getBytes(srcEncode), destEncode);
				} else {
					return source.toString();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			}
		} else {
			return "";
		}
	}

	/*public static class UTF8PostMethod extends PostMethod {
		public UTF8PostMethod(String url) {
			super(url);
		}

		public String getRequestCharSet() {
			return "UTF-8";
		}
	}*/
	
}
