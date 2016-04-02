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

import com.zhrt.dao.UserLimitDao;
import com.zhrt.entity.UserLimit;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class UserLimitService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserLimitService.class);
	@Autowired
	private UserLimitDao userLimitDao;

	@Transactional
	public UserLimit getById(String id) {
		UserLimit entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		entity = userLimitDao.get(paramMap);
		return entity;
	}

	@Transactional
	public List<UserLimit> getList() {
		List<UserLimit> rankTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		rankTypeInfoList = userLimitDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<UserLimit> getList(Map paramMap) {
		List<UserLimit> rankTypeInfoList = null;
		rankTypeInfoList = userLimitDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = userLimitDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(UserLimit entity) {
		userLimitDao.add(entity);
	}

	@Transactional
	public String update(UserLimit entity) {
		String id = "";
		userLimitDao.update(entity);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		userLimitDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		userLimitDao.delBatch(idsMap);
	}

}
