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
import org.springframework.transaction.annotation.Transactional;

import com.power.dao.PowerRoleFunDao;
import com.power.dao.PowerUserDao;
import com.power.dao.PowerUserFunDao;
import com.power.dao.PowerUserRoleDao;
import com.power.entity.PowerRoleFun;
import com.power.entity.PowerUser;
import com.power.entity.PowerUserFun;
import com.power.entity.PowerUserRole;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerUserRoleService {
	private static Logger logger = LoggerFactory.getLogger(PowerUserRoleService.class);

	@Autowired
	private PowerUserRoleDao powerUserRoleDao;
	@Autowired
	private PowerRoleFunDao powerRoleFunDao;
	@Autowired
	private PowerUserFunDao powerUserFunDao;
	@Autowired
	private PowerUserDao powerUserDao;

	@Transactional
	public PowerUserRole getById(String id)   {
		PowerUserRole entity = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			entity = powerUserRoleDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	@Transactional
	public List<PowerUserRole> getList()   {
		List<PowerUserRole> powerModuleList = null;
		try {
			
			Map paramMap = new HashMap();
			powerModuleList = powerUserRoleDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerUserRole> getList(Map paramMap)   {
		List<PowerUserRole> powerModuleList = null;
		try {
			powerModuleList = powerUserRoleDao.getList(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	/**
	 * 根据roleId查询用户总数
	 * 
	 */
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = powerUserRoleDao.getListCount(paramMap);
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	
	@Transactional
	public List<String> getUseridsByRoleid(String roleId)   {
		List<String> funidList = null;
		try {
			Map queryMap = new HashMap();
			queryMap.put(com.system.util.Constant.DATABASE_QUERY_FILED, "userId");
			queryMap.put("roleId", roleId);
			funidList = powerUserRoleDao.getOnefield(queryMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funidList;
	}
	
	@Transactional
	public List<String> getRolesByUserid(String userId)   {
		List<String> funidList = null;
		try {
			Map queryMap = new HashMap();
			queryMap.put(com.system.util.Constant.DATABASE_QUERY_FILED, "roleId");
			queryMap.put("userId", userId);
			funidList = powerUserRoleDao.getOnefield(queryMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funidList;
	}
	
	@Transactional
	public void add(PowerUserRole entity)   {
		try {
			powerUserRoleDao.add(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建用户角色和用户默认权限
	 * @param entity
	 */
	@Transactional
	public void addUserroleAndUserfun(PowerUserRole entity)   {
		try {
			//保存用户角色
			powerUserRoleDao.add(entity);
			
			//查询该角色的所有权限
			Map roleFunMap = new HashMap();
			roleFunMap.put("roleId", entity.getRoleId());
			List<PowerRoleFun> roleFunList = powerRoleFunDao.getList(roleFunMap);
			
			//将该角色的所有权限赋给用户
			for(PowerRoleFun powerRoleFun:roleFunList){
				PowerUserFun powerUserFun = new PowerUserFun();
				powerUserFun.setUserId(entity.getUserId());
				powerUserFun.setFunctionId(powerRoleFun.getFunctionId());
				powerUserFunDao.add(powerUserFun);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据roleId,domainId查询用户列表
	 * 
	 */
	@Transactional
	public List<PowerUser> findUsersByRoleId(Map paramMap){
		List<PowerUser> powerUserList = new ArrayList<PowerUser>();
		try{
			List<PowerUserRole> userList = powerUserRoleDao.getList(paramMap);
			
			Map map = new HashMap();
			map.put("domainId", paramMap.get("domainId"));
			map.put("status", Constant.STATUS_VALID);
			
			PowerUser powerUser = null;
			for(PowerUserRole userRole : userList){
				map.put("id", userRole.getUserId());
				powerUser = powerUserDao.get(map);
				
				powerUserList.add(powerUser);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerUserList;
	}
	
	@Transactional
	public String update(PowerUserRole entity)   {
		String id = null;
		try {
			
			powerUserRoleDao.update(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delete(Map paramMap){
		try {
			powerUserRoleDao.delete(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerUserRoleDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
