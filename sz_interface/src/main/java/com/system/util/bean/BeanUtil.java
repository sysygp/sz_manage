/**   
* @Title: BeanUtil.java 
* @Package com.system.zhrt.core.utils.bean 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年11月17日 上午1:24:17 
* @version V1.0   
*/
package com.system.util.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.system.util.date.DateUtil;

/**
 * @ClassName: BeanUtil 
 * @Description: TODO 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年11月17日 上午1:24:17 
 * */
public class BeanUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
	/**
	 * 根据一个对象的属性去赋值
	* @Title: setAttrributeValue 
	* @param obj
	* @param attribute
	* @param value
	* @throws IllegalAccessException
	* @throws InvocationTargetException void 
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月17日 上午1:30:34
	 */
	public static void setAttrributeValue(Object obj, String attribute, Object value) throws IllegalAccessException, InvocationTargetException {
		String met = attribute.trim();
		if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase())) {
			met = met.substring(0, 1).toUpperCase() + met.substring(1);
		}
		met = "set" + met;
		// -------------------------
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			/**
			 * 
			 * 因为这里只是调用bean中属性的set方法，属性名称不能重复 所以set方法也不会重复，所以就直接用方法名称去锁定一个方法
			 * （注：在java中，锁定一个方法的条件是方法名及参数）
			 * **/
			if (method.getName().equals(met)) {
				Class[] parameterC = method.getParameterTypes();
				try {
					/**
					 * 如果是基本数据类型时（如int、float、double、byte、char、boolean）
					 * 需要先将Object转换成相应的封装类之后再转换成对应的基本数据类型 否则会报
					 * ClassCastException
					 **/
					// System.out.println(parameterC[0]);
					logger.info("parameterC=" + parameterC[0]);
					if (parameterC[0] == int.class || parameterC[0] == Integer.class) {
						try {
							Integer intValue = Integer.parseInt(String.valueOf(value));
							method.invoke(obj, intValue);
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == float.class || parameterC[0] == Float.class) {
						try {
							Float floatValue = Float.parseFloat(String.valueOf(value));
							method.invoke(obj, floatValue);
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == double.class || parameterC[0] == Double.class) {
						try {
							Double doubleValue = Double.parseDouble(String.valueOf(value));
							method.invoke(obj, doubleValue);
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == Number.class) {
						try {
							Number numberValue = NumberFormat.getInstance().parse(String.valueOf(value));
							method.invoke(obj, numberValue);
							break;
						} catch (Exception e) {
							break;
						}

					} else if (parameterC[0] == byte.class) {
						try {
							method.invoke(obj, ((Byte) value).byteValue());
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == char.class) {
						try {
							method.invoke(obj, ((Character) value).charValue());
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == boolean.class) {
						try {
							method.invoke(obj, ((Boolean) value).booleanValue());
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == Date.class) {
						try {
							String dateStr = String.valueOf(value);
							if (StringUtils.isNotBlank(dateStr) && !dateStr.equals("null") && !dateStr.equals("0")) {
								Date dateValue = DateUtil.DF_YMDHMS.parse(dateStr);
								method.invoke(obj, dateValue);
							}
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == long.class || parameterC[0] == Long.class) {
						try {
							Long longValue = Long.parseLong(String.valueOf(value));
							method.invoke(obj, longValue);
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == BigDecimal.class) {
						// Long longValue =
						// Long.parseLong(String.valueOf(value));
						// BigDecimal bigValue = new
						// BigDecimal(Double.toString(longValue));
						try {
							BigDecimal bigValue = new BigDecimal(String.valueOf(value));
							method.invoke(obj, bigValue);
						} catch (Exception e) {
							break;
						}
						break;
					} else if (parameterC[0] == java.sql.Time.class) {
						try {
							java.sql.Time longValue = java.sql.Time.valueOf(String.valueOf(value));
							method.invoke(obj, longValue);
						} catch (Exception e) {
							break;
						}
						break;
					} else {
						try {
							method.invoke(obj, parameterC[0].cast(value));
						} catch (Exception e) {
							break;
						}
						break;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * map对象转换为Bean对象
	* @Title: mapTransBean 
	* @param parameters
	* @param obj
	* @return Object 
	* @author 杨功平 852704764@qq.com
	* @date 2014年11月17日 上午1:34:15
	 */
	public static Object mapTransBean(Map<String, String> parameters,Object obj){
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
	    * 转换传递过来的参数信息
	    * @param Map：传递的参数
	    * @param cmd:请求编码
	    * @param Object:目标对象
	    * @return
	    */
		public static Object mapTransObject(Map<String, String> parameters,Object obj)
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
							 //System.out.println("key=" + strKey + " value=" + strValue);
							logger.info("key=" + strKey + " value=" + strValue);
							// 键值都不为空时进行赋值
							if (StringUtils.isNotBlank(strKey)&&!strKey.trim().equals("null")&& StringUtils.isNotBlank(strValue)&&!strValue.trim().equals("null")) {
								if (StringUtils.isNotBlank(strKey))
									setAttrributeValue(obj, strKey.trim(), strValue.trim());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			return obj;
		}
		
		/**
		 * Map拷贝-原Map拷贝到目标Map
		 * 
		 * @param Map
		 *            ：传递的参数
		 * @param dMap
		 *            :请求编码
		 * @param Object
		 *            :目标对象
		 * @return
		 */
		public static Map mapTransNewMap(Map<String, String> parameters, Map<String, String> useMap) {
			// 传到数据库中的数据值
			Map<String, String> retMap = new HashMap<String, String>();
			try {
				if (parameters != null && !parameters.isEmpty()) {
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
							logger.info("key=" + strKey + " value=" + strValue);
							String useValue = useMap.get(strKey);
							if (StringUtils.isNotBlank(useValue)&&StringUtils.isNotBlank(strValue)) {
								retMap.put(useValue, strValue);
							}

						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							continue;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return retMap;
		}
}
