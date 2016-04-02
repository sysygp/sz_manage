package com.zhrt.dao;

import java.util.List;
import java.util.Map;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.ChannelAppInfo;

@MyBatisRepository
public interface ChannelAppInfoDao {
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
	ChannelAppInfo get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<ChannelAppInfo> getList(Object parameters);
	
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
	 *	修改渠道产品的产品信息
	 * @param channelAppInfo
	 */
//	void updateByAppId(ChannelAppInfo channelAppInfo);

	/**
	 * 删除渠道产品信息
	 * @param appId
	 */
//	void delByMyepayAppId(String appId);

	/**
	 * 
	 * 根据渠道id修改渠道产品信息（渠道下线时，对应的渠道产品也下线）
	 * 创建人：
	 * 创建时间: 2015年8月18日 上午11:41:24
	 * 修改人：
	 * 修改时间：
	 * @param param
	 */
	void updateByChannelId(Map<String, Object> param);
	
	/**
	 * 
	 * 产品下线时，渠道产品修改为下线状态
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午1:56:28
	 * 修改人：
	 * 修改时间：
	 * @param param
	 */
	void updateByAppId(Map<String, Object> param);

	/**
	 * 
	 * 产品版本下线时，渠道产品修改为下线状态
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午2:02:37
	 * 修改人：
	 * 修改时间：
	 * @param map
	 */
	void updateByVerId(Map<String, Object> map);
	
	/**
	 * 
	 * 批量更新渠道运营产品状态
	 * 创建人：
	 * 创建时间: 2015年9月11日 下午5:13:59
	 * 修改人：
	 * 修改时间：
	 * @param map
	 */
	void updateBatch(Map<String, Object> map);
}
