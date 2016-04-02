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

import com.zhrt.dao.SpcodeDao;
import com.zhrt.entity.Spcode;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SpcodeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpcodeService.class);
	@Autowired
	private SpcodeDao spcodeDao;

	@Transactional
	public Spcode getById(String id) {
		Spcode rankTypeInfo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		rankTypeInfo = spcodeDao.get(paramMap);
		return rankTypeInfo;
	}

	@Transactional
	public List<Spcode> getList() {
		List<Spcode> rankTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		rankTypeInfoList = spcodeDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<Spcode> getList(Map paramMap) {
		List<Spcode> rankTypeInfoList = null;
		rankTypeInfoList = spcodeDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = spcodeDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(Spcode rankTypeInfo) {
		spcodeDao.add(rankTypeInfo);
	}

	@Transactional
	public String update(Spcode rankTypeInfo) {
		String id = "";
		spcodeDao.update(rankTypeInfo);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		spcodeDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		spcodeDao.delBatch(idsMap);
	}

}
