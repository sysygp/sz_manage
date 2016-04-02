package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * 消息类型实体类
 * @author ：
 * @vision : 1.0.0
 * @createDate : 2015年8月6日 下午6:49:41
 * @email  ：zhu_shz@myepay.cn
 */
public class MsgTypeInfo {

	private String id;
	private String msgTypeId;
	private String msgTypeName;
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
	public String getMsgTypeId() {
		return msgTypeId;
	}
	public void setMsgTypeId(String msgTypeId) {
		this.msgTypeId = msgTypeId;
	}
	public String getMsgTypeName() {
		return msgTypeName;
	}
	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
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
