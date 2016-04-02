/**   
* @Title: StringTransUtil.java 
* @Package com.zhrt.common.util 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年11月14日 上午11:06:34 
* @version V1.0   
*/
package com.system.util.stringutil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**字符串和其他格式互转工具类
 * @ClassName: StringTransUtil 
 * @Description: 字符串和其他格式互转工具类 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年11月14日 上午11:06:34 
 * */
public class StringTransUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(StringTransUtil.class);

	/**
	 * map集合转化为URL参数形式字符串，并将字符串中参数按照升序排序(用于构造字符串)
	* @Title: mapToString 
	* @param map Map数组
	* @return String 升序排序后的参数形式的字符串，如： a=a1&b=b1&c=c1 格式的字符串。转化失败返回null
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月14日 上午11:11:37
	 */
	public static String mapToString(Map<String, String> map){
		String transedStr = null;
		
		try{
			if (map == null || map.isEmpty()) {
				return null;
			}

			Object[] keyList = map.keySet().toArray();
			Arrays.sort(keyList);

			StringBuilder res = new StringBuilder(128);
	        for (Object aKey : keyList) {
	            res.append(aKey).append("=").append(map.get(aKey)).append("&");
	        }

	        transedStr = res.substring(0, res.length() - 1);
	        
	        logger.debug(" mapToString result:"+transedStr);
		}catch(Exception e){
			transedStr = null;
			logger.error(" mapToString error:" + e.getLocalizedMessage());
		}
		
		return transedStr;
	}
	
	
	/**
	 * URL参数形式字符串转化为map集合
	* @Title: stringToMap 
	* @param paramStr URL参数形式字符串
	* @return Map<String,String> map形式的参数。转化失败返回null
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月14日 上午11:30:01
	 */
	public static Map<String, String> stringToMap(String paramStr){
		logger.debug(" stringToMap param:"+paramStr);
		
		Map<String, String> transedMap = null;
		try{
			
			if (StringUtils.isBlank(paramStr)) {
				transedMap = null;
				return transedMap;
			}
			
			transedMap = new HashMap<String, String>();
			String[] split = paramStr.split("&");
	        for (String aSplit : split) {
	            String[] temp = aSplit.split("=");
	            if (temp.length == 1) {
	                transedMap.put(temp[0], "");
	            }
	            if (temp.length > 1) {
	                transedMap.put(temp[0], temp[1]);
	            }
	        }
			
			logger.debug(" stringToMap result: ok");
		}catch(Exception e){
			transedMap = null;
			logger.error(" stringToMap error:" + e.getLocalizedMessage());
		}
		
		return transedMap;
	}
	
	
	
}
