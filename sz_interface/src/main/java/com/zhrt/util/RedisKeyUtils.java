package com.zhrt.util;

import java.util.Date;

public class RedisKeyUtils {
	
	/**
	 * 获取日限制的key,如 20150104
	 * @author Yang_gp
	 * @version 1.3
	 * @since 1.3
	 * @return
	 */
	public static String getDayLimitKey(){
		return DateUtil.DF_YMD_TRIM.format(new Date());
	}
	
	
	/**
	 * 获取月限制的key,如201501
	 * @author Yang_gp
	 * @version 1.3
	 * @since 1.3
	 * @return
	 */
	public static String getMonthLimitKey(){
		return DateUtil.DF_YM_TRIM.format(new Date());
	}

	/**
	 * 获取用户单代码限制的field
	 * @param spCodeId
	 * @param userUuid
	 * @return
	 */
	public static String getSpcodeLimitField(String spCodeId,String userUuid){
		return spCodeId+"_"+userUuid;
	}
	
	/**
	 * 获取用户限制的field
	 * @param userUuid
	 * @return
	 */
	public static String getUserLimitField(String userUuid){
		return "allCode"+"_"+userUuid;
	}
	

}
