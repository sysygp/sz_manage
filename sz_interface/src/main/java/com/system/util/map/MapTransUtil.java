/**   
* @Title: MapTransUtil.java 
* @Package com.system.zhrt.core.utils.maputil 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年11月17日 上午1:20:21 
* @version V1.0   
*/
package com.system.util.map;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.system.util.bean.BeanUtil;
import com.system.util.date.DateUtil;

/**
 * @ClassName: MapTransUtil 
 * @Description: TODO 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年11月17日 上午1:20:21 
 * */
public class MapTransUtil {
	/**
	    * 转换传递过来的参数信息
	    * @param Map：传递的参数
	    * @param cmd:请求编码
	    * @param Object:目标对象
	    * @return
	    */
		public static Object map2Bean(Map<String, String> parameters,Object obj)
		{
			//传到数据库中的数据值
			try {
				if (parameters!=null&&!parameters.isEmpty()) {
					Iterator iter = parameters.entrySet().iterator();
					while (iter.hasNext()) {
						try {
							Map.Entry entry = (Map.Entry) iter.next();
							String strKey = null;
							String strValue = null;
							if (entry.getKey() != null)
								strKey = entry.getKey().toString();
							if (entry.getValue() != null)
								strValue = entry.getValue().toString();
							// 键值都不为空时进行赋值
							if (StringUtils.isNotBlank(strKey)&&!strKey.trim().equals("null")&& StringUtils.isNotBlank(strValue)&&!strValue.trim().equals("null")) {
								if (StringUtils.isNotBlank(strKey))
									BeanUtil.setAttrributeValue(obj, strKey.trim(), strValue.trim());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			return obj;
		}
		
	
		
	/**
	 * 将JavaBean对象转换为Map对象
	 * @param javaBean
	 * @return
	 */
	public static Map<String, String> bean2Map(Object javaBean) {
		Map<String, String> result = new HashMap<String, String>();
		Method[] methods = javaBean.getClass().getDeclaredMethods();

		for (Method method : methods) {
			try {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					Object value = method.invoke(javaBean, (Object[]) null);
					if(method.getReturnType().getName().equals("java.util.Date")){
						result.put(field, null == value ? null : DateUtil.DF_YMDHMS.format(value));
					}else{
						result.put(field, null == value ? null : value.toString());
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
