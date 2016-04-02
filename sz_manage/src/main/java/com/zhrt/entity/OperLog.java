package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * 平台操作日志实体类
 * @author ：
 * @vision : 1.0.0
 * @createDate : 2015年8月20日 下午5:10:31
 * @email  ：zhu_shz@myepay.cn
 */
public class OperLog {

	private String id;//主键
	private String bussName;//业务名称
	private String operType;//操作类型
	private String operDesc;//操作描述
	private String operUser;//操作人
	private String operRole;//操作角色
	private Date operTime;//操作时间
	private String operResult;//操作结果
	private String ip;//操作IP
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getOperDesc() {
		return operDesc;
	}
	public void setOperDesc(String operDesc) {
		this.operDesc = operDesc;
	}
	public String getOperUser() {
		return operUser;
	}
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}
	public String getOperRole() {
		return operRole;
	}
	public void setOperRole(String operRole) {
		this.operRole = operRole;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getOperResult() {
		return operResult;
	}
	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
