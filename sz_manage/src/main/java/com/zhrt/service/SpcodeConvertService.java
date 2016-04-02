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

import com.zhrt.dao.SpcodeConvertDao;
import com.zhrt.entity.SpcodeConvert;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SpcodeConvertService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SpcodeConvertService.class);

	@Autowired
	private SpcodeConvertDao spcodeConvertDao;

	@Transactional
	public SpcodeConvert getById(String id) {
		SpcodeConvert cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = spcodeConvertDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<SpcodeConvert> getList() {
		List<SpcodeConvert> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = spcodeConvertDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<SpcodeConvert> getList(Map<String, Object> paramMap) {
		List<SpcodeConvert> cpList = null;
		try {
			cpList = spcodeConvertDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = spcodeConvertDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(SpcodeConvert cpInfo) {
		try {
			spcodeConvertDao.add(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(SpcodeConvert cpInfo) {
		try {
			spcodeConvertDao.update(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			spcodeConvertDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			spcodeConvertDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Map<String, Object> getCpMap(){
		Map<String, Object> cpMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<SpcodeConvert> cpList = spcodeConvertDao.getList(param);
		for (SpcodeConvert cp : cpList) {
			cpMap.put(cp.getId(), cp);
		}
		return cpMap;
	}

	public List<SpcodeConvert> getForPage(Map paramMap) {
		List<SpcodeConvert> cpList = null;
		cpList = spcodeConvertDao.getForPage(paramMap);
		return cpList;
	}
}
