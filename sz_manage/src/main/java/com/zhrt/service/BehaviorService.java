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

import com.zhrt.dao.BehaviorDao;
import com.zhrt.entity.Behavior;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class BehaviorService {
	private static Logger logger = LoggerFactory.getLogger(BehaviorService.class);

	@Autowired
	private BehaviorDao behaviorDao;
	
	@Transactional
	public Behavior getById(String id){
		Behavior behavior = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			behavior = behaviorDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavior;
	}
	
	
	@Transactional
	public List<Behavior> getList(){
		List<Behavior> behavList = null;
		try {
			Map paramMap = new HashMap();
			behavList = behaviorDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavList;
	}
	
	@Transactional
	public List<Behavior> getList(Map paramMap) {
		List<Behavior> behavList = null;
		try {
			behavList = behaviorDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = behaviorDao.getListCount(paramMap);
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(Behavior behavior)   {
		try {
			behaviorDao.add(behavior);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void update(Behavior behavior)   {
		try {
			behaviorDao.update(behavior);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delById(String id){
		try {
			Behavior behavior = new Behavior();
			behavior.setId(id);
			behaviorDao.delById(id);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids){
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			behaviorDao.delBatch(idsMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
