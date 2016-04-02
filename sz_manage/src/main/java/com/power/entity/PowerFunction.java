package com.power.entity;

/**
 * 
 * @Description:功能
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月6日 下午3:25:01
 */
public class PowerFunction {

	private String id;
	private String moduleId;
	private String functionName;
	private String functionUrl;
	private Integer iscomFun;
	private Integer status;
	
	
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getFunctionUrl() {
		return functionUrl;
	}
	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public Integer getIscomFun() {
		return iscomFun;
	}
	public void setIscomFun(Integer iscomFun) {
		this.iscomFun = iscomFun;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}