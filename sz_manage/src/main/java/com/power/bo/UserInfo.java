package com.power.bo;


public class UserInfo {
	private Integer userId=-1;
	private String loginId;
	private String name;
	private Integer domainId=-1;
	private String domainName;
	private Integer companyId=-1;
    private Integer domainType=-1;
    private String domainTypeName;
    private Integer imUserId=0;
    private String remoteIp;
    private String userType;
    private String chargePartnerId;
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer compId) {
		this.companyId = compId;
	}
	public Integer getDomainId() {
		return domainId;
	}
	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDomainType() {
		return domainType;
	}
	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainTypeName() {
		return domainTypeName;
	}
	public void setDomainTypeName(String domainTypeName) {
		this.domainTypeName = domainTypeName;
	}
	public Integer getImUserId() {
		return imUserId;
	}
	public void setImUserId(Integer imUserId) {
		this.imUserId = imUserId;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getChargePartnerId() {
		return chargePartnerId;
	}
	public void setChargePartnerId(String chargePartnerId) {
		this.chargePartnerId = chargePartnerId;
	}
	
}
