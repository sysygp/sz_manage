package com.power.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.list.SetUniqueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.power.bo.DtreeBo;
import com.power.dao.PowerFunctionDao;
import com.power.dao.PowerModuleDao;
import com.power.dao.PowerRoleDao;
import com.power.dao.PowerRoleFunDao;
import com.power.dao.PowerUserFunDao;
import com.power.dao.PowerUserRoleDao;
import com.power.entity.PowerFunction;
import com.power.entity.PowerModule;
import com.power.entity.PowerRole;
import com.power.entity.PowerRoleFun;
import com.power.entity.PowerUser;
import com.power.entity.PowerUserFun;
import com.power.entity.PowerUserRole;
import com.power.util.DtreeUtil;
import com.system.util.map.MapTransUtil;
import com.system.util.stringutil.StrUtils;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerRoleService {
	private static Logger logger = LoggerFactory.getLogger(PowerRoleService.class);

	@Autowired
	private PowerRoleDao powerRoleDao;
	@Autowired
	private PowerModuleDao powerModuleDao;
	@Autowired
	private PowerFunctionDao powerFunctionDao;
	@Autowired
	private PowerUserFunDao powerUserFunDao;
	@Autowired
	private PowerUserFunService powerUserFunService;
	@Autowired
	private PowerRoleFunDao powerRoleFunDao;
	@Autowired
	private PowerRoleFunService powerRoleFunService;
	@Autowired
	private PowerUserRoleDao powerUserRoleDao;
	@Autowired
	private PowerUserRoleService powerUserRoleService;
	
	@Transactional
	public PowerRole getById(String id)   {
		PowerRole entity = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			entity = powerRoleDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	@Transactional
	public List<PowerRole> getList()   {
		List<PowerRole> powerModuleList = null;
		try {
			
			Map paramMap = new HashMap();
			powerModuleList = powerRoleDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerRole> getList(Map paramMap)   {
		List<PowerRole> powerModuleList = null;
		try {
			
			powerModuleList = powerRoleDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			
			listCount = powerRoleDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(PowerRole entity) {
		powerRoleDao.add(entity);
	}
	
	@Transactional
	public void update(PowerRole entity)   {
		try {
			
			powerRoleDao.update(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除角色
	 * */
	@Transactional
	public String delById(String id){
		String result = "";
		
		try{
			Map roleMap = new HashMap();
			roleMap.put("roleId", id);
			List<PowerUserRole> userRoles = powerUserRoleDao.getList(roleMap);
			
			if(userRoles!=null && userRoles.size()>0){
				result = "当前角色已分配用户,不能删除";
			}else{
				//删除角色
				PowerRole role = new PowerRole();
				role.setId(id);
				role.setStatus(Constant.STATUS_INVALID);
				powerRoleDao.update(MapTransUtil.bean2Map(role));
				
				//删除角色权限
				powerRoleFunDao.delete(roleMap);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 批量删除角色
	 * */
	@Transactional
	public String delByIds(String[] ids){
		String status = Constant.DEAL_OK;
		String result = "";
		
		try {
			Map roleMap = new HashMap();
			List<PowerUserRole> userRoles = null;
			for(String id : ids){
				roleMap.put("roleId", id);
				userRoles = powerUserRoleDao.getList(roleMap);
				if(userRoles!=null && userRoles.size()>0){
					result = "选中项中含有已分配用户的角色,不能删除";
					status = Constant.DEAL_ERR;
					break;
				}
			}
			
			if(Constant.DEAL_OK.equals(status)){
				//删除角色
				Map map = new HashMap();
				map.put("ids", ids);
				map.put("status", Constant.STATUS_INVALID);
				powerRoleDao.delMul(map);
				
				//删除角色权限
				Map roleFunMap = new HashMap();
				roleFunMap.put("ids", ids);
				powerRoleFunDao.delMul(roleFunMap);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
			List<PowerRole> roleList = powerRoleDao.getList(roleMap);
			
			if(roleList !=null && roleList.size()>0){
				result = "已存在角色名【"+ powerRole.getName() +"】";
				
			}else{
				powerRoleDao.add(powerRole);
				
				PowerRoleFun powerRoleFun = null;
				for(String functionId : funids){
					powerRoleFun = new PowerRoleFun();
					
					powerRoleFun.setRoleId(powerRole.getId());
					powerRoleFun.setFunctionId(functionId);
					
					powerRoleFunDao.add(powerRoleFun);
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public String editRole(PowerRole powerRoleNew, String[] funidsArray){
		String status = Constant.DEAL_OK;
		String result = "";
		
		try{
			String roleId = powerRoleNew.getId();
			
			
			/*
			 * 检查同一域下名称是否有重复
			 */
			Map roleMap = new HashMap();
			roleMap.put("name", powerRoleNew.getName());
			roleMap.put("domainId", powerRoleNew.getDomainId());
			roleMap.put("platId", powerRoleNew.getPlatId());
			roleMap.put("status", Constant.STATUS_VALID);
			List<PowerRole> roleList = powerRoleDao.getList(roleMap);
			
			if(roleList != null && roleList.size() > 0){
				PowerRole role = roleList.get(0);
				if(!role.getId().equals(roleId)){
					result = "已存在角色名【"+ powerRoleNew.getName() +"】";
					status = Constant.DEAL_ERR;
				}
			}
			
			
			
			
			if(Constant.DEAL_OK.equals(status)){

				//修改角色
				powerRoleDao.update(MapTransUtil.bean2Map(powerRoleNew));
				
				
				List<String> funids = Arrays.asList(funidsArray);
				Map funidNewMap = new HashMap();
				for(String funid : funids){
					funidNewMap.put(funid, "");
				}
				
				//该角色原有权限
				List<String> roleFuncOldList = powerRoleFunService.getFunidsByRoleid(roleId);
				Map roleFuncOldMap = new HashMap();
				for(String funidOld : roleFuncOldList){
					roleFuncOldMap.put(funidOld, "");
				}
				
				//该角色下所有用户
				List<String> userIds = powerUserRoleService.getUseridsByRoleid(roleId);
				
				
				
				
				/*
				 * 将新增加的权限进行保存到角色权限表中
				 * 将新增加的权限保存到该角色下用户对应的用户权限表中
				 */
				for(String funid : funids){
					
					//将该角色新增加的权限进行保存
					if(roleFuncOldMap.get(funid) == null){
						PowerRoleFun powerRoleFun = new PowerRoleFun();
						powerRoleFun.setFunctionId(funid);
						powerRoleFun.setRoleId(roleId);
						powerRoleFunDao.add(powerRoleFun);
					}
					
					//将该角色新增加的权限同步新增到该角色下的用户
					for(String userId : userIds){
						List<String> userFuncOldList = powerUserFunService.getFunidsByUserid(userId);
						Map userFuncOldMap = new HashMap();
						for(String funidOld : userFuncOldList){
							userFuncOldMap.put(funidOld, "");
						}
						
						if(userFuncOldMap.get(funid) == null){
							PowerUserFun powerUserFun = new PowerUserFun();
							powerUserFun.setFunctionId(funid);
							powerUserFun.setUserId(userId);
							powerUserFunDao.add(powerUserFun);
						}
					}
				}
				
				
				
				
				/*
				 * 将新删除的权限从角色权限表中删除
				 * 将新删除的权限从该角色下用户对应的用户权限表中删除
				 */
				for(String funOld : roleFuncOldList){
					
					//将该角色删除的权限从该角色权限中删除
					if(funidNewMap.get(funOld) == null){
						PowerRoleFun powerRoleFun = new PowerRoleFun();
						powerRoleFun.setFunctionId(funOld);
						powerRoleFun.setRoleId(roleId);
						powerRoleFunDao.delete(powerRoleFun);
					}
				}
				
				//将该角色删除的权限同步从该角色下的用户的权限中删除
				for(String userId : userIds){
					List<String> userFuncOldList = powerUserFunService.getFunidsByUserid(userId);
					
					for(String funOld : userFuncOldList){
						if(funidNewMap.get(funOld) == null){
							PowerUserFun powerUserFun = new PowerUserFun();
							powerUserFun.setFunctionId(funOld);
							powerUserFun.setUserId(userId);
							powerUserFunDao.delete(powerUserFun);
						}
					}
					
				}
				
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
