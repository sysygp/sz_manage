package com.zhrt.entity;

import java.util.Date;

/**
 * 用户 日月限制
 * @author Administrator
 *
 */
public class UserLimit {

	private String id;
	private String spcodeId;
	private Integer limitDayNum;//日限制条数
	private Integer limitDayMoney;//日限制金额
	private Integer limitMonthNum;//月限制条数
	private Integer limitMonthMoney;//月限制金额
	private String createPerson;
	private Integer status;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpcodeId() {
		return spcodeId;
	}
	public void setSpcodeId(String spcodeId) {
		this.spcodeId = spcodeId;
	}
	public Integer getLimitDayNum() {
		return limitDayNum;
	}
	public void setLimitDayNum(Integer limitDayNum) {
		this.limitDayNum = limitDayNum;
	}
	public Integer getLimitDayMoney() {
		return limitDayMoney;
	}
	public void setLimitDayMoney(Integer limitDayMoney) {
		this.limitDayMoney = limitDayMoney;
	}
	public Integer getLimitMonthNum() {
		return limitMonthNum;
	}
	public void setLimitMonthNum(Integer limitMonthNum) {
		this.limitMonthNum = limitMonthNum;
	}
	public Integer getLimitMonthMoney() {
		return limitMonthMoney;
	}
	public void setLimitMonthMoney(Integer limitMonthMoney) {
		this.limitMonthMoney = limitMonthMoney;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	
	
}
