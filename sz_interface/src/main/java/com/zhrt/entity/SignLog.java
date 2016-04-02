package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * 签到记录
 * @author ：朱士竹
 * @vision : 1.0.0
 * @createDate : 2015年9月6日 上午11:56:06
 * @email  ：zhu_shz@myepay.cn
 */
public class SignLog {

	private String id;
	private String appId;
	private String appVerId;
	private String userId;
	private Date signDate;
	private String channelId;
	private String fillSignFlag;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppVerId() {
		return appVerId;
	}
	public void setAppVerId(String appVerId) {
		this.appVerId = appVerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getFillSignFlag() {
		return fillSignFlag;
	}
	public void setFillSignFlag(String fillSignFlag) {
		this.fillSignFlag = fillSignFlag;
	}
	
}
