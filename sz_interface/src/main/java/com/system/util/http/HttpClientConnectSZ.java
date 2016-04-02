package com.system.util.http;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.system.util.encode.EncodeUtil;
import com.zhrt.util.EU;

/**
 * HttpClient工具类
* @ClassName: HttpClientConnect 
* @Description: TODO 
* @author 杨功平 852704764@qq.com 
* @date 2014年8月8日 上午9:37:39 
*
 */
public class HttpClientConnectSZ {
	
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
	static{
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,connectionTimeOut);  
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeOut);
 
        manager.setMaxTotal(maxTotalConnections);
		manager.setDefaultMaxPerRoute(maxConnectionPerHost);
		
		initialed = true;
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
	public static String getResWithHClientPost(String url,String encode,String json) {
		String resContent = null;
		
		
		try {
			DefaultHttpClient client = new DefaultHttpClient(manager,params);
			HttpPost httpPost = new HttpPost(url);
			byte[] content = EU.getInstance().encode(json.toString().getBytes("UTF8"));
			ByteArrayEntity httpEntity = new ByteArrayEntity(content);
			httpPost.setEntity(httpEntity);
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				InputStream is = entity.getContent();
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				byte[] b = new byte[1024];
				int size = 0;
				while ((size = is.read(b)) != -1) {
					out.write(b, 0, size);
				}
				byte[] bytes = EU.getInstance().decode(out.toByteArray());
				resContent = new String(bytes);
				is.close();
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			resContent = "";
		} finally {
		}
		
		return resContent;
	}
	
}
