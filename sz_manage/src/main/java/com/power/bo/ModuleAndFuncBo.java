package com.power.bo;

import java.util.List;

import com.power.entity.PowerFunction;

public class ModuleAndFuncBo {

	private String moduleId;
	private String moduleName;
	private List<PowerFunction> functionList;
	
	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	
	public List<PowerFunction> getFunctionList() {
		return functionList;
	}
	public void setFunctionList(List<PowerFunction> functionList) {
		this.functionList = functionList;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	
}
