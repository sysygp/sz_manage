package com.zhrt.entity;

import java.util.Date;

public class Plat {
	private String id;
	private Integer platid;
	private String platname;
	private String platkey;
	private String channelId;
	private String cnName;
	private String cpId;
	private String cpName;
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
	public Integer getPlatid() {
		return platid;
	}
	public void setPlatid(Integer platid) {
		this.platid = platid;
	}
	public String getPlatname() {
		return platname;
	}
	public void setPlatname(String platname) {
		this.platname = platname;
	}
	public String getPlatkey() {
		return platkey;
	}
	public void setPlatkey(String platkey) {
		this.platkey = platkey;
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
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getCpName() {
		return cpName;
	}
	public void setCpName(String cpName) {
		this.cpName = cpName;
	}
}
