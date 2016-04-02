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

import com.zhrt.dao.BillingterminalDao;
import com.zhrt.entity.Billingterminal;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class BillingterminalService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(BillingterminalService.class);
	@Autowired
	private BillingterminalDao billingterminalDao;

	@Transactional
	public Billingterminal getById(String id) {
		Billingterminal billing = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		billing = billingterminalDao.get(paramMap);
		return billing;
	}

	@Transactional
	public List<Billingterminal> getList() {
		List<Billingterminal> billingList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		billingList = billingterminalDao.getList(paramMap);
		return billingList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<Billingterminal> getList(Map paramMap) {
		List<Billingterminal> billingList = null;
		billingList = billingterminalDao.getList(paramMap);
		return billingList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = billingterminalDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(Billingterminal billing) {
		billingterminalDao.add(billing);
	}

	@Transactional
	public String update(Billingterminal billing) {
		String id = "";
		billingterminalDao.update(billing);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		billingterminalDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		billingterminalDao.delBatch(idsMap);
	}

}
