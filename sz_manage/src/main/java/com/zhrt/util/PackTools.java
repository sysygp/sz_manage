package com.zhrt.util;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;

import com.system.util.encode.BASE64;
import com.system.util.encryp.CryptoEncode;
import com.zhrt.bo.DynamicGameConfig;

/**
 * 打包工具类
 * @author ：
 * @vision : 1.0.0
 * @createDate : 2015年9月11日 下午4:00:32
 * @email  ：zhu_shz@myepay.cn
 */
public class PackTools {
	
	/**
	 * 获取轻游戏服务平台的动态配置文件流
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static String getFixGameConfigStr(String platId,String platKey,String chanAppVerSeq,String sdkVer,String talkingdataAppid,String talkingdataKey) 
			throws Exception {
		StringBuffer gameConfigStr = new StringBuffer("");
		try {
			
			byte[] srcByte = null;
			
			srcByte = CryptoEncode.ZCryptoEncode(platKey.getBytes(), platId.getBytes());
			platId = BASE64.encryptBASE64(srcByte);
			
			srcByte = CryptoEncode.ZCryptoEncode(platKey.getBytes(), chanAppVerSeq.getBytes());
			chanAppVerSeq = BASE64.encryptBASE64(srcByte);
			
			srcByte = CryptoEncode.ZCryptoEncode(platKey.getBytes(), sdkVer.getBytes());
			sdkVer = BASE64.encryptBASE64(srcByte);
			
			if(StringUtils.isBlank(talkingdataAppid)){
				talkingdataAppid="";
			}
			if(StringUtils.isBlank(talkingdataKey)){
				talkingdataKey="";
			}
			
			gameConfigStr.append("{");
			gameConfigStr.append("\"platId\":\""+platId.replace("\n", "").replace("\r", "")+"\",");
			gameConfigStr.append("\"platKey\":\""+platKey.replace("\n", "").replace("\r", "")+"\",");
			gameConfigStr.append("\"chanAppVerSeq\":\""+chanAppVerSeq.replace("\n", "").replace("\r", "")+"\",");
			gameConfigStr.append("\"talkingdataAppid\":\""+talkingdataAppid.replace("\n", "").replace("\r", "")+"\",");
			gameConfigStr.append("\"talkingdataKey\":\""+talkingdataKey.replace("\n", "").replace("\r", "")+"\",");
			gameConfigStr.append("\"sdkVer\":\""+sdkVer.replace("\n", "").replace("\r", "")+"\"");
			gameConfigStr.append("}");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameConfigStr.toString();
	}
	
	
	
	public static String getDynamicGameConfigStr(DynamicGameConfig dynamicGameConfig,String platKey) 
			throws Exception {
		String gameDynamicConfigStr = "";
		try {

			byte[] srcByte = null;
			
			srcByte = CryptoEncode.ZCryptoEncode(platKey.getBytes(), JSONArray.fromObject(dynamicGameConfig).toString().getBytes());
			gameDynamicConfigStr = BASE64.encryptBASE64(srcByte);
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gameDynamicConfigStr;
	}

}
