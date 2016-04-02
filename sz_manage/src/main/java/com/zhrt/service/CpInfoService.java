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

import com.zhrt.dao.CpInfoDao;
import com.zhrt.entity.CpInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class CpInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CpInfoService.class);

	@Autowired
	private CpInfoDao cpInfoDao;

	@Transactional
	public CpInfo getById(String id) {
		CpInfo cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = cpInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<CpInfo> getList() {
		List<CpInfo> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = cpInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<CpInfo> getList(Map<String, Object> paramMap) {
		List<CpInfo> cpList = null;
		try {
			cpList = cpInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = cpInfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(CpInfo cpInfo) {
		try {
			cpInfoDao.add(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(CpInfo cpInfo) {
		try {
			cpInfoDao.update(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cpInfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			cpInfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Map<String, Object> getCpMap(){
		Map<String, Object> cpMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<CpInfo> cpList = cpInfoDao.getList(param);
		for (CpInfo cp : cpList) {
			cpMap.put(cp.getId(), cp);
		}
		return cpMap;
	}

	public List<CpInfo> getForPage(Map paramMap) {
		List<CpInfo> cpList = null;
		cpList = cpInfoDao.getForPage(paramMap);
		return cpList;
	}
}
