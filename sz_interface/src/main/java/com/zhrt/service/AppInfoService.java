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
		appInfoDao.createAppUserTables(map);
		
//		DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATA_SOURCE_USERSYSQUERY);
		createTrigger(platid,appSeq);
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
	 * 创建人：朱士竹
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
	 * 创建人：朱士竹
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
	 * 创建人：朱士竹
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
	
	/**
	 * 
	 * 创建触发器
	 * 创建人：朱士竹
	 * 创建时间: 2015年9月10日 上午11:59:59
	 * 修改人：
	 * 修改时间：
	 * @param platid
	 * @param appSeq
	 */
	public void createTrigger(Integer platid,String appSeq){
		Map<String, Object> commonMap = new HashMap<String, Object>();
		
		//账户总表Insert触发器SQL  account_9_total
		String accountTotalInsertTrigger = "CREATE TRIGGER `trInsert_account_app_"+platid+"_"+appSeq+"` AFTER INSERT ON `account_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN insert into account_app_"+platid+"_total "
				+ "(id,userid,platid,accountId,accountPwd,accountPwdLevel,accountMoney,accountCore,payPwd,phone,payPwdLimit,accountStatus,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,createTime,updateTime,virtualCurrency,tokenVaildity) "
				+ "values(new.id,new.userid,new.platid,new.accountId,new.accountPwd,new.accountPwdLevel,new.accountMoney,new.accountCore,new.payPwd,new.phone,new.payPwdLimit,new.accountStatus,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.createTime,new.updateTime,new.virtualCurrency,new.tokenValidity);END;";
		commonMap.put("sql", accountTotalInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户总表Update触发器SQL  account_9_total
		String accountTotalUpdateTrigger = "CREATE TRIGGER `trUpdate_account_app_"+platid+"_"+appSeq+"` AFTER UPDATE ON `account_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN UPDATE account_app_"+platid+"_total "
				+ "SET id=new.id,userid=new.userid,platid=new.platid,accountId=new.accountId,accountPwd=new.accountPwd,accountPwdLevel=new.accountPwdLevel,accountMoney=new.accountMoney,accountCore=new.accountCore,payPwd=new.payPwd,phone=new.phone,payPwdLimit=new.payPwdLimit,accountStatus=new.accountStatus,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,createTime=new.createTime,updateTime=new.updateTime,virtualCurrency=new.virtualCurrency,tokenValidity=new.tokenValidity where id=old.id;END;";
		commonMap.put("sql", accountTotalUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户总表Delete触发器SQL
		String accountTotalDeleteTrigger = "CREATE TRIGGER `trDelete_account_app_"+platid+"_"+appSeq+"` AFTER DELETE ON `account_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN DELETE FROM  account_app_"+platid+"_total where id=old.id;END;";
		commonMap.put("sql", accountTotalDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户充值记录表Insert触发器SQL accountrecharge
		String accountEchargeInsertTrigger = "CREATE TRIGGER `trInsert_accountrecharge_app_"+platid+"_"+appSeq+"` AFTER INSERT ON `accountrecharge_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN insert into accountrecharge_app_"+platid+"_total " 
				+"(id,userid,platid,accountId,rechargeMoney,rechargeType,phone,orderid,cpOrderid,chanAppVerSeq,cpId,appId,appVerId,spareMoney,sdkVer,channelId,chargeTime,createTime,updateTime,indexId,result,virtualCurrency) "
				+ "values (new.id,new.userid,new.platid,new.accountId,new.rechargeMoney,new.rechargeType,new.phone,new.orderid,new.cpOrderid,new.chanAppVerSeq,new.cpId,new.appId,new.appVerId,new.spareMoney,new.sdkVer,new.channelId,new.chargeTime,new.createTime,new.updateTime,new.indexId,new.result,new.virtualCurrency);END;";
        commonMap.put("sql", accountEchargeInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
				
		//账户充值记录表Update触发器SQL  accountrecharge
		String accountEchargeUpdateTrigger = "CREATE TRIGGER `trUpdate_accountrecharge_app_"+platid+"_"+appSeq+"` AFTER UPDATE ON `accountrecharge_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN UPDATE accountrecharge_app_"+platid+"_total "
				+ "SET id=new.id,userid=new.userid,platid=new.platid,accountId=new.accountId,rechargeMoney=new.rechargeMoney,rechargeType=new.rechargeType,phone=new.phone,orderid=new.orderid,cpOrderid=new.cpOrderid,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,appVerId=new.appVerId,spareMoney=new.spareMoney,sdkVer=new.sdkVer,channelId=new.channelId,chargeTime=new.chargeTime,createTime=new.createTime,updateTime=new.updateTime,indexId=new.indexId,result=new.result,virtualCurrency=new.virtualCurrency where id=old.id;END;";
		commonMap.put("sql", accountEchargeUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户充值表Delete触发器SQL
		String accountEchargeDeleteTrigger = "CREATE TRIGGER `trDelete_accountrecharge_app_"+platid+"_"+appSeq+"` AFTER DELETE ON `accountrecharge_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN DELETE FROM accountrecharge_app_"+platid+"_total where id=old.id;END;";
		commonMap.put("sql", accountEchargeDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//计费流水表Insert触发器SQL
		String billingInsertTrigger = "CREATE TRIGGER `trInsert_user_billing_app_"+platid+"_"+appSeq+"` "
				+ "AFTER INSERT ON `user_billing_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN "
				+ "insert into user_billing_app_"+platid+"_total (id,userId,platId,channelId,extChannelId,cpId,extCpId,appId,extAppId,chanAppId,extChanAppId,propId,extPropId,phone,province,city,serialNumber,sdkVer,money,indexId,result,resultCode,chargeTime,createTime,updateTime,chanAppVerSeq,verId,paymentType,virtualCurrency) values (new.id,new.userId,new.platId,new.channelId,new.extChannelId,new.cpId,new.extCpId,new.appId,new.extAppId,new.chanAppId,new.extChanAppId,new.propId,new.extPropId,new.phone,new.province,new.city,new.serialNumber,new.sdkVer,new.money,new.indexId,new.result,new.resultCode,new.chargeTime,new.createTime,new.updateTime,new.chanAppVerSeq,new.verId,new.paymentType,new.virtualCurrency);"
				+ "END;";
		commonMap.put("sql", billingInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//计费流水表Update触发器SQL
		String billingUpdateTrigger = "CREATE TRIGGER `trUpdate_user_billing_app_"+platid+"_"+appSeq+"` "
				+ "AFTER UPDATE ON `user_billing_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN "
				+ "UPDATE user_billing_app_"+platid+"_total SET id=new.id,userId=new.userId,platId=new.platId,channelId=new.channelId,extChannelId=new.extChannelId,cpId=new.cpId,extCpId=new.extCpId,appId=new.appId,extAppId=new.extAppId,chanAppId=new.chanAppId,extChanAppId=new.extChanAppId,propId=new.propId,extPropId=new.extPropId,phone=new.phone,province=new.province,city=new.city,serialNumber=new.serialNumber,sdkVer=new.sdkVer,money=new.money,indexId=new.indexId,result=new.result,resultCode=new.resultCode,chargeTime=new.chargeTime,createTime=new.createTime,updateTime=new.updateTime,chanAppVerSeq=new.chanAppVerSeq,verId=new.verId,paymentType=new.paymentType,virtualCurrency=new.virtualCurrency where id=old.id;"
				+ "END;";
		commonMap.put("sql", billingUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//计费流水表Delete触发器SQL
		String billingDeleteTrigger = "CREATE TRIGGER `trDelete_user_billing_app_"+platid+"_"+appSeq+"` AFTER DELETE ON `user_billing_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN DELETE FROM user_billing_app_"+platid+"_total where id=old.id;END;";
		commonMap.put("sql", billingDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户消费表Insert触发器SQL
		String accCostInsertTrigger = "CREATE TRIGGER `trInsert_accountcost_app_"+platid+"_"+appSeq+"` "
				+ "AFTER INSERT ON `accountcost_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN "
				+ "insert into accountcost_app_"+platid+"_total (id,userid,accountId,platid,costMoney,phone,costType,createTime,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,spareMoney,indexId,result,virtualCurrency) values (new.id,new.userid,new.accountId,new.platid,new.costMoney,new.phone,new.costType,new.createTime,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.spareMoney,new.indexId,new.result,new.virtualCurrency);"
				+ "END;";
		commonMap.put("sql", accCostInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户消费表Update触发器SQL
		String accCostUpdateTrigger = "CREATE TRIGGER `trUpdate_accountcost_app_"+platid+"_"+appSeq+"` "
				+ "AFTER UPDATE ON `accountcost_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN "
				+ "UPDATE accountcost_app_"+platid+"_total SET id=new.id,userid=new.userid,accountId=new.accountId,platid=new.platid,costMoney=new.costMoney,phone=new.phone,costType=new.costType,createTime=new.createTime,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,spareMoney=new.spareMoney,indexId=new.indexId,result=new.result,virtualCurrency=new.virtualCurrency where id=old.id;"
				+ "END;";
		commonMap.put("sql", accCostUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//账户消费表Delete触发器SQL
		String accCostDeleteTrigger = "CREATE TRIGGER `trDelete_accountcost_app_"+platid+"_"+appSeq+"` AFTER DELETE ON `accountcost_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN DELETE FROM accountcost_app_"+platid+"_total where id=old.id;END;";
		commonMap.put("sql", accCostDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");

		//无IMEI游客用户基本信息表Insert触发器SQL
		String nlBasicInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_n_l_basic` "
				+ "AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_n_l_basic` FOR EACH ROW BEGIN "
				+ "insert into user_app_"+platid+"_total_n_l_basic (id,userid,imei,platid,nickname,createTime) values (new.id,new.userid,new.imei,new.platid,new.nickname,new.createTime);"
				+ "END;";
		commonMap.put("sql", nlBasicInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");

		//无IMEI游客用户基本信息表Update触发器SQL
		String nlBasicUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_n_l_basic` "
				+ "AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_n_l_basic` FOR EACH ROW BEGIN "
				+ "UPDATE user_app_"+platid+"_total_n_l_basic SET id=new.id,userid=new.userid,imei=new.imei,platid=new.platid,nickname=new.nickname,createTime=new.createTime where id=old.id;"
				+ "END;";
		commonMap.put("sql", nlBasicUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//无IMEI游客用户基本信息表Delete触发器SQL
		String nlBasicDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_n_l_basic` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_n_l_basic` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_n_l_basic where id=old.id;END;";
		commonMap.put("sql", nlBasicDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");

		//无IMEI游客用户扩展信息表Insert触发器SQL
		String nlExtendInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_n_l_extend` "
				+ "AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_n_l_extend` FOR EACH ROW BEGIN "
				+ "insert into user_app_"+platid+"_total_n_l_extend (id,userid,platid,imei,imsi,mac,termType,termModel,termOs,longitude,latitude,createTime,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,lastLogTime) values (new.id,new.userid,new.platid,new.imei,new.imsi,new.mac,new.termType,new.termModel,new.termOs,new.longitude,new.latitude,new.createTime,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.lastLogTime);"
				+ "END;";
		commonMap.put("sql", nlExtendInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
				
		
		//无IMEI游客用户扩展信息表Update触发器SQL
		String nlExtendUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_n_l_extend` AFTER "
				+ "UPDATE ON `user_app_"+platid+"_"+appSeq+"_n_l_extend` FOR EACH ROW BEGIN "
				+ "UPDATE user_app_"+platid+"_total_n_l_extend SET id=new.id,userid=new.userid,platid=new.platid,imei=new.imei,imsi=new.imsi,mac=new.mac,termType=new.termType,termModel=new.termModel,termOs=new.termOs,longitude=new.longitude,latitude=new.latitude,createTime=new.createTime,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,lastLogTime=new.lastLogTime where id=old.id;"
				+ "END;";
		commonMap.put("sql", nlExtendUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//无IMEI游客用户扩展信息表Delete触发器SQL
		String nlExtendDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_n_l_extend` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_n_l_extend` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_n_l_extend where id=old.id;END;";
		commonMap.put("sql", nlExtendDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//无IMEI游客用户历史信息表Insert触发器SQL
		String nlHistoryInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_n_l_history` AFTER "
				+ "INSERT ON `user_app_"+platid+"_"+appSeq+"_n_l_history` FOR EACH ROW BEGIN "
				+ "insert into user_app_"+platid+"_total_n_l_history (id,userid,platid,imei,imsi,mac,termType,termModel,termOs,longitude,latitude,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,behType,createTime,exitTime) values (new.id,new.userid,new.platid,new.imei,new.imsi,new.mac,new.termType,new.termModel,new.termOs,new.longitude,new.latitude,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.behType,new.createTime,new.exitTime);"
				+ "END;";
		commonMap.put("sql", nlHistoryInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//无IMEI游客用户历史信息表Update触发器SQL
		String nlHistoryUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_n_l_history` AFTER "
				+ "UPDATE ON `user_app_"+platid+"_"+appSeq+"_n_l_history` FOR EACH ROW BEGIN "
				+ "UPDATE user_app_"+platid+"_total_n_l_history SET id=new.id,userid=new.userid,platid=new.platid,imei=new.imei,imsi=new.imsi,mac=new.mac,termType=new.termType,termModel=new.termModel,termOs=new.termOs,longitude=new.longitude,latitude=new.latitude,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,behType=new.behType,createTime=new.createTime,exitTime=new.exitTime where id=old.id;"
				+ "END;";
		commonMap.put("sql", nlHistoryUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//无IMEI游客用户历史信息表Delete触发器SQL
		String nlHistoryDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_n_l_history` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_n_l_history` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_n_l_history where id=old.id;END;";
		commonMap.put("sql", nlHistoryDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户基本信息表Insert触发器SQL
		String nyBasicInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_n_y_basic` "
				+ "AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_n_y_basic` FOR EACH ROW BEGIN "
				+ "insert into user_app_"+platid+"_total_n_y_basic (id,userid,imei,platid,nickname,createTime) values (new.id,new.userid,new.imei,new.platid,new.nickname,new.createTime);"
				+ "END;";
		commonMap.put("sql", nyBasicInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户基本信息表Update触发器SQL
		String nyBasicUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_n_y_basic` "
				+ "AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_n_y_basic` FOR EACH ROW BEGIN "
				+ "UPDATE user_app_"+platid+"_total_n_y_basic SET id=new.id,userid=new.userid,imei=new.imei,platid=new.platid,nickname=new.nickname,createTime=new.createTime where id=old.id;END;";
		commonMap.put("sql", nyBasicUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户基本信息表Delete触发器SQL
		String nyBasicDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_n_y_basic` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_n_y_basic` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_n_y_basic where id=old.id;END;";
		commonMap.put("sql", nyBasicDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户扩展信息表Insert触发器SQL
		String nyExtendInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_n_y_extend` "
				+ "AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_n_y_extend` FOR EACH ROW BEGIN "
				+ "insert into user_app_"+platid+"_total_n_y_extend (id,userid,platid,imei,imsi,mac,termType,termModel,termOs,longitude,latitude,createTime,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,lastLogTime) values (new.id,new.userid,new.platid,new.imei,new.imsi,new.mac,new.termType,new.termModel,new.termOs,new.longitude,new.latitude,new.createTime,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.lastLogTime);"
				+ "END;";
		commonMap.put("sql", nyExtendInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户扩展信息表Update触发器SQL
		String nyExtendUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_n_y_extend` "
				+ "AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_n_y_extend` FOR EACH ROW BEGIN "
				+ "UPDATE user_app_"+platid+"_total_n_y_extend SET id=new.id,userid=new.userid,platid=new.platid,imei=new.imei,imsi=new.imsi,mac=new.mac,termType=new.termType,termModel=new.termModel,termOs=new.termOs,longitude=new.longitude,latitude=new.latitude,createTime=new.createTime,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,lastLogTime=new.lastLogTime where id=old.id;"
				+ "END;";
		commonMap.put("sql", nyExtendUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户扩展信息表Delete触发器SQL
		String nyExtendDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_n_y_extend` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_n_y_extend` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_n_y_extend where id=old.id;END;";
		commonMap.put("sql", nyExtendDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户历史信息表Insert触发器SQL
		String nyHistoryInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_n_y_history` "
				+ "AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_n_y_history` FOR EACH ROW BEGIN "
				+ "insert into user_app_"+platid+"_total_n_y_history (id,userid,platid,imei,imsi,mac,termType,termModel,termOs,longitude,latitude,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,behType,createTime,exitTime) values (new.id,new.userid,new.platid,new.imei,new.imsi,new.mac,new.termType,new.termModel,new.termOs,new.longitude,new.latitude,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.behType,new.createTime,new.exitTime);"
				+ "END;";
		commonMap.put("sql", nyHistoryInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户历史信息表Update触发器SQL
		String nyHistoryUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_n_y_history` "
				+ "AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_n_y_history` FOR EACH ROW BEGIN "
				+ "UPDATE user_app_"+platid+"_total_n_y_history SET id=new.id,userid=new.userid,platid=new.platid,imei=new.imei,imsi=new.imsi,mac=new.mac,termType=new.termType,termModel=new.termModel,termOs=new.termOs,longitude=new.longitude,latitude=new.latitude,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,behType=new.behType,createTime=new.createTime,exitTime=new.exitTime where id=old.id;"
				+ "END;";
		commonMap.put("sql", nyHistoryUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//有IMEI游客用户历史信息表Delete触发器SQL
		String nyHistoryDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_n_y_history` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_n_y_history` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_n_y_history where id=old.id;END;";
		commonMap.put("sql", nyHistoryDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户基本表Insert触发器SQL user_z_basic
		String zlBasicInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_z_basic` AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_z_basic` FOR EACH ROW BEGIN insert into user_app_"+platid+"_total_z_basic "
				+ "(id,userid,accountid,platid,platname,userpwd,phone,province,provinceName,nickname,photo,birthday,sex,email,createTime,wxAccessToken,wxExpires,wxExpireTime,wxRefreshToken,wxOpenid,wxScope,wxUnionid,accountType) "
				+ "values (new.id,new.userid,new.accountid,new.platid,new.platname,new.userpwd,new.phone,new.province,new.provinceName,new.nickname,new.photo,new.birthday,new.sex,new.email,new.createTime,new.wxAccessToken,new.wxExpires,new.wxExpireTime,new.wxRefreshToken,new.wxOpenid,new.wxScope,new.wxUnionid,new.accountType);END;";
		commonMap.put("sql", zlBasicInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户基本表Update触发器SQL user_z_basic
		String zlBasicUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_z_basic` AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_z_basic` FOR EACH ROW BEGIN UPDATE user_app_"+platid+"_total_z_basic "
			    + "SET id=new.id,userid=new.userid,accountid=new.accountid,platid=new.platid,platname=new.platname,userpwd=new.userpwd,phone=new.phone,province=new.province,provinceName=new.provinceName,nickname=new.nickname,photo=new.photo,birthday=new.birthday,sex=new.sex,email=new.email,createTime=new.createTime,wxAccessToken=new.wxAccessToken,wxExpires=new.wxExpires,wxExpireTime=new.wxExpireTime,wxRefreshToken=new.wxRefreshToken,wxOpenid=new.wxOpenid,wxScope=new.wxScope,wxUnionid=new.wxUnionid,accountType=new.accountType where id=old.id;END;";
		commonMap.put("sql", zlBasicUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户基本表Delete触发器SQL
		String zBasicDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_z_basic` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_z_basic` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_z_basic where id=old.id;END;";
		commonMap.put("sql", zBasicDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");

		//注册的用户扩展表Insert触发器SQL user_z_extend
		String zlExtendInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_z_extend` AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_z_extend` FOR EACH ROW BEGIN insert into user_app_"+platid+"_total_z_extend "
				+ "(id,userid,accountid,platid,phone,province,provinceName,imei,imsi,mac,termType,termModel,termOs,longitude,latitude,createTime,chanAppVerSeq,cpId,appId,verId,sdkVer,channelId,lastLogTime) "
				+ "values(new.id,new.userid,new.accountid,new.platid,new.phone,new.province,new.provinceName,new.imei,new.imsi,new.mac,new.termType,new.termModel,new.termOs,new.longitude,new.latitude,new.createTime,new.chanAppVerSeq,new.cpId,new.appId,new.verId,new.sdkVer,new.channelId,new.lastLogTime);END;";
		commonMap.put("sql", zlExtendInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户扩展表Update触发器SQL user_z_extend
		String zlExtendUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_z_extend` AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_z_extend` FOR EACH ROW BEGIN UPDATE user_app_"+platid+"_total_z_extend "	    
				+ "SET id=new.id,userid=new.userid,accountid=new.accountid,platid=new.platid,phone=new.phone,province=new.province,provinceName=new.provinceName,imei=new.imei,imsi=new.imsi,mac=new.mac,termType=new.termType,termModel=new.termModel,termOs=new.termOs,longitude=new.longitude,latitude=new.latitude,createTime=new.createTime,chanAppVerSeq=new.chanAppVerSeq,cpId=new.cpId,appId=new.appId,verId=new.verId,sdkVer=new.sdkVer,channelId=new.channelId,lastLogTime=new.lastLogTime where id=old.id;END;";
		commonMap.put("sql", zlExtendUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户扩展表Delete触发器SQL
		String zExtendDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_z_extend` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_z_extend` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_z_extend where id=old.id;END;";
		commonMap.put("sql", zExtendDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户历史表Insert触发器SQL user_z_history
		String zlHistoryInsertTrigger = "CREATE TRIGGER `trInsert_user_app_"+platid+"_"+appSeq+"_z_history` AFTER INSERT ON `user_app_"+platid+"_"+appSeq+"_z_history` FOR EACH ROW BEGIN insert into user_app_"+platid+"_total_z_history"
				+ "(id,userid,accountid,platid,platname,updSeq,updFiled,updBefore,updAfter,updTime) "
				+ "values(new.id,new.userid,new.accountid,new.platid,new.platname,new.updSeq,new.updFiled,new.updBefore,new.updAfter,new.updTime);END;";
		commonMap.put("sql", zlHistoryInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		 
		//注册的用户历史表Update触发器SQL user_z_history
		String zlHistoryUpdateTrigger = "CREATE TRIGGER `trUpdate_user_app_"+platid+"_"+appSeq+"_z_history` AFTER UPDATE ON `user_app_"+platid+"_"+appSeq+"_z_history` FOR EACH ROW BEGIN UPDATE user_app_"+platid+"_total_z_l_history "	    
				+ "SET id=new.id,userid=new.userid,accountid=new.accountid,platid=new.platid,platname=new.platname,updSeq=new.updSeq,updFiled=new.updFiled,updBefore=new.updBefore,updAfter=new.updAfter,updTime=new.updTime where id=old.id;END;";
		commonMap.put("sql", zlHistoryUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//注册的用户历史表Delete触发器SQL
		String zHistoryDeleteTrigger = "CREATE TRIGGER `trDelete_user_app_"+platid+"_"+appSeq+"_z_history` AFTER DELETE ON `user_app_"+platid+"_"+appSeq+"_z_history` FOR EACH ROW BEGIN DELETE FROM user_app_"+platid+"_total_z_history where id=old.id;END;";
		commonMap.put("sql", zHistoryDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//用户签到信息表Insert触发器SQL
		String signLogInsertTrigger = "CREATE TRIGGER `trInsert_signlog_app_"+platid+"_"+appSeq+"` "
				+ "AFTER INSERT ON `signlog_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN "
				+ "insert into signlog_app_"+platid+"_total (id,appId,appVerId,userId,channelId,signDate,fillSignFlag) values (new.id,new.appId,new.appVerId,new.userId,new.channelId,new.signDate,new.fillSignFlag);"
				+ "END;";
		commonMap.put("sql", signLogInsertTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");

		//用户签到信息表Update触发器SQL
		String signLogUpdateTrigger = "CREATE TRIGGER `trUpdate_signlog_app_"+platid+"_"+appSeq+"` "
				+ "AFTER UPDATE ON `signlog_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN "
				+ "UPDATE signlog_app_"+platid+"_total SET id=new.id,appId=new.appId,appVerId=new.appVerId,userId=new.userId,channelId=new.channelId,signDate=new.signDate,fillSignFlag=new.fillSignFlag where id=old.id;"
				+ "END;";
		commonMap.put("sql", signLogUpdateTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
		
		//用户签到信息表Delete触发器SQL
		String signLogDeleteTrigger = "CREATE TRIGGER `trDelete_signlog_app_"+platid+"_"+appSeq+"` AFTER DELETE ON `signlog_app_"+platid+"_"+appSeq+"` FOR EACH ROW BEGIN DELETE FROM signlog_app_"+platid+"_total where id=old.id;END;";
		commonMap.put("sql", signLogDeleteTrigger);
		commonDao.addPartTable(commonMap);
		commonMap.remove("sql");
	}
}
