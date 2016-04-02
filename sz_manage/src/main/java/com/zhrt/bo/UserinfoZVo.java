package com.zhrt.bo;

import java.util.Date;

/**
 * 注册用户详细信息
 *
 */
public class UserinfoZVo {
	private String id;
	private String userid;
	private String accountid;
	private String platid;
	private String platname;
	private String userpwd;
	private String lastPhone;  //最后一次绑定的手机号
	private String lastProvince;
	private String lastProvinceName;
	private String lastNickName;
	private String lastPhoto;
	private String lastBirthday;
	private Integer lastSex;
	private String lastEmail;
	private Date lastLogTime;   //最后一次登录时间
	
	private String phone;       //注册时手机号
	private String province;
	private String provinceName;
	private String imei;
	private String imsi;
	private String mac;
	private Integer termType;   //终端ID
	private String version;     //版本号
	private String termModel;   //终端型号，手机型号
	private String termOs;		//终端操作系统
	private Double longitude;	//经度
	private Double latitude;	//纬度
	private Date createTime;    //注册时间
	
	
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
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
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
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getLastPhone() {
		return lastPhone;
	}
	public void setLastPhone(String lastPhone) {
		this.lastPhone = lastPhone;
	}
	public String getLastProvince() {
		return lastProvince;
	}
	public void setLastProvince(String lastProvince) {
		this.lastProvince = lastProvince;
	}
	public String getLastProvinceName() {
		return lastProvinceName;
	}
	public void setLastProvinceName(String lastProvinceName) {
		this.lastProvinceName = lastProvinceName;
	}
	public String getLastNickName() {
		return lastNickName;
	}
	public void setLastNickName(String lastNickName) {
		this.lastNickName = lastNickName;
	}
	public String getLastPhoto() {
		return lastPhoto;
	}
	public void setLastPhoto(String lastPhoto) {
		this.lastPhoto = lastPhoto;
	}
	public String getLastBirthday() {
		return lastBirthday;
	}
	public void setLastBirthday(String lastBirthday) {
		this.lastBirthday = lastBirthday;
	}
	public Integer getLastSex() {
		return lastSex;
	}
	public void setLastSex(Integer lastSex) {
		this.lastSex = lastSex;
	}
	public String getLastEmail() {
		return lastEmail;
	}
	public void setLastEmail(String lastEmail) {
		this.lastEmail = lastEmail;
	}
	public Date getLastLogTime() {
		return lastLogTime;
	}
	public void setLastLogTime(Date lastLogTime) {
		this.lastLogTime = lastLogTime;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
