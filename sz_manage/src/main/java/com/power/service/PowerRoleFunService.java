package com.power.service;

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
import com.power.entity.PowerRole;
import com.power.entity.PowerRoleFun;
import com.system.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerRoleFunService {
	private static Logger logger = LoggerFactory.getLogger(PowerRoleFunService.class);

	@Autowired
	private PowerRoleFunDao powerRoleFunDao;

	
	@Transactional
	public PowerRoleFun getById(String id)   {
		PowerRoleFun entity = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			entity = powerRoleFunDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	@Transactional
	public List<PowerRoleFun> getList()   {
		List<PowerRoleFun> powerModuleList = null;
		try {
			
			Map paramMap = new HashMap();
			powerModuleList = powerRoleFunDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerRoleFun> getList(Map paramMap)   {
		List<PowerRoleFun> powerModuleList = null;
		try {
			
			powerModuleList = powerRoleFunDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			
			listCount = powerRoleFunDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	
	@Transactional
	public List<String> getFunidsByRoleid(String roleId)   {
		List<String> funidList = null;
		try {
			Map queryMap = new HashMap();
			queryMap.put(Constant.DATABASE_QUERY_FILED, "functionId");
			queryMap.put("roleId", roleId);
			funidList = powerRoleFunDao.getOnefield(queryMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funidList;
	}
	
	@Transactional
	public List<String> getFunidsByRoleids(String[] roleIds)   {
		List<String> funidList = null;
		try {
			Map queryMap = new HashMap();
			queryMap.put(Constant.DATABASE_QUERY_FILED, "functionId");
			queryMap.put("ids", roleIds);
			funidList = powerRoleFunDao.getOnefieldByids(queryMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funidList;
	}
	
	@Transactional
	public void add(PowerRoleFun entity)   {
		try {
			
			powerRoleFunDao.add(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(PowerRoleFun entity)   {
		String id = null;
		try {
			
			powerRoleFunDao.update(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delete(Map paramMap){
		try {
			powerRoleFunDao.delete(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerRoleFunDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
