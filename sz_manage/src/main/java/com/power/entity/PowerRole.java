package com.power.entity;


/**
 * 
 * @Description:角色信息
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月6日 下午1:54:57
 */
public class PowerRole {

	private String id;
	private String name;
	private String domainId;
	private Integer platId;
	private Integer status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPlatId() {
		return platId;
	}
	public void setPlatId(Integer platId) {
		this.platId = platId;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	
	
}
