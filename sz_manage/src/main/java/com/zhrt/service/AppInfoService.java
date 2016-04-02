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

import com.system.util.datasource.DynamicDataSource;
import com.zhrt.dao.AppInfoDao;
import com.zhrt.dao.AppVersionInfoDao;
import com.zhrt.dao.ChannelAppInfoDao;
import com.zhrt.dao.CommonDao;
import com.zhrt.entity.AppInfo;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class AppInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AppInfoService.class);

	@Autowired
	private AppInfoDao appInfoDao;
	@Autowired
	private AppVersionInfoDao appVersionInfoDao;
	@Autowired
	private ChannelAppInfoDao channelAppInfoDao;
	@Autowired
	private CommonDao commonDao;

	@Transactional
	public AppInfo getById(String id) {
		AppInfo cp = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			cp = appInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cp;
	}

	@Transactional
	public List<AppInfo> getList() {
		List<AppInfo> cpList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			cpList = appInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public List<AppInfo> getList(Map<String, Object> paramMap) {
		List<AppInfo> cpList = null;
		try {
			cpList = appInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cpList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = appInfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(AppInfo appInfo,Integer platid) {	
		appInfoDao.add(appInfo);
		String appSeq = appInfo.getAppSeq()+"";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("platid", platid);
		map.put("appseq", appSeq);
		
//		DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATA_SOURCE_USERSYSQUERY);
	}

	@Transactional
	public void update(AppInfo appInfo) {
		appInfoDao.update(appInfo);
		//将产品下线时，同时产品的版本和渠道产品也处于下线状态
		if(String.valueOf(Constant.STATUS_INVALID).equals(appInfo.getStatus())){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("appId", appInfo.getId());
			param.put("status", Constant.STATUS_INVALID);
			appVersionInfoDao.updateByAppId(param);
			channelAppInfoDao.updateByAppId(param);
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			appInfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			appInfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public AppInfo getByMyepayAppId(String appId) {
		AppInfo appInfo = new AppInfo();
		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			parameters.put("myepayAppId", appId);
			appInfo = appInfoDao.get(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appInfo;
	}

	/**
	 * 
	 * 查询产品名称和id的集合
	 * 创建人：
	 * 创建时间: 2015年8月18日 下午1:40:38
	 * 修改人：
	 * 修改时间：
	 * @return
	 */
	@Transactional
	public Map<String, Object> getAppMap() {
		Map<String, Object> appMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<AppInfo> appList = appInfoDao.getList(param);
		for (AppInfo app : appList) {
			appMap.put(app.getId(), app);
		}
		return appMap;
	}

	//appId字段已重命名，方法无效
/*	@Transactional
	public AppInfo getByAppId(String appId) {
		AppInfo appInfo = new AppInfo();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("appId", appId);
		appInfo = appInfoDao.get(parameters);
		return appInfo;
	}*/

	/**
	 * 
	 * 列表页查询
	 * 创建人：
	 * 创建时间: 2015年8月18日 下午1:40:09
	 * 修改人：
	 * 修改时间：
	 * @param paramMap
	 * @return
	 */
	@Transactional
	public List<AppInfo> getForPage(Map<String, Object> paramMap) {
		List<AppInfo> appList = new ArrayList<AppInfo>();
		appList = appInfoDao.getForPage(paramMap);
		return appList;
	}
	
	/**
	 * 
	 * 查询渠道运营的产品
	 * 创建人：
	 * 创建时间: 2015年8月18日 上午11:55:01
	 * 修改人：
	 * 修改时间：
	 * @param channelId
	 * @return
	 */
	@Transactional
	public List<AppInfo> getByChannel(String channelId){
		List<AppInfo> appList = new ArrayList<AppInfo>();
		appList = appInfoDao.getByChannel(channelId);
		return appList;
	}
	
}
