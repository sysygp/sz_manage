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

import com.zhrt.dao.SpcodeLimitDao;
import com.zhrt.entity.SpcodeLimit;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SpcodeLimitService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpcodeLimitService.class);
	@Autowired
	private SpcodeLimitDao spcodeLimitDao;

	@Transactional
	public SpcodeLimit getById(String id) {
		SpcodeLimit entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		entity = spcodeLimitDao.get(paramMap);
		return entity;
	}

	@Transactional
	public List<SpcodeLimit> getList() {
		List<SpcodeLimit> rankTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		rankTypeInfoList = spcodeLimitDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<SpcodeLimit> getList(Map paramMap) {
		List<SpcodeLimit> rankTypeInfoList = null;
		rankTypeInfoList = spcodeLimitDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = spcodeLimitDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(SpcodeLimit entity) {
		spcodeLimitDao.add(entity);
	}

	@Transactional
	public String update(SpcodeLimit entity) {
		String id = "";
		spcodeLimitDao.update(entity);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		spcodeLimitDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		spcodeLimitDao.delBatch(idsMap);
	}

}
