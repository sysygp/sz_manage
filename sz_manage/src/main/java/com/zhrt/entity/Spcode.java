package com.zhrt.entity;

import java.util.Date;

public class Spcode {

	private String id;
	private String spId;
	private String name;
	private String operatorType;//运营商类型
	private String chargeProvince;//允许计费省份
	private String chargeTimeBegin;//允许计费时间开始点
	private String chargeTimeEnd;//允许计费时间结束点
	private String createPerson;
	private Integer status;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	private Integer limitDayNum;//日限制条数
	private Integer limitDayMoney;//日限制金额
	private Integer limitMonthNum;//月限制条数
	private Integer limitMonthMoney;//月限制金额
	
	private String feeStatus;//0：注册  1：短信计费  2：wap计费 3：sdk计费
	private String feeCode;//计费代码
	private String feeNumber;//短信注册端口号或计费端口号
	private String chargeMoney;//计费金额
	
	//拦截关键字，如果有多条下行短信（包括确认购买短信和扣费提醒短信），则将每条短信中挑选一个拦截关键字并以#分割
	//比如1条短信：“...谢谢购买《捕鱼》,资费10元...”，则为“谢谢购买《捕鱼》”
	//比如2条短信：“...即将购买《捕鱼》...”，“...谢谢购买《捕鱼》,资费10元...”， 则为“即将购买《捕鱼》#谢谢购买《捕鱼》”
	//比如3条短信：“...即将购买《捕鱼》...”，“...回复1234确认...”，“...谢谢购买《捕鱼》,资费10元...”，则为“即将购买《捕鱼》#确认#谢谢购买《捕鱼》”
	private String interceptWord;
	
	private String replyType;//回复类型  0：不需要回复 1:需要回复
	
	//回复内容 如果replay为0：则不写； 如果replay为1：需要回复短信中的拦截关键字+“，”+验证码前文字+“#”+验证码后文字
	/**
	 * 当replyType为1时范例：假如包含回复内容的短信为：
	 * “即将购买《捕鱼》，回复是确认”，设置的拦截关键字为“即将购买《捕鱼》”，那么填写“即将购买《捕鱼》，回复#确认”
	 * “即将购买《捕鱼》，回复是”，设置的拦截关键字为“即将购买《捕鱼》”，那么填写“即将购买《捕鱼》，回复#”
	 * “是确认”，设置的拦截关键字为“确认”，那么填写“确认，#确认”
	 */
	private String replyContent;
	private String chargeTimes;//执行次数
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getFeeNumber() {
		return feeNumber;
	}
	public void setFeeNumber(String feeNumber) {
		this.feeNumber = feeNumber;
	}
	
	public String getInterceptWord() {
		return interceptWord;
	}
	public void setInterceptWord(String interceptWord) {
		this.interceptWord = interceptWord;
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
	public String getChargeProvince() {
		return chargeProvince;
	}
	public void setChargeProvince(String chargeProvince) {
		this.chargeProvince = chargeProvince;
	}
	public String getChargeTimeBegin() {
		return chargeTimeBegin;
	}
	public void setChargeTimeBegin(String chargeTimeBegin) {
		this.chargeTimeBegin = chargeTimeBegin;
	}
	public String getChargeTimeEnd() {
		return chargeTimeEnd;
	}
	public void setChargeTimeEnd(String chargeTimeEnd) {
		this.chargeTimeEnd = chargeTimeEnd;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getReplyType() {
		return replyType;
	}
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
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
	public String getChargeMoney() {
		return chargeMoney;
	}
	public void setChargeMoney(String chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getChargeTimes() {
		return chargeTimes;
	}
	public void setChargeTimes(String chargeTimes) {
		this.chargeTimes = chargeTimes;
	}
	
	
}
