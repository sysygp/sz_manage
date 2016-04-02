package com.power.util;

import java.util.List;

import com.power.entity.PowerFunction;
import com.zhrt.util.Constant;

public class DtreeUtil {

	/**
	 * 获取树目录字符串
	 * @param displayModuleList  需要显示在树目录中的模块
	 * @param displayFunList  需要显示在树目录中的功能
	 * @param selectedFunidList  需要显示在树目录中的被默认选中的功能id
	 * @return
	 */
	public static String getDtreeStr(List<String> displayModuleidList , List<PowerFunction> displayFunList,
			List<String> selectedFunidList){
		
		StringBuffer dtree = new StringBuffer("");
		
		dtree.append("var mytree = new dTree('mytree');\n");
		dtree.append("mytree.add('0','-1','权限','','','','','打开或关闭菜单','','','',false);\n");
		
		for(String moduleId : displayModuleidList){
			dtree.append("mytree.add('" + moduleId + "','0','" + Constant.moduleMap.get(moduleId) + "','module','" + moduleId + "',0);\n");
		}
		
		for(PowerFunction powerFun : displayFunList){
			dtree.append("mytree.add('" + powerFun.getId() + "','" + powerFun.getModuleId() + "','"+ Constant.funcMap.get(powerFun.getId()) + "','powerCode','" + powerFun.getId() + "'," + (selectedFunidList.contains(powerFun.getId())?1:0) + ",'','');\n");
		}
		
		return dtree.toString();
	}
	
	
}
