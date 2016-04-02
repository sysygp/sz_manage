package com.zhrt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.dao.UserRegistDao;
import com.zhrt.entity.UserRegist;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class UserRegistService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserRegistService.class);
	@Autowired
	private UserRegistDao userRegistDao;

	@Transactional
	public UserRegist getById(String id) {
		UserRegist billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		billing = userRegistDao.get(paramMap);
		return billing;
	}
	
	@Transactional
	public UserRegist getByUserRegistDate(String userRegistDate) {
		UserRegist billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userRegistDate", userRegistDate);
		billing = userRegistDao.get(paramMap);
		return billing;
	}
	
	@Transactional
	public UserRegist getAllByUserRegistDate(String userRegistDate,String channelId) {
		UserRegist billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userRegistDate", userRegistDate);
		if(StringUtils.isNotBlank(channelId)){
			paramMap.put("channelId", channelId);
		}
		billing = userRegistDao.getByUserRegistDate(paramMap);
		if(billing != null){
			billing.setUserRegistDate(userRegistDate);
		}
		return billing;
	}
	
	@Transactional
	public UserRegist getByChannelId(String channelId) {
		UserRegist billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelId", channelId);
		billing = userRegistDao.get(paramMap);
		return billing;
	}
	
	@Transactional
	public UserRegist getByChannelIdBetweenTime(String channelId,String beginTime,String endTime) {
		UserRegist billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelId", channelId);
		paramMap.put("beginTime", beginTime);
		paramMap.put("endTime", endTime);
		billing = userRegistDao.getByChannelIdBetweenTime(paramMap);
		if(billing != null){
			billing.setChannelId(channelId);
		}
		return billing;
	}
	
	@Transactional
	public UserRegist getByRegistdateAndChannel(String userRegistDate,String channelId) {
		UserRegist billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("channelId", channelId);
		paramMap.put("userRegistDate", userRegistDate);
		billing = userRegistDao.get(paramMap);
		return billing;
	}

	@Transactional
	public List<UserRegist> getList() {
		List<UserRegist> billingList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		billingList = userRegistDao.getList(paramMap);
		return billingList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<UserRegist> getList(Map paramMap) {
		List<UserRegist> billingList = null;
		billingList = userRegistDao.getList(paramMap);
		return billingList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = userRegistDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(UserRegist billing) {
		userRegistDao.add(billing);
	}

	@Transactional
	public String update(UserRegist billing) {
		String id = "";
		userRegistDao.update(billing);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		userRegistDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		userRegistDao.delBatch(idsMap);
	}

}
