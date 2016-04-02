package com.zhrt.entity;

import java.util.Date;

public class Billingterminal {
	
	private String id;
	private String userUuid;//
	private String tel;//
	private String chanAppSeq;//
	private String channelId;//
	private String channelName;//
	
	private String spId;
	private String spCodeId;
	private String spCodeName;
	private Integer price;//计费价格
	private String cpId;
	private String cpName;
	private String serviceId;
	private String serviceName;
	private String propId;
	
	private Date cTime;//流水存库时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getChanAppSeq() {
		return chanAppSeq;
	}

	public void setChanAppSeq(String chanAppSeq) {
		this.chanAppSeq = chanAppSeq;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpCodeId() {
		return spCodeId;
	}

	public void setSpCodeId(String spCodeId) {
		this.spCodeId = spCodeId;
	}

	public String getSpCodeName() {
		return spCodeName;
	}

	public void setSpCodeName(String spCodeName) {
		this.spCodeName = spCodeName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public Date getcTime() {
		return cTime;
	}

	public void setcTime(Date cTime) {
		this.cTime = cTime;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	
}
