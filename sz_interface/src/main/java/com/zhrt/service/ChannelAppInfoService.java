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

import com.zhrt.dao.ChannelAppInfoDao;
import com.zhrt.entity.ChannelAppInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class ChannelAppInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ChannelAppInfoService.class);

	@Autowired
	private ChannelAppInfoDao channelAppInfoDao;

	@Transactional
	public ChannelAppInfo getById(String id) {
		ChannelAppInfo entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			entity = channelAppInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Transactional
	public ChannelAppInfo getByChanAppVerSeq(Integer chanAppVerSeq) {
		ChannelAppInfo entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("chanAppVerSeq", chanAppVerSeq);
			entity = channelAppInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Transactional
	public ChannelAppInfo get(Map<String, Object> paramMap) {
		ChannelAppInfo entity = null;
		try {
			entity = channelAppInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Transactional
	public List<ChannelAppInfo> getList() {
		List<ChannelAppInfo> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = channelAppInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<ChannelAppInfo> getList(Map<String, Object> paramMap) {
		List<ChannelAppInfo> cpList = null;
		try {
			cpList = channelAppInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = channelAppInfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(ChannelAppInfo entity) {
		try {
			channelAppInfoDao.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(ChannelAppInfo entity) {
		try {
			channelAppInfoDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			channelAppInfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			channelAppInfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Map<String, Object> getChannelAppInfoMap(){
		Map<String, Object> channelAppInfoMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<ChannelAppInfo> channelAppInfoList = channelAppInfoDao.getList(param);
		for (ChannelAppInfo channelAppInfo : channelAppInfoList) {
			channelAppInfoMap.put(channelAppInfo.getId(), channelAppInfo);
		}
		return channelAppInfoMap;
	}
	/**
	 * 
	 * 批量更新渠道运营产品状态
	 * 创建人：朱士竹
	 * 创建时间: 2015年9月11日 下午5:15:26
	 * 修改人：
	 * 修改时间：
	 */
	@Transactional
	public void updateBatch(Map<String, Object> map){
		channelAppInfoDao.updateBatch(map);
	}
}
