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

import com.power.dao.PowerUserFunDao;
import com.power.entity.PowerUserFun;
import com.system.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerUserFunService {
	private static Logger logger = LoggerFactory.getLogger(PowerUserFunService.class);

	@Autowired
	private PowerUserFunDao powerUserFunDao;

	
	@Transactional
	public PowerUserFun getById(String id)   {
		PowerUserFun entity = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			entity = powerUserFunDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	@Transactional
	public List<PowerUserFun> getList()   {
		List<PowerUserFun> powerModuleList = null;
		try {
			
			Map paramMap = new HashMap();
			powerModuleList = powerUserFunDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerUserFun> getList(Map paramMap)   {
		List<PowerUserFun> powerModuleList = null;
		try {
			
			powerModuleList = powerUserFunDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	

	
	@Transactional
	public List<String> getFunidsByUserid(String userId)   {
		List<String> funidList = null;
		try {
			Map queryMap = new HashMap();
			queryMap.put(Constant.DATABASE_QUERY_FILED, "functionId");
			queryMap.put("userId", userId);
			funidList = powerUserFunDao.getOnefield(queryMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return funidList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			
			listCount = powerUserFunDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(PowerUserFun entity)   {
		try {
			
			powerUserFunDao.add(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(PowerUserFun entity)   {
		String id = null;
		try {
			
			powerUserFunDao.update(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delete(Map paramMap)   {
		try {
			powerUserFunDao.delete(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerUserFunDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
