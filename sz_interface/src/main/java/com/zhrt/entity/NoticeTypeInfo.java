package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * 简单描述: 通知类型实体类
 *
 * @author 王坤
 * @Create Date 2015年8月28 12点30分30秒
 */
public class NoticeTypeInfo {

	private String id;
	private String noticeTypeId;
	private String noticeTypeName;
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
	
	public String getNoticeTypeId() {
		return noticeTypeId;
	}
	public void setNoticeTypeId(String noticeTypeId) {
		this.noticeTypeId = noticeTypeId;
	}
	public String getNoticeTypeName() {
		return noticeTypeName;
	}
	public void setNoticeTypeName(String noticeTypeName) {
		this.noticeTypeName = noticeTypeName;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
