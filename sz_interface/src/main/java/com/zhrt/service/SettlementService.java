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

import com.zhrt.dao.SettlementDao;
import com.zhrt.entity.Settlement;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SettlementService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SettlementService.class);

	@Autowired
	private SettlementDao settlementDao;

	@Transactional
	public Settlement getById(String id) {
		Settlement cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = settlementDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<Settlement> getList() {
		List<Settlement> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = settlementDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public Settlement getByChannel(String channelId) {
		Settlement settlement  = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("channelId", channelId);
			settlement = settlementDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return settlement;
	}
	
	@Transactional
	public List<Settlement> getList(Map<String, Object> paramMap) {
		List<Settlement> cpList = null;
		try {
			cpList = settlementDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = settlementDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(Settlement cpInfo) {
		try {
			settlementDao.add(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(Settlement cpInfo) {
		try {
			settlementDao.update(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			settlementDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			settlementDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Map<String, Object> getCpMap(){
		Map<String, Object> cpMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<Settlement> cpList = settlementDao.getList(param);
		for (Settlement cp : cpList) {
			cpMap.put(cp.getId(), cp);
		}
		return cpMap;
	}

	public List<Settlement> getForPage(Map paramMap) {
		List<Settlement> cpList = null;
		cpList = settlementDao.getForPage(paramMap);
		return cpList;
	}
}
