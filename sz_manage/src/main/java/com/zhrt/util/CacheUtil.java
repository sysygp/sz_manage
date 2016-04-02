package com.zhrt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.system.util.encode.BASE64;
import com.system.util.encode.EncodeUtil;
import com.system.util.encryp.CryptoEncode;
import com.system.util.encryp.MD5Util;
import com.system.util.http.HttpClientConnect;
import com.system.util.property.PropertiesConfigDynamic;

/**
 * 
 * 缓存服务工具类
 * @author ：
 * @vision : 1.0.0
 * @createDate : 2015年8月26日 下午7:25:19
 * @email  ：zhu_shz@myepay.cn
 */
public class CacheUtil {
	
	public static final String CP = "1";
	public static final String CHANNEL = "2";
	public static final String APP = "3";
	public static final String APPSORT = "4";
	public static final String PROP = "5";
	public static final String NOTICE = "6";
	
	public static String KEY = "ke*#2)7";//加密
	public static String SOURCE = "1";//系统来源
	private static String URL = PropertiesConfigDynamic.getConfig("cacheManageUrl");
	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);

	/**
	 * 
	 * 缓存服务器写缓存
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午7:25:14
	 * 修改人：
	 * 修改时间：
	 * @param type
	 * @param needFlush
	 * @throws Exception
	 */
	public static int cacheWrite(String type,boolean needFlush){
		int code = ErrorCode.SYSTEM_RIGHT;
		String url = new StringBuffer(URL).append("cachew/").append(type).toString();
		JSONObject json;
		try {
			json = send(type, needFlush, url);
			code = json.getInt("code");
		} catch (Exception e) {
			logger.info("缓存更新失败！");
			e.printStackTrace();
			code = ErrorCode.SYSTEM_ERROR;
		}
		return code;
	}
	/**
	 * 
	 * 从缓存服务器读取缓存
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午7:25:57
	 * 修改人：
	 * 修改时间：
	 * @param type
	 * @param needFlush
	 * @return
	 * @throws Exception 
	 */
	public static Object cacheRead(String type){
		Object info = null;
		String url = new StringBuffer(URL).append("cacher/").append(type).toString();
		JSONObject json;
		try {
			json = send(type, false, url);
			int code = json.getInt("code");
			if(ErrorCode.SYSTEM_RIGHT == code){
				info = json.get("info");
			}
		} catch (Exception e) {
			logger.info("缓存读取失败！");
			e.printStackTrace();
		}
		return info;
	}
	
	public static JSONObject send(String type,boolean needFlush,String url) throws Exception{
		//加密参数
		String sign = MD5Util.string2MD5(type+needFlush+KEY);
		//发送包体
		String src = "{\"source\":\""+SOURCE+"\",\"type\":\""+type+"\",\"needFlush\":"+needFlush+",\"sign\":\""+sign+"\"}";
		//加密包体
		byte[] bOut = CryptoEncode.ZCryptoEncode(KEY.getBytes(), src.getBytes());
		//对加密后包体编码
		String send = BASE64.encryptBASE64(bOut);
		String result = HttpClientConnect.getResContentWithHClientPost(url, EncodeUtil.ENCODE, send);
		
		//对返回结果解码并解密
		byte[] ori = BASE64.decryptBASE64(result);
		byte[] res = CryptoEncode.ZCryptoDecode(KEY.getBytes(), ori);
		String resString = new String(res, "utf-8");
		String jsonString = resString.substring(resString.indexOf("{"), resString.lastIndexOf("}")+1);
		System.out.println(jsonString);
		JSONObject json = JSONObject.fromObject(jsonString);
		return json;
	}
	
	public static void main(String[] args) {
//		int code = cacheWrite("1", true);
		Object o = cacheRead(CacheUtil.NOTICE);
		System.out.println(o);
	}
}
