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

import com.power.entity.PowerUser;
import com.system.util.date.DateUtil;
import com.zhrt.bo.DynamicGameConfig;
import com.zhrt.dao.AppInfoDao;
import com.zhrt.dao.AppVersionInfoDao;
import com.zhrt.dao.ChannelAppInfoDao;
import com.zhrt.dao.ChannelInfoDao;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.AppVersionInfo;
import com.zhrt.entity.ChannelAppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.Plat;
import com.zhrt.entity.SmsCenter;
import com.zhrt.entity.UrlCenter;
import com.zhrt.util.ApkTool;
import com.zhrt.util.Constant;
import com.zhrt.util.PackTools;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class AppVersionInfoService {
	private static Logger logger = LoggerFactory.getLogger(AppVersionInfoService.class);

	@Autowired
	private AppVersionInfoDao appVersionInfoDao;
	@Autowired
	private AppInfoDao appInfoDao;
	@Autowired
	private ChannelAppInfoDao channelAppInfoDao;
	@Autowired
	private SeqService seqService;
	@Autowired
	private ChannelInfoDao channelInfoDao;

	@Transactional
	public AppVersionInfo getById(String id) {
		AppVersionInfo avi = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			avi = appVersionInfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return avi;
	}

	@Transactional
	public List<AppVersionInfo> getList() {
		List<AppVersionInfo> aviList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			aviList = appVersionInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aviList;
	}

	@Transactional
	public List<AppVersionInfo> getList(Map<String, Object> paramMap) {
		List<AppVersionInfo> aviList = null;
		try {
			aviList = appVersionInfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aviList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = appVersionInfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(AppVersionInfo appVersionInfo) {
		try {
			appVersionInfoDao.add(appVersionInfo);
			Map<String, Object> appInfoMap = new HashMap<String, Object>();
			appInfoMap.put("id", appVersionInfo.getAppId());
			AppInfo appInfo = appInfoDao.get(appInfoMap);
			
			ChannelAppInfo channelAppInfo = new ChannelAppInfo();
			channelAppInfo.setPlatId(Constant.PLATID);			
			
			channelAppInfo.setAppName(appInfo.getAppName());
			channelAppInfo.setAppType(appInfo.getAppType());
			channelAppInfo.setCpId(appInfo.getCpId());
			channelAppInfo.setAppId(appInfo.getId());
			channelAppInfo.setChannelId(Constant.MAIN_CHANNELID);
			channelAppInfo.setMyepayChanAppId(appInfo.getMyepayChanAppId());
			channelAppInfo.setAppId(appInfo.getId());
			channelAppInfo.setMyepayAppId(appInfo.getMyepayAppId());
			channelAppInfo.setMyepaySdkVer(appInfo.getMyepaySdkVer());
			channelAppInfo.setVerNumber(appVersionInfo.getVerNumber());
			channelAppInfo.setVerId(appVersionInfo.getId());
			
			// 获取当前登录用户，修改关联
			Integer chanAppVerSeq = seqService.getNextByName("chanAppVerSeq");
			channelAppInfo.setChanAppVerSeq(chanAppVerSeq);
			channelAppInfo.setSdkVer(Constant.SDKVER);
			channelAppInfo.setMainFlag("1");
			channelAppInfo.setCreatePerson("admin");
			channelAppInfo.setCreateTime(DateUtil.getCurDate());
			channelAppInfo.setLastUpdatePerson("admin");
			channelAppInfo.setLastUpdateTime(DateUtil.getCurDate());
			channelAppInfoDao.add(channelAppInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(AppVersionInfo appVersionInfo) {
		appVersionInfoDao.update(appVersionInfo);
		if(String.valueOf(Constant.STATUS_INVALID).equals(appVersionInfo.getStatus())){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", Constant.STATUS_INVALID);
			map.put("verId", appVersionInfo.getId());
			channelAppInfoDao.updateByVerId(map);
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			appVersionInfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			appVersionInfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public List<AppVersionInfo> getByAppId(String appId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", appId);
//		map.put("status", Constant.STATUS_VALID);  
		List<AppVersionInfo> appVersionInfos = appVersionInfoDao.getList(map);
		return appVersionInfos;
	}
	
	@Transactional
	public Map<String, Object> getAppVersionInfoMap(){
		Map<String, Object> appVersionInfoMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<AppVersionInfo> appVersionInfoList = appVersionInfoDao.getList(param);
		for (AppVersionInfo appVersionInfo : appVersionInfoList) {
			appVersionInfoMap.put(appVersionInfo.getId(), appVersionInfo);
		}
		return appVersionInfoMap;
	}
	/**
	 * 
	 * 查询可添加的渠道运营产品
	 * 创建人：
	 * 创建时间: 2015年8月19日 下午7:13:12
	 * 修改人：
	 * 修改时间：
	 * @param param 渠道id
	 * @return
	 */
	@Transactional
	public List<AppVersionInfo> getParentCanAddChannelApp(Object param){
		List<AppVersionInfo> appList = new ArrayList<AppVersionInfo>();
		appList = appVersionInfoDao.getParentCanAddChannelApp(param);
		return appList;
	}

	/**
	 * 
	 * 查询可以添加的渠道运营产品数量
	 * 创建人：
	 * 创建时间: 2015年8月20日 上午9:17:16
	 * 修改人：
	 * 修改时间：
	 * @param paramMap
	 * @return
	 */
	@Transactional
	public int getParentCanAddChannelAppCount(Object paramMap) {
		int count = 0;
		count = appVersionInfoDao.getParentCanAddChannelAppCount(paramMap);
		return count;
	}
	/**
	 * 
	 * 查询子渠道可以添加的渠道运营产品
	 * 创建人：
	 * 创建时间: 2015年8月20日 上午11:24:04
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	@Transactional
	public List<AppVersionInfo> getChildCanAddChannelApp(Object param){
		List<AppVersionInfo> appVerList = new ArrayList<AppVersionInfo>();
		appVerList = appVersionInfoDao.getChildCanAddChannelApp(param);
		return appVerList;
	}
	/**
	 * 
	 * 查询子渠道可以添加的渠道运营产品
	 * 创建人：
	 * 创建时间: 2015年8月20日 上午11:26:46
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	@Transactional
	public int getChildCanAddChannelAppCount(Object param){
		int count = 0;
		count = appVersionInfoDao.getChildCanAddChannelAppCount(param);
		return count;
	}

	/**
	 * 
	 * 新增产品版本，同时与用户勾选的渠道绑定
	 * 创建人：
	 * 创建时间: 2015年9月7日 下午5:21:00
	 * 修改人：
	 * 修改时间：
	 * @param aviInfo
	 * @param channels
	 */
	@Transactional
	public void add(AppVersionInfo aviInfo, String[] channels,PowerUser user,Plat plat) {
		//保存产品版本信息
		appVersionInfoDao.add(aviInfo);
		//查询产品信息
		Map<String, Object> appInfoMap = new HashMap<String, Object>();
		appInfoMap.put("id", aviInfo.getAppId());
		AppInfo appInfo = appInfoDao.get(appInfoMap);

		// 获取默认计费方案和配置文件流
		// 同步产品数据

		try {
			//渠道信息
			ChannelInfo channelInfo = new ChannelInfo();
			//渠道产品信息
			ChannelAppInfo channelAppInfo = new ChannelAppInfo();
			
			channelAppInfo.setAppId(aviInfo.getAppId());
			channelAppInfo.setAppName(appInfo.getAppName());
			channelAppInfo.setAppType(appInfo.getAppType());
			channelAppInfo.setCpId(appInfo.getCpId());
			channelAppInfo.setCreatePerson(user.getLoginName());
			channelAppInfo.setCreateTime(DateUtil.getCurDate());
			channelAppInfo.setLastUpdatePerson(user.getLoginName());
			channelAppInfo.setLastUpdateTime(DateUtil.getCurDate());
			channelAppInfo.setMyepayAppId(appInfo.getMyepayAppId());
			channelAppInfo.setMyepayChanAppId(appInfo.getMyepayChanAppId());
			channelAppInfo.setMyepaySdkVer(appInfo.getMyepaySdkVer());
			channelAppInfo.setPlatId(user.getPlatId()+"");
			channelAppInfo.setSdkVer(aviInfo.getSdkVer());
			channelAppInfo.setStatus("1");
			channelAppInfo.setVerId(aviInfo.getId());
			channelAppInfo.setVerNumber(aviInfo.getVerNumber());
			for (String channelId : channels) {
				//查询渠道信息
				Map<String, Object> chanMap = new HashMap<String, Object>();
				chanMap.put("id", channelId);
				channelInfo = channelInfoDao.get(chanMap);
				
				Integer chanAppVerSeq = seqService.getNextByName("chanAppVerSeq");
				
				channelAppInfo.setChanAppVerSeq(chanAppVerSeq);
				channelAppInfo.setChannelId(channelId);
				channelAppInfo.setMyepayChannelId(channelInfo.getChannelId());
				channelAppInfo.setMyepayChannelCode(channelInfo.getChannelCode());
				channelAppInfo.setMainFlag(channelInfo.getMainFlag());
				//保存渠道产品信息
				channelAppInfoDao.add(channelAppInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}
}
