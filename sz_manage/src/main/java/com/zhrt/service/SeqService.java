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

import com.zhrt.dao.SeqDao;
import com.zhrt.entity.Seq;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SeqService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SeqService.class);

	@Autowired
	private SeqDao seqDao;

	@Transactional
	public Integer getNextByName(String name) {
		Integer i = new Integer(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		i = seqDao.getNextByName(map);
		return i;
	}

	@Transactional
	public List<Seq> getList() {
		List<Seq> cpList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		cpList = seqDao.getList(paramMap);
		return cpList;
	}

	@Transactional
	public List<Seq> getList(Map<String, Object> paramMap) {
		List<Seq> cpList = null;
		cpList = seqDao.getList(paramMap);
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		listCount = seqDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(Seq seq) {
		seqDao.add(seq);
	}

	@Transactional
	public void update(Seq seq) {
		seqDao.update(seq);
	}
	/*
	 * @Transactional public void delByIds(Map<String, Object> idsMap) { try {
	 * seqDao.delBatch(idsMap); } catch (Exception e) { e.printStackTrace(); } }
	 */

}
