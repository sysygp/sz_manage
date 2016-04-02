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

import com.zhrt.dao.OperLogDao;
import com.zhrt.entity.OperLog;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class OperLogService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(OperLogService.class);

	@Autowired
	private OperLogDao operLogDao;

	@Transactional
	public OperLog getById(String id) {
		OperLog log = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		log = operLogDao.get(paramMap);
		return log;
	}

	@Transactional
	public List<OperLog> getList() {
		List<OperLog> logList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		logList = operLogDao.getList(paramMap);
		return logList;
	}

	@Transactional
	public List<OperLog> getList(Map<String, Object> paramMap) {
		List<OperLog> logList = null;
		logList = operLogDao.getList(paramMap);
		return logList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		listCount = operLogDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(OperLog log) {
		operLogDao.add(log);
	}

	@Transactional
	public void update(OperLog log) {
		operLogDao.update(log);
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		operLogDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		operLogDao.delBatch(idsMap);
	}
}
