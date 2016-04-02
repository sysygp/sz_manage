package com.zhrt.entity;

/**
 * 
 * @Description:短信中心端口号实体
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月24日 下午4:30:36
 */
public class UrlCenter {

	private String logUrl="http://xxx:port/xxx";//日志请求路径

	public String getLogUrl() {
		return logUrl;
	}

	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}
}
