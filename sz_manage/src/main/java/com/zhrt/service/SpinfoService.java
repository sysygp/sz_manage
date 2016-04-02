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

import com.zhrt.dao.SpinfoDao;
import com.zhrt.entity.CpInfo;
import com.zhrt.entity.Spinfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SpinfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpinfoService.class);
	@Autowired
	private SpinfoDao spinfoDao;

	@Transactional
	public Spinfo getById(String id) {
		Spinfo rankTypeInfo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		rankTypeInfo = spinfoDao.get(paramMap);
		return rankTypeInfo;
	}

	@Transactional
	public List<Spinfo> getList() {
		List<Spinfo> rankTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		rankTypeInfoList = spinfoDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<Spinfo> getList(Map paramMap) {
		List<Spinfo> rankTypeInfoList = null;
		rankTypeInfoList = spinfoDao.getList(paramMap);
		return rankTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = spinfoDao.getListCount(paramMap);
		return listCount;
	}
	
	@Transactional
	public Map<String, Object> getSpMap(){
		Map<String, Object> spMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<Spinfo> spList = spinfoDao.getList(param);
		for (Spinfo sp : spList) {
			spMap.put(sp.getId(), sp);
		}
		return spMap;
	}

	@Transactional
	public void add(Spinfo rankTypeInfo) {
		spinfoDao.add(rankTypeInfo);
	}

	@Transactional
	public String update(Spinfo rankTypeInfo) {
		String id = "";
		spinfoDao.update(rankTypeInfo);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		spinfoDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		spinfoDao.delBatch(idsMap);
	}

}
