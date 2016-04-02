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

import com.zhrt.dao.SignLogDao;
import com.zhrt.entity.SignLog;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SignLogService {
	private static Logger logger = LoggerFactory.getLogger(SignLogService.class);

	@Autowired
	private SignLogDao signLogDao;
	
	@Transactional
	public SignLog getById(String id){
		SignLog SignLog = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			SignLog = signLogDao.get(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SignLog;
	}
	
	@Transactional
	public SignLog get(Object o){
		SignLog SignLog = null;
		try {
			SignLog = signLogDao.get(o);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SignLog;
	}
	
	@Transactional
	public List<SignLog> getList(){
		List<SignLog> behavList = null;
		try {
			Map paramMap = new HashMap();
			behavList = signLogDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavList;
	}
	
	@Transactional
	public List<SignLog> getList(Map paramMap) {
		List<SignLog> behavList = null;
		try {
			behavList = signLogDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return behavList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = signLogDao.getListCount(paramMap);
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(SignLog SignLog)   {
		try {
			signLogDao.add(SignLog);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void update(SignLog SignLog)   {
		try {
			signLogDao.update(SignLog);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delById(String id){
		try {
			SignLog SignLog = new SignLog();
			SignLog.setId(id);
			signLogDao.delById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids){
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			signLogDao.delBatch(idsMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
