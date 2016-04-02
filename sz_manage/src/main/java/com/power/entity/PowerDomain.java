package com.power.entity;

/**
 * 
 * @Description:域管理模块
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月16日 下午6:56:57
 */
public class PowerDomain{
	private String id;
	private String name;
	private String parentId;
	private Integer platId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPlatId() {
		return platId;
	}
	public void setPlatId(Integer platId) {
		this.platId = platId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}