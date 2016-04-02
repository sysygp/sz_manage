package com.zhrt.bo;

/**
 * 修改支付密码的请求报文
 *
 */
public class ModifyPayPwdReqVO extends ReqBaseVO{
	private String phone;
	private String function;
	
	private String payPwdOri;		//原支付密码
	private String payPwdNew;		//新支付密码
	private String confirmpwdNew;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPayPwdOri() {
		return payPwdOri;
	}
	public void setPayPwdOri(String payPwdOri) {
		this.payPwdOri = payPwdOri;
	}
	public String getPayPwdNew() {
		return payPwdNew;
	}
	public void setPayPwdNew(String payPwdNew) {
		this.payPwdNew = payPwdNew;
	}
	public String getConfirmpwdNew() {
		return confirmpwdNew;
	}
	public void setConfirmpwdNew(String confirmpwdNew) {
		this.confirmpwdNew = confirmpwdNew;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	
}
