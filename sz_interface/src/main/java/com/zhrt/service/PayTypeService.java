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

import com.zhrt.dao.PayTypeDAO;
import com.zhrt.entity.PayType;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PayTypeService {
	private static Logger logger = LoggerFactory.getLogger(PayTypeService.class);

	@Autowired
	private PayTypeDAO payTypeDao;
	
	@Transactional
	public PayType getById(String id){
		PayType paytype = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			paytype = payTypeDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return paytype;
	}
	
	
	@Transactional
	public List<PayType> getList()   {
		List<PayType> payTypeList = null;
		try {
			Map paramMap = new HashMap();
			payTypeList = payTypeDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payTypeList;
	}
	
	@Transactional
	public List<PayType> getList(Map paramMap)   {
		List<PayType> payTypeList = null;
		try {
			payTypeList = payTypeDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return payTypeList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = payTypeDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(PayType payType)   {
		try {
			
			payTypeDao.add(payType);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public String update(PayType payType)   {
		String id = null;
		try {
			
			payTypeDao.update(payType);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delById(String id){
		try {
			PayType payType = new PayType();
			payType.setId(id);
			payTypeDao.delById(id);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids){
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			payTypeDao.delBatch(idsMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Map<String, Object> getPayTypeMap() {
		Map<String, Object> payTypeMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<PayType> payTypeList = payTypeDao.getList(param);
		for (PayType payType : payTypeList) {
			payTypeMap.put(payType.getPayTypeId(), payType);
		}
		return payTypeMap;
	}
}
