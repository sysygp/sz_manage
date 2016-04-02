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
public class SmsCenter {

	private String cmccPort="1065712059555387";//移动端口号
	private String cuccPort="10690335555387";//联通端口号
	private String ctccPort="1065902020716555387";//电信端口号
	
	
	public String getCmccPort() {
		return cmccPort;
	}
	public void setCmccPort(String cmccPort) {
		this.cmccPort = cmccPort;
	}
	public String getCuccPort() {
		return cuccPort;
	}
	public void setCuccPort(String cuccPort) {
		this.cuccPort = cuccPort;
	}
	public String getCtccPort() {
		return ctccPort;
	}
	public void setCtccPort(String ctccPort) {
		this.ctccPort = ctccPort;
	}
}
