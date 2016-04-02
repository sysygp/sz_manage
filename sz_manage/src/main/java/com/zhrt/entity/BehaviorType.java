package com.zhrt.entity;

import java.util.Date;

public class BehaviorType {
	
	private String id;
	private String behavId;
	private String behavName;
	private String remark;
	private String cuser;
	private Date ctime;
	private String uuser;
	private Date utime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBehavId() {
		return behavId;
	}
	public void setBehavId(String behavId) {
		this.behavId = behavId;
	}
	public String getBehavName() {
		return behavName;
	}
	public void setBehavName(String behavName) {
		this.behavName = behavName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCuser() {
		return cuser;
	}
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getUuser() {
		return uuser;
	}
	public void setUuser(String uuser) {
		this.uuser = uuser;
	}
	public Date getUtime() {
		return utime;
	}
	public void setUtime(Date utime) {
		this.utime = utime;
	}
}
