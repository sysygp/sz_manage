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

import com.zhrt.dao.MsgTypeDao;
import com.zhrt.entity.MsgTypeInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class MsgTypeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(MsgTypeService.class);
	@Autowired
	private MsgTypeDao msgTypeDao;

	@Transactional
	public MsgTypeInfo getById(String id) {
		MsgTypeInfo msgTypeInfo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		msgTypeInfo = msgTypeDao.get(paramMap);
		return msgTypeInfo;
	}

	@Transactional
	public List<MsgTypeInfo> getList() {
		List<MsgTypeInfo> msgTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		msgTypeInfoList = msgTypeDao.getList(paramMap);
		return msgTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<MsgTypeInfo> getList(Map paramMap) {
		List<MsgTypeInfo> msgTypeInfoList = null;
		msgTypeInfoList = msgTypeDao.getList(paramMap);
		return msgTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = msgTypeDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(MsgTypeInfo msgTypeInfo) {
		msgTypeDao.add(msgTypeInfo);
	}

	@Transactional
	public String update(MsgTypeInfo msgTypeInfo) {
		String id = "";
		msgTypeDao.update(msgTypeInfo);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		msgTypeDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		msgTypeDao.delBatch(idsMap);
	}

}
