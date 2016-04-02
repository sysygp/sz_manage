package com.zhrt.entity;

import java.util.Date;

/**
 * 产品实体
 * 
 * @author WangKun 【  842306323@qq.com 】
 * @Created Date: 2015/08/26 10:25:08
 *
 */
public class AppInfo {

	private String id;
	private String cpId;
	private String cpCode;
	private String appName;
	private String appType;
	private String descript;
	private String status;
//	private String icon;
	private String appSort;
	private String createPerson;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	private String myepaySdkVer;
	private String myepayAppId;
	private String myepayChanAppId;
	private Integer propCount;
	private Integer appSeq;//产品序号
	private String wxAppId; //微信wxAppId
	private String wxAppSecret;//微信wxAppSecret
	private Integer appRankSeq;//产品和排行榜标识为
	private String talkingdataAppid;
	private String talkingdataKey;
	
	
	
	public Integer getAppRankSeq() {
		return appRankSeq;
	}
	public void setAppRankSeq(Integer appRankSeq) {
		this.appRankSeq = appRankSeq;
	}
	
	public String getTalkingdataAppid() {
		return talkingdataAppid;
	}
	public void setTalkingdataAppid(String talkingdataAppid) {
		this.talkingdataAppid = talkingdataAppid;
	}
	public String getTalkingdataKey() {
		return talkingdataKey;
	}
	public void setTalkingdataKey(String talkingdataKey) {
		this.talkingdataKey = talkingdataKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpCode() {
		return cpCode;
	}
	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
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
	public String getAppSort() {
		return appSort;
	}
	public void setAppSort(String appSort) {
		this.appSort = appSort;
	}
	public String getMyepaySdkVer() {
		return myepaySdkVer;
	}
	public void setMyepaySdkVer(String myepaySdkVer) {
		this.myepaySdkVer = myepaySdkVer;
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
	public Integer getPropCount() {
		return propCount;
	}
	public void setPropCount(Integer propCount) {
		this.propCount = propCount;
	}
	public Integer getAppSeq() {
		return appSeq;
	}
	public void setAppSeq(Integer appSeq) {
		this.appSeq = appSeq;
	}
	public String getWxAppId() {
		return wxAppId;
	}
	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}
	public String getWxAppSecret() {
		return wxAppSecret;
	}
	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}
	
}
