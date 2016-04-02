package com.zhrt.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * sdk版本实体
 * @author ：朱士竹
 * @vision : 1.0.0
 * @createDate : 2015年8月6日 下午6:46:47
 * @email  ：zhu_shz@myepay.cn
 */
public class SdkVerInfo {
	
	private String id;
	private String sdkVerCode;//sdk版本号
	private String updateFile;
	private MultipartFile file;
	private String description;
	private String isUpdate;
	private String status;
	private String remark;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	public String getSdkVerCode() {
		return sdkVerCode;
	}
	public void setSdkVerCode(String sdkVerCode) {
		this.sdkVerCode = sdkVerCode;
	}
	public String getUpdateFile() {
		return updateFile;
	}
	public void setUpdateFile(String updateFile) {
		this.updateFile = updateFile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
