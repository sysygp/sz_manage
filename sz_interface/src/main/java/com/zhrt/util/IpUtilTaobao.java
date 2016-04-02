package com.zhrt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.system.util.http.HttpClientConnect;

/**
 * 淘宝IP库工具类
 * @author Administrator
 *
 */
public class IpUtilTaobao extends IpUtil{
	
	/**
	 * 根据ip 获取详情
	 * @param ip
	 * @return
	 */
	public static String getInfoByIp(String ip){
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=223.72.76.7";
		return HttpClientConnect.getResContentWithHClientGet(url, "UTF-8");
	}
	
	/**
	 * 根据ip 获取省份名称
	 * @param ip
	 * @return
	 */
	public static String getProNameByIp(String ip){
		String info = getInfoByIp(ip);
		return ((JSONObject)JSON.parseObject(info).get("data")).getString("region");
	}
}
