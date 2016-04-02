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

import com.zhrt.dao.BillingDao;
import com.zhrt.entity.Billing;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class BillingService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BillingService.class);
	@Autowired
	private BillingDao billingDao;

	@Transactional
	public Billing getById(String id) {
		Billing billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		billing = billingDao.get(paramMap);
		return billing;
	}

	@Transactional
	public List<Billing> getList() {
		List<Billing> billingList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		billingList = billingDao.getList(paramMap);
		return billingList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<Billing> getList(Map paramMap) {
		List<Billing> billingList = null;
		billingList = billingDao.getList(paramMap);
		return billingList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = billingDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(Billing billing) {
		billingDao.add(billing);
	}

	@Transactional
	public String update(Billing billing) {
		String id = "";
		billingDao.update(billing);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		billingDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		billingDao.delBatch(idsMap);
	}

}
