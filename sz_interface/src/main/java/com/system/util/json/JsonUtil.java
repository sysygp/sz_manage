package com.system.util.json;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;

import com.system.util.date.DateUtil;

/**
 * 
 * @Description:json工具类，用于处理json和实体类之间的转换
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月11日 下午5:52:15
 */
public class JsonUtil {
	
	/** 
	* 从一个JSON数组得到一个java对象集合 
	* @param object 
	* @param clazz 
	* @return 
	*/ 
	public static List json2ClazzList(String jsonString, Class clazz){ 	
		JSONArray array = JSONArray.fromObject(jsonString); 
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss"};  
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		List list = new ArrayList(); 
		for(Iterator iter = array.iterator(); iter.hasNext();){ 
			JSONObject jsonObject = (JSONObject)iter.next(); 
			list.add(JSONObject.toBean(jsonObject, clazz)); 
		}
		return list; 
	} 
	/** 
	* 从一个JSON 对象字符格式中得到一个java对象，形如： 
	* @param object 
	* @param clazz 
	* @return 
	*/ 
	public static Object json2Clazz(String jsonString, Class clazz) { 
		JSONObject jsonObject = null; 
		try{ 
			 if(StringUtils.isNotBlank(jsonString))
				 jsonObject = JSONObject.fromObject(jsonString); 
			 return JSONObject.toBean(jsonObject, clazz); 
		}catch(Exception e){ 
			e.printStackTrace();
		} 
		return null;
	} 
	
	/**
	 * 将list结合转换为JSONArray类型字符串
	 * @param list
	 * @return
	 */
	public static String listToJSONArray(List<Object> list) {
		JSONArray jsonArry = new JSONArray();
		
		JSONObject obj = null;
		if(list!=null && list.size()>0){
			for(Object javaBean : list){
				obj = new JSONObject();
				
				Method[] methods = javaBean.getClass().getDeclaredMethods();
				for (Method method : methods) {
					try {
						if (method.getName().startsWith("get")) {
							String field = method.getName();
							field = field.substring(field.indexOf("get") + 3);
							field = field.toLowerCase().charAt(0) + field.substring(1);
		
							Object value = method.invoke(javaBean, (Object[]) null);
							if(method.getReturnType().getName().equals("java.util.Date")){
								obj.put(field, null == value ? null : DateUtil.DF_YMD.format(value));
							}else{
								obj.put(field, null == value ? "" : value.toString());
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				jsonArry.add(obj);
			}
		}
		
		return jsonArry.toString();
	}
	
	public static void main(String[] args){
//		StringBuffer stb = new StringBuffer();
//		stb.append("[");
//		stb.append("{\"imei\":\"111111111111111\",\"platseq\":\"1\"},");
//		stb.append("{\"imei\":\"222222222222222\",\"platseq\":\"2\"}");
//		stb.append("]");
//		List list = json2ClazzList(stb.toString(),TestVO.class);		
//		System.out.print(list.size());
	}
	
	public class TestVO {

		private String imei;
		private String platseq;
		
		
		public String getImei() {
			return imei;
		}
		public void setImei(String imei) {
			this.imei = imei;
		}
		public String getPlatseq() {
			return platseq;
		}
		public void setPlatseq(String platseq) {
			this.platseq = platseq;
		}
		
		
	}

}
