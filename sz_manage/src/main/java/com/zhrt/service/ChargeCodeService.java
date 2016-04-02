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

import com.zhrt.dao.ChargeCodeDao;
import com.zhrt.entity.ChargeCode;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class ChargeCodeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ChargeCodeService.class);
	@Autowired
	private ChargeCodeDao chargeCodeDao;

	@Transactional
	public ChargeCode getById(String id) {
		ChargeCode chargeCode = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		chargeCode = chargeCodeDao.get(paramMap);
		return chargeCode;
	}
	
	@Transactional
	public List<ChargeCode> getList() {
		return getList(null);
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<ChargeCode> getListValid() {
		Map paramMap = new HashMap();
		
		return getList(paramMap);
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<ChargeCode> getList(Map paramMap) {
		List<ChargeCode> chargeCodeList = chargeCodeDao.getList(paramMap);
		return chargeCodeList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = chargeCodeDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(ChargeCode chargeCode) {
		chargeCodeDao.add(chargeCode);
	}

	@Transactional
	public String update(ChargeCode chargeCode) {
		String id = "";
		chargeCodeDao.update(chargeCode);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		chargeCodeDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		chargeCodeDao.delBatch(idsMap);
	}

}
