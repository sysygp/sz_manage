package com.zhrt.bo;

import java.util.Date;

/**
 * 非注册用户详细信息
 *
 */
public class UserinfoNVo {
	private String id;
	private String userid;
	private String version;   //版本号
	private String platid;
	private String platname;
	private String imei;
	private String imsi;
	private String mac;
	private Integer termType;  //终端ID
	private String termModel;  //终端型号，手机型号
	private String termOs;		//终端操作系统
	private Double longitude;	//经度
	private Double latitude;	//纬度
	private String nickname;
	private Date createTime;
	private Date lastLogTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPlatname() {
		return platname;
	}
	public void setPlatname(String platname) {
		this.platname = platname;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public Integer getTermType() {
		return termType;
	}
	public void setTermType(Integer termType) {
		this.termType = termType;
	}
	public String getTermModel() {
		return termModel;
	}
	public void setTermModel(String termModel) {
		this.termModel = termModel;
	}
	public String getTermOs() {
		return termOs;
	}
	public void setTermOs(String termOs) {
		this.termOs = termOs;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastLogTime() {
		return lastLogTime;
	}
	public void setLastLogTime(Date lastLogTime) {
		this.lastLogTime = lastLogTime;
	}
	public String getPlatid() {
		return platid;
	}
	public void setPlatid(String platid) {
		this.platid = platid;
	}
}
