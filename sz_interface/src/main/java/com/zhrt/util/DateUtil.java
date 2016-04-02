package com.zhrt.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {
	
	public static DateFormat DF_YMD= new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat DF_YM= new SimpleDateFormat("yyyy-MM");
	public static DateFormat DF_YMD_TRIM= new SimpleDateFormat("yyyyMMdd");
	public static DateFormat DF_YM_TRIM= new SimpleDateFormat("yyyyMM");

	/**
	 * 获取下一天凌晨时间,如2011年2月8号0时0分0秒
	 * @author Yang_gp
	 * @version 1.3
	 * @since 1.3
	 * @return
	 */
	public static long getNextDayBegin(){
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.add(Calendar.DATE, 1);
		
		gCalendar.set(Calendar.HOUR_OF_DAY , 0);
		gCalendar.set(Calendar.MINUTE , 0);
		gCalendar.set(Calendar.SECOND , 0);
		return gCalendar.getTimeInMillis();
	}
	/**
	 * 获取下一个月第一天凌晨时间,
	 * @author Yang_gp
	 * @version 1.3
	 * @since 1.3
	 * @return
	 */
	public static long getNextMonthBegin(){
		GregorianCalendar gCalendar = new GregorianCalendar();
		gCalendar.add(Calendar.MONTH, 1);
		
		gCalendar.set(Calendar.DATE , 1);
		gCalendar.set(Calendar.HOUR_OF_DAY , 0);
		gCalendar.set(Calendar.MINUTE , 0);
		gCalendar.set(Calendar.SECOND , 0);
		return gCalendar.getTimeInMillis();
	}
	
}
