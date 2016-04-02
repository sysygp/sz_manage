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

import com.zhrt.dao.PropInfoDao;
import com.zhrt.entity.PropInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PropInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PropInfoService.class);

	@Autowired
	private PropInfoDao propInfoDao;

	@Transactional
	public PropInfo getById(String id) {
		PropInfo cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = propInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<PropInfo> getList() {
		List<PropInfo> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = propInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<PropInfo> getList(Map<String, Object> paramMap) {
		List<PropInfo> cpList = null;
		try {
			cpList = propInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = propInfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(PropInfo cpInfo) {
		try {
			propInfoDao.add(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(PropInfo cpInfo) {
		try {
			propInfoDao.update(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			propInfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			propInfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Map<String, Object> getPropMap(){
		Map<String, Object> propMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<PropInfo> propList = propInfoDao.getList(param);
		for (PropInfo prop : propList) {
			propMap.put(prop.getId(), prop);
		}
		return propMap;
	}
	@Transactional
	public List<PropInfo> getPropsByAppId(String appId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("appId", appId);
		List<PropInfo> propList = propInfoDao.getPropsByAppId(param);
		return propList;
	}
}
