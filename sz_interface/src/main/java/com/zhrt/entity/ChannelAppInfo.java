package com.zhrt.entity;

import java.util.Date;

/**
 * 渠道应用信息
 * @author 朱士竹
 * @createDate 2015年7月15日
 * @vision 1.0
 */
public class ChannelAppInfo {

	private String id;
	private String platId;
	
	private Integer chanAppVerSeq;
	private String channelId;
	private String cpId;
	private String appId;
	private String appName;
	private String appType;
	private String verId;
	private String verNumber;
	private String sdkVer;
	private String status;
	private String description;
	private String createPerson;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	private String mainFlag;
	private String fileName;
	private String fileType;
	private String fileLen;
	private String downUrl;
	private String myepaySdkVer;
	private String myepayAppId;
	private String myepayChanAppId;
	private String myepayChannelId;
	private String myepayChannelCode;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlatId() {
		return platId;
	}
	public void setPlatId(String platId) {
		this.platId = platId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getSdkVer() {
		return sdkVer;
	}
	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getMainFlag() {
		return mainFlag;
	}
	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
	public String getVerId() {
		return verId;
	}
	public void setVerId(String verId) {
		this.verId = verId;
	}
	public String getVerNumber() {
		return verNumber;
	}
	public void setVerNumber(String verNumber) {
		this.verNumber = verNumber;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileLen() {
		return fileLen;
	}
	public void setFileLen(String fileLen) {
		this.fileLen = fileLen;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public void setMyepaySdkVer(String myepaySdkVer) {
		this.myepaySdkVer = myepaySdkVer;
	}
	public Integer getChanAppVerSeq() {
		return chanAppVerSeq;
	}
	public void setChanAppVerSeq(Integer chanAppVerSeq) {
		this.chanAppVerSeq = chanAppVerSeq;
	}
	public String getMyepayAppId() {
		return myepayAppId;
	}
	public void setMyepayAppId(String myepayAppId) {
		this.myepayAppId = myepayAppId;
	}
	public String getMyepayChanAppId() {
		return myepayChanAppId;
	}
	public void setMyepayChanAppId(String myepayChanAppId) {
		this.myepayChanAppId = myepayChanAppId;
	}
	public String getMyepayChannelId() {
		return myepayChannelId;
	}
	public void setMyepayChannelId(String myepayChannelId) {
		this.myepayChannelId = myepayChannelId;
	}
	public String getMyepayChannelCode() {
		return myepayChannelCode;
	}
	public void setMyepayChannelCode(String myepayChannelCode) {
		this.myepayChannelCode = myepayChannelCode;
	}
	public String getMyepaySdkVer() {
		return myepaySdkVer;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}
