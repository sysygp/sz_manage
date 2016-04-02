package com.zhrt.dao;

import java.util.List;
import com.system.dao.MyBatisRepository;
import com.zhrt.entity.AppVersionInfo;

@MyBatisRepository
public interface AppVersionInfoDao {
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
	AppVersionInfo get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<AppVersionInfo> getList(Object parameters);
	
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
	 * 查询父渠道可以添加的渠道运营产品
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月20日 上午9:20:19
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	List<AppVersionInfo> getParentCanAddChannelApp(Object param);


	/**
	 * 
	 * 查询父渠道可以添加的渠道运营产品数量
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月20日 上午9:20:45
	 * 修改人：
	 * 修改时间：
	 * @param paramMap
	 * @return
	 */
	int getParentCanAddChannelAppCount(Object param);
	
	/**
	 * 
	 * 查询子渠道可添加的渠道运营产品
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月20日 上午11:21:05
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	List<AppVersionInfo> getChildCanAddChannelApp(Object param);
	
	/**
	 * 
	 * 查询子渠道可添加的渠道运营产品数量
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月20日 上午11:22:10
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	int getChildCanAddChannelAppCount(Object param);
	/**
	 * 
	 * 产品下线时，修改产品的所有版本为下线状态
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月26日 下午1:49:20
	 * 修改人：
	 * 修改时间：
	 * @param obj
	 */
	void updateByAppId(Object obj);
}
