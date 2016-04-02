package com.power.service;

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

import com.power.dao.PowerFunctionDao;
import com.power.entity.PowerFunction;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerFunctionService {
	private static Logger logger = LoggerFactory.getLogger(PowerFunctionService.class);

	@Autowired
	private PowerFunctionDao powerFunctionDao;

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<PowerFunction> getListValid()   {
		List<PowerFunction> entityList = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("status", Constant.STATUS_VALID);
			entityList = powerFunctionDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<PowerFunction> getListByModule(String moduleId)   {
		List<PowerFunction> entityList = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("moduleId", moduleId);
			paramMap.put("status", Constant.STATUS_VALID);
			entityList = powerFunctionDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<PowerFunction> getListValidByModule(String moduleId)   {
		List<PowerFunction> entityList = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("status", Constant.STATUS_VALID);
			paramMap.put("moduleId", moduleId);
			entityList = powerFunctionDao.getList(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return entityList;
	}
	
	@Transactional
	public PowerFunction get(Map paramMap){
		PowerFunction entity = null;
		try {
			entity = powerFunctionDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Transactional
	public PowerFunction getById(String id)   {
		PowerFunction entity = null;
		try {
			
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			entity = powerFunctionDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	@Transactional
	public List<PowerFunction> getList()   {
		List<PowerFunction> powerModuleList = null;
		try {
			Map paramMap = new HashMap();
			paramMap.put("status",  Constant.STATUS_VALID);
			powerModuleList = powerFunctionDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	@Transactional
	public List<PowerFunction> getList(Map paramMap)   {
		List<PowerFunction> powerModuleList = null;
		try {
			
			powerModuleList = powerFunctionDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return powerModuleList;
	}
	
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			
			listCount = powerFunctionDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	
	@Transactional
	public void add(PowerFunction entity)   {
		try {
			
			powerFunctionDao.add(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(PowerFunction entity)   {
		String id = null;
		try {
			
			powerFunctionDao.update(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delById(String id)   {
		try {
			
			PowerFunction entity = new PowerFunction();
			entity.setId(id);
			powerFunctionDao.delById(entity);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerFunctionDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> getFunMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", Constant.STATUS_VALID);
		List<PowerFunction> funList = powerFunctionDao.getList(param);
		for (PowerFunction fun : funList) {
			map.put(fun.getId(), fun.getFunctionName());
		}
		//System.out.println(map);
		return map;
	}
}
