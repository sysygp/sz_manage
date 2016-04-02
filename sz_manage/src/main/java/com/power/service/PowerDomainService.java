package com.power.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.power.dao.PowerDomainDao;
import com.power.entity.PowerDomain;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PowerDomainService {
	private static Logger logger = LoggerFactory.getLogger(PowerDomainService.class);
	
	@Autowired
	private PowerDomainDao powerDomainDao;
	
	@Transactional
	public PowerDomain getById(String id) {
		PowerDomain entity = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			entity = powerDomainDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	@Transactional
	public List<PowerDomain> getList(){
		List<PowerDomain> domainList = null;
		try {
			Map paramMap = new HashMap();
			domainList = powerDomainDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return domainList;
	}
	
	@Transactional
	public List<PowerDomain> getList(Map paramMap)   {
		List<PowerDomain> domainList = null;
		try {
			domainList = powerDomainDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return domainList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = powerDomainDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(PowerDomain entity)   {
		try {
			powerDomainDao.add(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void update(PowerDomain entity)   {
		try {
			powerDomainDao.update(entity);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delById(String id) {
		try {
			powerDomainDao.delById(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			powerDomainDao.delMul(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
