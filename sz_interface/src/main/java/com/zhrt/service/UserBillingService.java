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

import com.zhrt.dao.UserBillingDao;
import com.zhrt.entity.UserBilling;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class UserBillingService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserBillingService.class);

	@Autowired
	private UserBillingDao userBillingDao;

	@Transactional
	public UserBilling getById(String id) {
		UserBilling cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = userBillingDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<UserBilling> getList() {
		List<UserBilling> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = userBillingDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<UserBilling> getList(Map<String, Object> paramMap) {
		List<UserBilling> cpList = null;
		try {
			cpList = userBillingDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = userBillingDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}


	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			userBillingDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			userBillingDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public UserBilling getByIndexAndResult(String indexId, String result,Map<String, String> locateMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("indexId", indexId);
		map.put("result", result);
		map.putAll(locateMap);
		UserBilling billing = userBillingDao.get(map);
		return billing;
	}
}
