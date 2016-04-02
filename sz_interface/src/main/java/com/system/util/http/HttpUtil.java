/**   
* @Title: HttpUtil.java 
* @Package com.zhrt.common.util 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年11月14日 上午10:59:20 
* @version V1.0   
*/
package com.system.util.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.util.stringutil.StringTransUtil;

/**
 * @ClassName: HttpUtil 
 * @Description: Http工具类 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年11月14日 上午10:59:20 
 * */
public class HttpUtil {
	
	//添加日志信息 
	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	public static final String PARAM_STR_NAME = "paramStr_flag";
	
	/**
	 * 获取http请求中的参数信息
	* @Title: getHttpReqInfo 
	* @param request
	* @return Map 
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月14日 上午11:35:55
	 */
	@SuppressWarnings("unchecked")
	public static Map getHttpReqInfo(HttpServletRequest request){
		Map<String, String> httpReqMap = new HashMap<String, String>();
		try {
			
			httpReqMap = getHttpReqInfoByForm(request);
			
			if(httpReqMap == null || httpReqMap.size() == 1){
				httpReqMap = getHttpReqInfoByStream(request);
			}
			
		} catch (Exception e) {
			logger.error("getHttpReqInfo error: "+e.getLocalizedMessage());
			return httpReqMap;
		}
		return httpReqMap;
	}
	
	/**
	 * 获取数据流提交形式参数
	* @Title: getHttpReqInfoByStream 
	* @param request
	* @return Map 数据流提交形式的参数组装的map
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月14日 下午12:05:22
	 */
	public static Map getHttpReqInfoByStream(HttpServletRequest request){
		Map<String, String> httpReqMap = new HashMap<String, String>();

		try {
			
			//接收数据流
			StringBuffer input = new StringBuffer("");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String data = null;
			while ((data = br.readLine()) != null) {
				input.append(data);
			}
			br.close();
			
			if (input.length() > 0) {
				httpReqMap.put(PARAM_STR_NAME, input.toString());
				
				Map<String, String> transedMap = StringTransUtil.stringToMap(input.toString());
				if(transedMap != null && !transedMap.isEmpty()){
					httpReqMap.putAll(transedMap);
				}
			}else{
				httpReqMap.put(PARAM_STR_NAME, "");
			}
			
		} catch (Exception e) {
			logger.error("getHttpReqInfoByStream error: "+e.getLocalizedMessage());
			return httpReqMap;
		}
		return httpReqMap;
	}
	
	
	/**
	 * 获取表单提交形式参数
	* @Title: getHttpReqInfoByForm 
	* @param request
	* @return Map 表单提交形式的参数组装的map
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月14日 上午11:59:57
	 */
	public static Map getHttpReqInfoByForm(HttpServletRequest request){
		Map<String, String> httpReqMap = new HashMap<String, String>();
		try {
			
			//表单提交形式获取
			StringBuffer paramStr = new StringBuffer("");
			Enumeration<?> enume = request.getParameterNames();
			while (enume!=null&&enume.hasMoreElements()) {
				String paramName = enume.nextElement().toString();
				String value = request.getParameter(paramName).trim().replace("\\r", "").replace("\\n", "");
				httpReqMap.put(paramName, value);  
				paramStr.append(paramName+"="+value+"&");
			}
			if(paramStr.length()>0 && paramStr.toString().endsWith("&")){
				paramStr.setLength(paramStr.length()-1);
			}
			
			httpReqMap.put(PARAM_STR_NAME, paramStr.toString());
			logger.debug("getHttpReqInfoByForm ok,paramStr is: "+paramStr.toString());
		} catch (Exception e) {
			logger.error("getHttpReqInfoByForm error: "+e.getLocalizedMessage());
			return httpReqMap;
		}
		return httpReqMap;
	}
	
}
