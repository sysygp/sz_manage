package com.zhrt.service;

import java.util.ArrayList;
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
import com.zhrt.dao.ChannelInfoDao;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class ChannelInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ChannelInfoService.class);

	@Autowired
	private ChannelInfoDao channelInfoDao;
	@Autowired
	private ChannelAppInfoDao channelAppInfoDao;

	@Transactional
	public ChannelInfo getById(String id) {
		ChannelInfo cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = channelInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<ChannelInfo> getList() {
		List<ChannelInfo> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = channelInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<ChannelInfo> getList(Map<String, Object> paramMap) {
		List<ChannelInfo> cpList = null;
		try {
			cpList = channelInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = channelInfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(ChannelInfo channelInfo) {
		try {
			channelInfoDao.add(channelInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(ChannelInfo channelInfo) {
		channelInfoDao.update(channelInfo);
		//如果渠道下线时，则修改渠道下的所有产品为下线状态
		if(String.valueOf(Constant.STATUS_INVALID).equals(channelInfo.getStatus())){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("channelId", channelInfo.getId());
			param.put("status", Constant.STATUS_INVALID);
			channelAppInfoDao.updateByChannelId(param);
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			channelInfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			channelInfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public ChannelInfo getByChannelId(String channelId) {
		ChannelInfo channelInfo = new ChannelInfo();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("channelId", channelId);
			channelInfo = channelInfoDao.get(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channelInfo;
	}
	
	@Transactional
	public Map<String, Object> getChannelMap(){
		Map<String, Object> channelMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<ChannelInfo> channelList = channelInfoDao.getList(param);
		for (ChannelInfo channel : channelList) {
			channelMap.put(channel.getId(), channel);
		}
		return channelMap;
	}

	/**
	 * 
	 * 查询运营产品的渠道
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月26日 上午11:01:42
	 * 修改人：
	 * 修改时间：
	 * @param appId
	 * @return
	 */
	@Transactional
	public List<ChannelInfo> getByChannelApp(String appId) {
		List<ChannelInfo> channelList = new ArrayList<ChannelInfo>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appId", appId);
		param.put("status", Constant.STATUS_VALID);
		channelList = channelInfoDao.getByChannelApp(param);
		return channelList;
	}
	
	@Transactional
	public List<ChannelInfo> getForPage(Object param){
		List<ChannelInfo> channelList = new ArrayList<ChannelInfo>();
		channelList = channelInfoDao.getForPage(param);
		return channelList;
	}

	/**
	 * 
	 * 查询推广某个产品版本的渠道
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月28日 下午7:44:15
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	@Transactional
	public List<ChannelInfo> getByVerId(Map<String, Object> param) {
		List<ChannelInfo> channelList = new ArrayList<ChannelInfo>();
		channelList = channelInfoDao.getByVerId(param);
		return channelList;
	}
}
 