package com.zhrt.dao;

import java.util.List;
import java.util.Map;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.AppInfo;

@MyBatisRepository
public interface AppInfoDao {
	/**
	 * 添加信息
	 * @param parameters
	 * @return
	 */
	void add(Object parameters);
	
	/**
	 * 查询信息
	 * @param parameters
	 * @return
	 */
	AppInfo get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<AppInfo> getList(Object parameters);
	
	/**
	 * 查询集合总数
	 * @param parameters
	 * @return
	 */
	int getListCount(Object parameters);
	
	/**
	 * 修改信息
	 * @param parameters
	 */
	void update(Object obj);

	/**
	 * 删除信息
	 * @param parameters
	 */
	void delById(Object parameters);
	
	/**
	 * 批量删除数据
	 * @param parameters
	 */
	void delBatch(Object parameters);

	/**
	 * 
	 * 列表页查询产品信息，关联查询出道具数量
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月12日 上午11:44:22
	 * 修改人：
	 * 修改时间：
	 * @param paramMap
	 * @return
	 */
	List<AppInfo> getForPage(Map<String, Object> paramMap);
	
	/**
	 * 
	 * 查询渠道运营的产品
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月18日 下午1:37:14
	 * 修改人：
	 * 修改时间：
	 * @param channelId
	 * @return
	 */
	List<AppInfo> getByChannel(String channelId);
	
	/**
	 * 
	 * 创建产品用户分表
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月26日 下午5:53:32
	 * 修改人：
	 * 修改时间：
	 * @param obj
	 */
	void createAppUserTables(Object obj);
}
