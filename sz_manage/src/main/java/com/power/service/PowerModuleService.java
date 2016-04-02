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

import com.power.dao.PowerModuleDao;
import com.power.entity.PowerFunction;
import com.power.entity.PowerModule;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerModuleService {
	private static Logger logger = LoggerFactory.getLogger(PowerModuleService.class);

	@Autowired
	private PowerModuleDao powerModuleDao;

	
	@Transactional
	public PowerModule getById(String id)   {
		PowerModule powerModule = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("moduleId", id);
			
			powerModule = powerModuleDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModule;
	}
	
	
	@Transactional
	public List<PowerModule> getList()   {
		List<PowerModule> powerModuleList = null;
		try {
			
			Map paramMap = new HashMap();
			powerModuleList = powerModuleDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerModule> getList(Map paramMap)   {
		List<PowerModule> powerModuleList = null;
		try {
			
			powerModuleList = powerModuleDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			
			listCount = powerModuleDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(PowerModule powerModule)   {
		try {
			
			powerModuleDao.add(powerModule);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(PowerModule powerModule)   {
		String id = null;
		try {
			
			powerModuleDao.update(powerModule);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delById(String id)   {
		try {
			
			PowerModule powerModule = new PowerModule();
			powerModule.setModuleId(id);
			powerModuleDao.delById(powerModule);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerModuleDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Map<String, Object> getModuleMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<PowerModule> moduleList = powerModuleDao.getList(map);
		for(PowerModule module : moduleList) {
			map.put(module.getModuleId(), module.getModuleName());
		}
		return map;
	}
}
