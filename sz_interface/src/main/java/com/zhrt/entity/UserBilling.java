package com.zhrt.entity;

import java.util.Date;

/**
 * 用户计费流水
 * @author 朱士竹
 * @createDate 2015年7月20日
 * @vision 1.0
 */
public class UserBilling {

	private String id;
	private String userId;
	private String platId;
	private String channelId;
	private String extChannelId;
	private String cpId;
	private String extCpId;
	private String appId;
	private String extAppId;
	private String chanAppId;
	private String extChanAppId;
	private String propId;
	private String extPropId;
	private Integer chanAppVerSeq;
	private String verId;
	private String phone;
	private Integer province;
	private Integer city;
	private String serialNumber;
	private String sdkVer;
	private Double money;
	private String indexId;
	private String result;
	private String resultCode;
	private Date chargeTime;
	private Date createTime;
	private Date updateTime;
	private Integer paymentType;
	
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
	public String getPlatId() {
		return platId;
	}
	public void setPlatId(String platId) {
		this.platId = platId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getExtChannelId() {
		return extChannelId;
	}
	public void setExtChannelId(String extChannelId) {
		this.extChannelId = extChannelId;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getExtCpId() {
		return extCpId;
	}
	public void setExtCpId(String extCpId) {
		this.extCpId = extCpId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getExtAppId() {
		return extAppId;
	}
	public void setExtAppId(String extAppId) {
		this.extAppId = extAppId;
	}
	public String getChanAppId() {
		return chanAppId;
	}
	public void setChanAppId(String chanAppId) {
		this.chanAppId = chanAppId;
	}
	public String getExtChanAppId() {
		return extChanAppId;
	}
	public void setExtChanAppId(String extChanAppId) {
		this.extChanAppId = extChanAppId;
	}
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	public String getExtPropId() {
		return extPropId;
	}
	public void setExtPropId(String extPropId) {
		this.extPropId = extPropId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSdkVer() {
		return sdkVer;
	}
	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getIndexId() {
		return indexId;
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getVerId() {
		return verId;
	}
	public void setVerId(String verId) {
		this.verId = verId;
	}
	public Integer getChanAppVerSeq() {
		return chanAppVerSeq;
	}
	public void setChanAppVerSeq(Integer chanAppVerSeq) {
		this.chanAppVerSeq = chanAppVerSeq;
	}
	public Integer getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
}
