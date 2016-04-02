package com.zhrt.entity;

import java.util.Date;
/**
 * 用户行为
 *
 */
public class Behavior {
	private String id;
	private String userid;
	private Integer userType;	//用户类型   1：注册用户 2：临时用户
	private String phone;
	private String province;
	private String provinceName;
	private String accountid;
	private String behavId;
	private String behavName;
	private String platid;
	private String platname;
	private String imei;
	private String imsi;
	private String mac;
	private Integer termType;	//终端类型 1:手机，2:平板, -1:未知
	private String termModel;	//终端型号 手机型号或平板型号
	private String version;
	private String longitude;
	private String latitude;
	private Date mobileTime;
	private Date createTime;
	private String sdkVer;
	private Integer chanAppVerSeq;
	private String cpId;
	private String appId;
	private String verId;
	private String channelId;
	
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
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getBehavId() {
		return behavId;
	}
	public void setBehavId(String behavId) {
		this.behavId = behavId;
	}
	public String getBehavName() {
		return behavName;
	}
	public void setBehavName(String behavName) {
		this.behavName = behavName;
	}
	public String getPlatid() {
		return platid;
	}
	public void setPlatid(String platid) {
		this.platid = platid;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getMobileTime() {
		return mobileTime;
	}
	public void setMobileTime(Date mobileTime) {
		this.mobileTime = mobileTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getSdkVer() {
		return sdkVer;
	}
	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}
	public Integer getChanAppVerSeq() {
		return chanAppVerSeq;
	}
	public void setChanAppVerSeq(Integer chanAppVerSeq) {
		this.chanAppVerSeq = chanAppVerSeq;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getVerId() {
		return verId;
	}
	public void setVerId(String verId) {
		this.verId = verId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}
