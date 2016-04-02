package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * @Description:道具信息实体类
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月28日 上午10:54:02
 */
public class PropInfo {

	private String id;
	private String appId;
	private String platId;
	private String propName;
	private String propFee;
	private String propType;
	private String payType;
	private String feeTipMode;
	private String extraMessage;
	private Integer virtualCurrency;
	private Integer propCode;
	private String status;
	private String spcodeIds;
	private String createPerson;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getPropCode() {
		return propCode;
	}
	public void setPropCode(Integer propCode) {
		this.propCode = propCode;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public String getPropFee() {
		return propFee;
	}
	public void setPropFee(String propFee) {
		this.propFee = propFee;
	}
	public String getPropType() {
		return propType;
	}
	public void setPropType(String propType) {
		this.propType = propType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getFeeTipMode() {
		return feeTipMode;
	}
	public void setFeeTipMode(String feeTipMode) {
		this.feeTipMode = feeTipMode;
	}
	public String getExtraMessage() {
		return extraMessage;
	}
	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdatePerson() {
		return lastUpdatePerson;
	}
	public void setLastUpdatePerson(String lastUpdatePerson) {
		this.lastUpdatePerson = lastUpdatePerson;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getPlatId() {
		return platId;
	}
	public void setPlatId(String platId) {
		this.platId = platId;
	}
	public Integer getVirtualCurrency() {
		return virtualCurrency;
	}
	public void setVirtualCurrency(Integer virtualCurrency) {
		this.virtualCurrency = virtualCurrency;
	}
	public String getSpcodeIds() {
		return spcodeIds;
	}
	public void setSpcodeIds(String spcodeIds) {
		this.spcodeIds = spcodeIds;
	}
	
}
