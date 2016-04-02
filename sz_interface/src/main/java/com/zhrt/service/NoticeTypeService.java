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

import com.zhrt.dao.NoticeTypeDao;
import com.zhrt.entity.MsgTypeInfo;
import com.zhrt.entity.NoticeTypeInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class NoticeTypeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(NoticeTypeService.class);
	@Autowired
	private NoticeTypeDao noticeTypeDao;

	@Transactional
	public NoticeTypeInfo getById(String id) {
		NoticeTypeInfo noticeTypeInfo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		noticeTypeInfo = noticeTypeDao.get(paramMap);
		return noticeTypeInfo;
	}

	@Transactional
	public List<NoticeTypeInfo> getList() {
		List<NoticeTypeInfo> noticeTypeInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		noticeTypeInfoList = noticeTypeDao.getList(paramMap);
		return noticeTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<NoticeTypeInfo> getList(Map paramMap) {
		List<NoticeTypeInfo> noticeTypeInfoList = null;
		noticeTypeInfoList = noticeTypeDao.getList(paramMap);
		return noticeTypeInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = noticeTypeDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(NoticeTypeInfo noticeTypeInfo) {
		noticeTypeDao.add(noticeTypeInfo);
	}

	@Transactional
	public String update(NoticeTypeInfo noticeTypeInfo) {
		String id = "";
		noticeTypeDao.update(noticeTypeInfo);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		noticeTypeDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		noticeTypeDao.delBatch(idsMap);
	}

}
