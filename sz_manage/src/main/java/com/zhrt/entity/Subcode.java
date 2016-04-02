package com.zhrt.entity;

/** 
 * 手机号段
 */
public class Subcode {
	private Integer id;
	private String subcode;
	private String latinProvince;
	private String latinCity;
	private Integer latinId;
	private Integer userType;
	private Integer operator;	//运营商ID 1 移动 2 联通 3 电信
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubcode() {
		return subcode;
	}
	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}
	public String getLatinProvince() {
		return latinProvince;
	}
	public void setLatinProvince(String latinProvince) {
		this.latinProvince = latinProvince;
	}
	public String getLatinCity() {
		return latinCity;
	}
	public void setLatinCity(String latinCity) {
		this.latinCity = latinCity;
	}
	public Integer getLatinId() {
		return latinId;
	}
	public void setLatinId(Integer latinId) {
		this.latinId = latinId;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
}
