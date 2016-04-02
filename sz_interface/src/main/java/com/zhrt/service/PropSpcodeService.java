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

import com.zhrt.dao.PropSpcodeDao;
import com.zhrt.entity.PropSpcode;
import com.zhrt.entity.Spcode;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PropSpcodeService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(PropSpcodeService.class);

	@Autowired
	private PropSpcodeDao propSpcodeDao;

	@Transactional
	public PropSpcode getById(String id) {
		PropSpcode cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = propSpcodeDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<PropSpcode> getList() {
		List<PropSpcode> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = propSpcodeDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<PropSpcode> getList(Map<String, Object> paramMap) {
		List<PropSpcode> cpList = null;
		try {
			cpList = propSpcodeDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = propSpcodeDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(PropSpcode cpInfo) {
		try {
			propSpcodeDao.add(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(PropSpcode cpInfo) {
		try {
			propSpcodeDao.update(cpInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			propSpcodeDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			propSpcodeDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Map<String, Object> getPropMap(){
		Map<String, Object> propMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<PropSpcode> propList = propSpcodeDao.getList(param);
		for (PropSpcode prop : propList) {
			propMap.put(prop.getId(), prop);
		}
		return propMap;
	}
	@Transactional
	public List<PropSpcode> getPropsByAppId(String appId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("appId", appId);
		List<PropSpcode> propList = propSpcodeDao.getPropsByAppId(param);
		return propList;
	}
	@Transactional
	List<Spcode> dynamicSql(String dynamicSql){
		Map<String, String> param = new HashMap<String, String>();
		param.put("dynamicSql", dynamicSql);
		return propSpcodeDao.dynamicSql(param);
	}
}
