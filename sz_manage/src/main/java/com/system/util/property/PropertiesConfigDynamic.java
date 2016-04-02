package com.system.util.property;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * 
* @ClassName: PropertiesConfigDynamic 
* @Description: 动态加在配置文件类 
* @author 杨功平 852704764@qq.com 
* @date 2014年5月20日 下午4:51:39 
*
 */
public class PropertiesConfigDynamic {
	
	private static PropertiesConfiguration ConfigConstant;
	
	static {
        try {
			ConfigConstant = new PropertiesConfiguration("config/config.properties");
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			e.printStackTrace();
		}
		ConfigConstant.setReloadingStrategy(new FileChangedReloadingStrategy());
    }
	
	
	/**
	 * 
	* @Title: getConfig 
	* @Description: 根据key获取value内容 
	* @param key
	* @return String
	* @throws
	 */
	public static String getConfig(String key) {
		return ConfigConstant.getString(key);
	}
	
	/**
	 * 
	* @Title: getConfigs 
	* @Description: 根据key获取value内容数组 
	* @param key
	* @return String[]
	* @throws
	 */
	public static String[] getConfigs(String key) {
		return ConfigConstant.getStringArray(key);
	}
	
	/**
	 * 
	* @Title: setConfig 
	* @Description: 设置key-value内容 
	* @param key
	* @param value void
	* @throws
	 */
	public static void setConfig(String key,String value) {
		ConfigConstant.setProperty(key, value);
		try {
			ConfigConstant.save();
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
}
