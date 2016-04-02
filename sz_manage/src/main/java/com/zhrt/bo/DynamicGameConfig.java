package com.zhrt.bo;

import com.zhrt.entity.SmsCenter;
import com.zhrt.entity.UrlCenter;

/**
 * 
 * @Description:动态配置文件来源实体
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月24日 下午4:34:20
 */
public class DynamicGameConfig {

	private SmsCenter smsCenter;
	private UrlCenter urlCenter;
	
	
	
	public UrlCenter getUrlCenter() {
		return urlCenter;
	}
	public void setUrlCenter(UrlCenter urlCenter) {
		this.urlCenter = urlCenter;
	}
	public SmsCenter getSmsCenter() {
		return smsCenter;
	}
	public void setSmsCenter(SmsCenter smsCenter) {
		this.smsCenter = smsCenter;
	}
	
}
