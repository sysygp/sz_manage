package com.zhrt.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
/**
 * 
 * 数据文件实体类
 * @author ：朱士竹
 * @vision : 1.0.0
 * @createDate : 2015年8月14日 上午11:06:18
 * @email  ：zhu_shz@myepay.cn
 */
public class DataPackInfo {

	private String id;
	private String appId;//产品id
	private String dataFileId;//数据文件Id
	private String dataFile;//数据文件
	private String status;//状态
	private String supportVersion;//支持的版本
	private String remark;//备注
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private MultipartFile file;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getDataFile() {
		return dataFile;
	}
	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSupportVersion() {
		return supportVersion;
	}
	public void setSupportVersion(String supportVersion) {
		this.supportVersion = supportVersion;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
	public String getDataFileId() {
		return dataFileId;
	}
	public void setDataFileId(String dataFileId) {
		this.dataFileId = dataFileId;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
