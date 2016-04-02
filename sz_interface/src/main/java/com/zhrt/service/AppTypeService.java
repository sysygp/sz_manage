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

import com.zhrt.dao.AppTypeDao;
import com.zhrt.entity.AppTypeInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class AppTypeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AppTypeService.class);
	@Autowired
	private AppTypeDao appTypeDao;

	@Transactional
	public AppTypeInfo getById(String id) {
		AppTypeInfo appTypeInfo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		appTypeInfo = appTypeDao.get(paramMap);
		return appTypeInfo;
	}

	@Transactional
	public List<AppTypeInfo> getList() {
		List<AppTypeInfo> appTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		appTypeInfoList = appTypeDao.getList(paramMap);
		return appTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<AppTypeInfo> getList(Map paramMap) {
		List<AppTypeInfo> appTypeInfoList = null;
		appTypeInfoList = appTypeDao.getList(paramMap);
		return appTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = appTypeDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(AppTypeInfo appTypeInfo) {
		appTypeDao.add(appTypeInfo);
	}

	@Transactional
	public String update(AppTypeInfo appTypeInfo) {
		String id = "";
		appTypeDao.update(appTypeInfo);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		appTypeDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		appTypeDao.delBatch(idsMap);
	}

	@Transactional
	public Map<String, Object> getAppSortMap() {
		Map<String, Object> appSortMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppTypeInfo> appTypeList = appTypeDao.getList(map);
		for (AppTypeInfo appType : appTypeList) {
			appSortMap.put(appType.getId(), appType);
		}
		return appSortMap;
	}


}
