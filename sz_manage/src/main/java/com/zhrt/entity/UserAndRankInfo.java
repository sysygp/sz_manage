package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * 简单描述: 用户排行版中间表实体类
 *
 * @author 王坤
 * @Create Date 2015年9月1 10点30分30秒
 */
public class UserAndRankInfo {

	private String id ;         //主键                                                     
	private String userId ;   //用户id                                                                                                                                                   
	private String rankCode ; //排行榜标识符                                                                                      
	private Integer rankValue ;  //排行榜数值，即在不同类型下的排行榜数值  
	private Date createTime ;  //创建时间                                               
	private Date updateTime;  //更新时间    	
	private Double longitude;  // 经度                                       
	private Double latitude ;  // 纬度 
	private String appId ;  // 游戏id

	
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getRankValue() {
		return rankValue;
	}
	public void setRankValue(Integer rankValue) {
		this.rankValue = rankValue;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRankCode() {
		return rankCode;
	}
	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}		
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
