package com.zhrt.entity;

import java.util.Date;

/**
 * 注册用户数
 * @author Administrator
 *
 */
public class UserRegist {
	
	private String id;
	private String userRegistDate;
	private String channelId;
	private int userCount;
	private int channelUserCount;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserRegistDate() {
		return userRegistDate;
	}
	public void setUserRegistDate(String userRegistDate) {
		this.userRegistDate = userRegistDate;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getChannelUserCount() {
		return channelUserCount;
	}
	public void setChannelUserCount(int channelUserCount) {
		this.channelUserCount = channelUserCount;
	}
	

	
}
