package com.power.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.power.bo.DtreeBo;
import com.power.entity.PowerFunction;
import com.power.entity.PowerModule;
import com.power.entity.PowerRole;
import com.power.entity.PowerRoleFun;
import com.power.entity.PowerUser;
import com.power.entity.PowerUserFun;
import com.power.entity.PowerUserRole;
import com.system.util.stringutil.StrUtils;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerinfoService {
	private static Logger logger = LoggerFactory.getLogger(PowerinfoService.class);

	@Autowired
	private PowerModuleService powerModuleService;
	@Autowired
	private PowerFunctionService powerFunctionService;
	@Autowired
	private PowerRoleFunService powerRoleFunService;
	@Autowired
	private PowerUserFunService powerUserFunService;
	@Autowired
	private PowerRoleService powerRoleService;
	@Autowired
	private PowerUserRoleService powerUserRoleService;
	
	
	
	/**
	 * 添加角色页面获取权限树
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DtreeBo getFunctionTreeAdd(String roleId, String platid, PowerUser loginUser){
		DtreeBo powerRoleFunBo = new DtreeBo();
		
		try {
			StringBuffer dtree = new StringBuffer("");
			dtree.append("var mytree = new dTree('mytree');\n");
			dtree.append("mytree.add(0,-1,'权限','','','','','打开或关闭菜单','','','',false);\n");
			
			if(platid.equals("0")){
				//所有权限列表 所有模块
				List<PowerModule> powerModuleList = powerModuleService.getList();
				List<PowerFunction> funList = powerFunctionService.getList();
				
				for(PowerModule powerModule : powerModuleList){
					dtree.append("mytree.add(" + powerModule.getModuleId() + ",0,'" + powerModule.getModuleName() + "','module','" + powerModule.getModuleId() + "',0);\n");
				}
				
				int checkVal = 0;
				for(PowerFunction powerFun : funList){
					dtree.append("mytree.add(" + powerFun.getId() + "," + powerFun.getModuleId() + ",'"+ Constant.funcMap.get(powerFun.getId()) + "','powerCode','" + powerFun.getId() + "'," + checkVal + ",'','');\n");
				}
				
			}else{
				//当前登录用户对应权限   模块
				Map userFunMap = new HashMap();
				userFunMap.put("userId", loginUser.getId());
				List<PowerUserFun> userFunList = powerUserFunService.getList(userFunMap);
				
				int checkVal = 0;
				Map powerFunMap = new HashMap();
				PowerFunction powerFunction = null;
				for(PowerUserFun userFun : userFunList){
					powerFunMap.put("id", userFun.getFunctionId());
					powerFunction = powerFunctionService.get(powerFunMap);
					dtree.append("mytree.add(" + powerFunction.getModuleId() + ",0,'" + Constant.moduleMap.get(powerFunction.getModuleId()) + "','module','" + powerFunction.getModuleId() + "',0);\n");
					dtree.append("mytree.add(" + userFun.getFunctionId() + "," + powerFunction.getModuleId() + ",'"+ Constant.funcMap.get(userFun.getFunctionId()) + "','powerCode','" + userFun.getFunctionId() + "'," + checkVal + ",'','');\n");
				}
			}
			dtree.append("document.write(mytree);\n");
			
			powerRoleFunBo.setDtree(dtree.toString());
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return powerRoleFunBo;
	}
	
	
	/**
	 * 添加角色
	 */
	@Transactional
	public String addRole(PowerRole powerRole, String[] funids){
		String result = "";
		
		try{
			Map roleMap = new HashMap();
			roleMap.put("name", powerRole.getName());
			roleMap.put("domainId", powerRole.getDomainId());
			roleMap.put("platId", powerRole.getPlatId());
			roleMap.put("status", Constant.STATUS_VALID);
			List<PowerRole> roleList = powerRoleService.getList(roleMap);
			
			if(roleList !=null && roleList.size()>0){
				result = "已存在角色名【"+ powerRole.getName() +"】";
				
			}else{
				powerRoleService.add(powerRole);
				
				PowerRoleFun powerRoleFun = null;
				for(String functionId : funids){
					powerRoleFun = new PowerRoleFun();
					
					powerRoleFun.setRoleId(powerRole.getId());
					powerRoleFun.setFunctionId(functionId);
					
					powerRoleFunService.add(powerRoleFun);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 修改角色
	 */
	@Transactional
	public String editRole(PowerRole powerRoleNew, String[] funids){
		String status = Constant.DEAL_OK;
		String result = "";
		
		try{
			String roleId = powerRoleNew.getId();
			
			Map roleMap = new HashMap();
			roleMap.put("name", powerRoleNew.getName());
			roleMap.put("domainId", powerRoleNew.getDomainId());
			roleMap.put("platId", powerRoleNew.getPlatId());
			roleMap.put("status", Constant.STATUS_VALID);
			List<PowerRole> roleList = powerRoleService.getList(roleMap);
			
			for(PowerRole role : roleList){
				if(!role.getId().equals(roleId)){
					if(role.getName().equals(powerRoleNew.getName())){
						result = "已存在角色名【"+ powerRoleNew.getName() +"】";
						status = Constant.DEAL_ERR;
						break;
					}
				}
			}
			
			if(Constant.DEAL_OK.equals(status)){
				powerRoleService.update(powerRoleNew);
				
				//删除并添加角色权限
				Map map = new HashMap();
				map.put("roleId", roleId);
				powerRoleFunService.delete(map);
				
				PowerRoleFun roleFun = null;
				for(String funid : funids){
					roleFun = new PowerRoleFun();
					roleFun.setRoleId(roleId);
					roleFun.setFunctionId(funid);
					
					powerRoleFunService.add(roleFun);
				}
				
				//查看角色下用户
				List<PowerUserRole> users = powerUserRoleService.getList(map);
				
				//删除角色下用户对应的权限  并添加
				Map userMap = new HashMap();
				for(PowerUserRole user : users){
					userMap.put("userId", user.getUserId());
					powerUserFunService.delete(userMap);
					
					PowerUserFun userFun = null;
					for(String functionId : funids){
						userFun = new PowerUserFun();
						userFun.setUserId(user.getUserId());
						userFun.setFunctionId(functionId);
						
						powerUserFunService.add(userFun);
					}
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
