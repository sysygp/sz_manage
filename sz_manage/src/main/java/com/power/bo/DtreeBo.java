package com.power.bo;

import java.util.List;

/** 
 * 角色权限信息
 * 
 */
public class DtreeBo {
	private String dtree;  //权限树
	private Integer platId;
	private List<String> hidePowerFunIds;  //隐藏的权限列表
	
	public String getDtree() {
		return dtree;
	}
	public void setDtree(String dtree) {
		this.dtree = dtree;
	}
	public List<String> getHidePowerFunIds() {
		return hidePowerFunIds;
	}
	public void setHidePowerFunIds(List<String> hidePowerFunIds) {
		this.hidePowerFunIds = hidePowerFunIds;
	}
	public Integer getPlatId() {
		return platId;
	}
	public void setPlatId(Integer platId) {
		this.platId = platId;
	}
}
