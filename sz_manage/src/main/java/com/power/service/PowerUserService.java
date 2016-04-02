package com.power.service;

import java.util.Arrays;
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
import com.power.entity.PowerUser;
import com.power.entity.PowerUserFun;
import com.power.entity.PowerUserRole;
import com.system.util.stringutil.StrUtils;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerUserService {
	private static Logger logger = LoggerFactory.getLogger(PowerUserService.class);

	@Autowired
	private PowerUserDao powerUserDao;
	@Autowired
	private PowerUserRoleDao powerUserRoleDao;
	@Autowired
	private PowerUserRoleService powerUserRoleService;
	@Autowired
	private PowerRoleFunDao powerRoleFunDao;
	@Autowired
	private PowerRoleFunService powerRoleFunService;
	@Autowired
	private PowerUserFunDao powerUserFunDao;
	@Autowired
	private PowerUserFunService powerUserFunService;

	@Transactional
	public PowerUser getById(String id)   {
		PowerUser entity = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			entity = powerUserDao.get(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Transactional
	public PowerUser findPowerUser(String loginId, String loginPwd)   {
		PowerUser entity = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("loginId", loginId);
			paramMap.put("loginPwd", loginPwd);
			paramMap.put("status", Constant.STATUS_VALID);
			
			entity = powerUserDao.get(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Transactional
	public List<PowerUser> getList()   {
		List<PowerUser> powerModuleList = null;
		try {
			
			Map paramMap = new HashMap();
			powerModuleList = powerUserDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerUser> getList(Map paramMap)   {
		List<PowerUser> powerModuleList = null;
		try {
			
			powerModuleList = powerUserDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			
			listCount = powerUserDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(PowerUser entity,String[] roleIds)   {
		try {
			
			//新增用户
			powerUserDao.add(entity);
			
			
			/*
			 * 新增用户角色和权限
			 */
			if(roleIds != null && roleIds.length > 0){
				//新增用户角色
				for(String roleId : roleIds){
					PowerUserRole userRole = new PowerUserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(entity.getId());
					powerUserRoleDao.add(userRole);
				}
				
				//新增用户权限
				List<String> functionids = powerRoleFunService.getFunidsByRoleids(roleIds);
				if(functionids != null && functionids.size() > 0){
					for(String functionId : functionids){
						PowerUserFun powerUserFun = new PowerUserFun();
						powerUserFun.setFunctionId(functionId);
						powerUserFun.setUserId(entity.getId());
						powerUserFunDao.add(powerUserFun);
					}
				}
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(PowerUser entity,String[] roleIds)   {
		String id = null;
		try {
			
			powerUserDao.update(entity);
			
			/*
			 * 修改用户角色和权限
			 */
			if(roleIds != null && roleIds.length > 0){
				
				//前台选中的该用户角色信息
				List<String> userRoleidsNew = Arrays.asList(roleIds);
				//数据库中原有的该用户角色信息
				List<String> userRoleidsOld = powerUserRoleService.getRolesByUserid(entity.getId());
				
				
				//新赋值的角色
				List<String> needAddUserRoleIds = StrUtils.getInOneCollElements(userRoleidsNew, userRoleidsOld);
				//取消的角色
				List<String> needDelUserRoleIds = StrUtils.getInOneCollElements(userRoleidsOld, userRoleidsNew);
				
				
				if(needAddUserRoleIds != null && needAddUserRoleIds.size() > 0){
					//保存新赋值的角色
					for(String userRoleid : needAddUserRoleIds){
						PowerUserRole userRole = new PowerUserRole();
						userRole.setRoleId(userRoleid);
						userRole.setUserId(entity.getId());
						powerUserRoleDao.add(userRole);
					}
				}
				
				
				
				if(needDelUserRoleIds != null && needDelUserRoleIds.size() > 0){
					//删除取消的角色
					for(String userRoleid : needDelUserRoleIds){
						PowerUserRole userRole = new PowerUserRole();
						userRole.setRoleId(userRoleid);
						userRole.setUserId(entity.getId());
						powerUserRoleDao.delete(userRole);
					}
				}
				
				
				
				
				
				
				//前台选中的该用户角色信息对应的权限
				List<String> userFunidsNew = powerRoleFunService.getFunidsByRoleids(roleIds);
				//数据库中原有的该用户的权限
				List<String> userFunidsOld = powerUserFunService.getFunidsByUserid(entity.getId());
				
				//新赋值的用户权限信息
				List<String> needAddUserFunIds = StrUtils.getInOneCollElements(userFunidsNew, userFunidsOld);
				//取消的用户权限信息
				List<String> needDelUserFunIds = StrUtils.getInOneCollElements(userFunidsOld, userFunidsNew);
				
				
				
				if(needAddUserFunIds != null && needAddUserFunIds.size() > 0){
					//保存新赋值的角色对应的权限
					for(String userFunid : needAddUserFunIds){
						PowerUserFun userFun = new PowerUserFun();
						userFun.setFunctionId(userFunid);
						userFun.setUserId(entity.getId());
						powerUserFunDao.add(userFun);
					}
				}
				
				
				if(needDelUserFunIds != null && needDelUserFunIds.size() > 0){
					//删除取消的角色对应的权限
					for(String userFunid : needDelUserFunIds){
						PowerUserFun userFun = new PowerUserFun();
						userFun.setFunctionId(userFunid);
						userFun.setUserId(entity.getId());
						powerUserFunDao.delete(userFun);
					}
				}
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void setUserFun(String userId,List<String> userFunidsNew,List<String> userFunidsOld){
		//新赋值的用户权限信息
		List<String> needAddUserFunIds = StrUtils.getInOneCollElements(userFunidsNew, userFunidsOld);
		//取消的用户权限信息
		List<String> needDelUserFunIds = StrUtils.getInOneCollElements(userFunidsOld, userFunidsNew);
		
		
		if(needAddUserFunIds != null && needAddUserFunIds.size() > 0){
			//保存新赋值的角色对应的权限
			for(String userFunid : needAddUserFunIds){
				PowerUserFun userFun = new PowerUserFun();
				userFun.setFunctionId(userFunid);
				userFun.setUserId(userId);
				powerUserFunDao.add(userFun);
			}
		}
		
		
		if(needDelUserFunIds != null && needDelUserFunIds.size() > 0){
			//删除取消的角色对应的权限
			for(String userFunid : needDelUserFunIds){
				PowerUserFun userFun = new PowerUserFun();
				userFun.setFunctionId(userFunid);
				userFun.setUserId(userId);
				powerUserFunDao.delete(userFun);
			}
		}
	}
	
	
	@Transactional
	public void updateUserFun(PowerUser entity,String[] funIds)   {
		
		if(funIds != null && funIds.length > 0){
			//前台选中的该用户角色信息对应的权限
			List<String> userFunidsNew = Arrays.asList(funIds);
			//数据库中原有的该用户的权限
			List<String> userFunidsOld = powerUserFunService.getFunidsByUserid(entity.getId());
			
			setUserFun(entity.getId(), userFunidsNew, userFunidsOld);
		}
		
	}
	
	
	@Transactional
	public void delById(String id)   {
		try {
			
			PowerUser entity = new PowerUser();
			entity.setId(id);
			powerUserDao.delById(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerUserDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Transactional
	public String findPowerUserNameByLoginId(String loginId) {
		 String name = "";
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("loginId", loginId);
			PowerUser powerUser = powerUserDao.get(paramMap);
			if(powerUser != null){
				name = powerUser.getLoginId();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
}
