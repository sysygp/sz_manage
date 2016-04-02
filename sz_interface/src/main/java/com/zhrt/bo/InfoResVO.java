package com.zhrt.bo;


public class InfoResVO{

	private String id;
	private String feeStatus;//0：注册  1：短信计费  4：wap计费 5：sdk计费
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
	private String tel;
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
	public String getChargeMoney() {
		return chargeMoney;
	}
	public void setChargeMoney(String chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	public String getInterceptWord() {
		return interceptWord;
	}
	public void setInterceptWord(String interceptWord) {
		this.interceptWord = interceptWord;
	}
	public String getReplyType() {
		return replyType;
	}
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getChargeTimes() {
		return chargeTimes;
	}
	public void setChargeTimes(String chargeTimes) {
		this.chargeTimes = chargeTimes;
	}
	
	
}
