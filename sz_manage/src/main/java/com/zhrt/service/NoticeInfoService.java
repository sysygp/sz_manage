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

import com.zhrt.dao.NoticeInfoDao;
import com.zhrt.entity.NoticeInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class NoticeInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(NoticeInfoService.class);

	@Autowired
	private NoticeInfoDao noticeDao;

	@Transactional
	public NoticeInfo getById(String id) {
		NoticeInfo notice = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			notice = noticeDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notice;
	}

	@Transactional
	public List<NoticeInfo> getList() {
		List<NoticeInfo> noticeList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			noticeList = noticeDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noticeList;
	}

	@Transactional
	public List<NoticeInfo> getList(Map<String, Object> paramMap) {
		List<NoticeInfo> noticeList = null;
		try {
			noticeList = noticeDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noticeList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = noticeDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(NoticeInfo notice) {
		try {
			noticeDao.add(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(NoticeInfo notice) {
		try {
			noticeDao.update(notice);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			noticeDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(String [] ids) {
		try {
			Map idsMap = new HashMap<String, Object>();
			idsMap.put("ids", ids);
			noticeDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Map<String, String> getNoticeMap(){
		Map<String, String> noticeMap = new HashMap<String, String>();
		Map<String, String> param = new HashMap<String, String>();
		List<NoticeInfo> noticeList = noticeDao.getList(param);
		for (NoticeInfo notice : noticeList) {
			noticeMap.put(notice.getId(), notice.getNoticeTitle());
		}
		return noticeMap;
	}

}
