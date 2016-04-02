package com.zhrt.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @Description:产品版本实体类
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月27日 下午7:48:55
 */
public class AppVersionInfo {

	private String id;
	private String appId;
	private String myepayAppId;
	private String verNumber;
	private String verName;
	private String verPackName;
	private String verPackFileType;
	private String verPackFileLength;
	private String verDownUrl;
	private String sdkVer;
	private String descript;
	private String status;
	private String verSource;
	private String createPerson;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	private MultipartFile file;
	private String icon;
	/**
	 * 添加渠道运营产品时列表页显示的字段
	 */
	private String appName;
	private String appTypeName;
	private String cpName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVerNumber() {
		return verNumber;
	}
	public void setVerNumber(String verNumber) {
		this.verNumber = verNumber;
	}
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}
	public String getVerPackName() {
		return verPackName;
	}
	public void setVerPackName(String verPackName) {
		this.verPackName = verPackName;
	}
	public String getVerPackFileType() {
		return verPackFileType;
	}
	public void setVerPackFileType(String verPackFileType) {
		this.verPackFileType = verPackFileType;
	}
	public String getVerPackFileLength() {
		return verPackFileLength;
	}
	public void setVerPackFileLength(String verPackFileLength) {
		this.verPackFileLength = verPackFileLength;
	}
	public String getVerDownUrl() {
		return verDownUrl;
	}
	public void setVerDownUrl(String verDownUrl) {
		this.verDownUrl = verDownUrl;
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
	public String getVerSource() {
		return verSource;
	}
	public void setVerSource(String verSource) {
		this.verSource = verSource;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getMyepayAppId() {
		return myepayAppId;
	}
	public void setMyepayAppId(String myepayAppId) {
		this.myepayAppId = myepayAppId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSdkVer() {
		return sdkVer;
	}
	public void setSdkVer(String sdkVer) {
		this.sdkVer = sdkVer;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppTypeName() {
		return appTypeName;
	}
	public void setAppTypeName(String appTypeName) {
		this.appTypeName = appTypeName;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
