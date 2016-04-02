package com.zhrt.entity;

/**
 * 
 * 简单描述: 无
 *
 * @author 王坤
 * @Create Date 2015年9月12日   
 *
 * @UpdateAuthor 
 * @Update Date 2015年8月27日 
 *
 */
public class ChargeCode {
	private String id;
	private String feeCode;
	private String feeNumber;
	private Integer times;
	private String response;
	private Integer responseType;
	private String filter;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Integer getResponseType() {
		return responseType;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
}
