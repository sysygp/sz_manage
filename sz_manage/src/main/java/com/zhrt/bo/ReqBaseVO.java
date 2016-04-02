package com.zhrt.bo;

/**
 * 
 * @Description:请求报文基类
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月29日 下午6:26:47
 */
public class ReqBaseVO {
	
	private String userid;//用户id
	private String platid;//平台id
	private String chanAppVerSeq;//渠道产品版本序号
	private String token;//token
	
	
	public String getPlatid() {
		return platid;
	}
	public void setPlatid(String platid) {
		this.platid = platid;
	}
	public String getChanAppVerSeq() {
		return chanAppVerSeq;
	}
	public void setChanAppVerSeq(String chanAppVerSeq) {
		this.chanAppVerSeq = chanAppVerSeq;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
