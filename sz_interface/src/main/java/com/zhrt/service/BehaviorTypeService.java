package com.zhrt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.dao.BehaviorTypeDAO;
import com.zhrt.entity.BehaviorType;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class BehaviorTypeService {
	private static Logger logger = LoggerFactory.getLogger(BehaviorTypeService.class);

	@Autowired
	private BehaviorTypeDAO behaviorTypeDao;
	
	@Transactional
	public BehaviorType getById(String id){
		BehaviorType behavType = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			behavType = behaviorTypeDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavType;
	}
	
	
	@Transactional
	public List<BehaviorType> getList(){
		List<BehaviorType> behavTypeList = null;
		try {
			Map paramMap = new HashMap();
			behavTypeList = behaviorTypeDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavTypeList;
	}
	
	@Transactional
	public List<BehaviorType> getList(Map paramMap) {
		List<BehaviorType> behavTypeList = null;
		try {
			behavTypeList = behaviorTypeDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavTypeList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = behaviorTypeDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(BehaviorType payType)   {
		try {
			behaviorTypeDao.add(payType);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(BehaviorType payType)   {
		String id = null;
		try {
			behaviorTypeDao.update(payType);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delById(String id){
		try {
			BehaviorType behavType = new BehaviorType();
			behavType.setId(id);
			behaviorTypeDao.delById(id);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids){
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			behaviorTypeDao.delBatch(idsMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
