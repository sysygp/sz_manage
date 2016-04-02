package com.zhrt.dao;

import java.util.List;
import java.util.Map;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.ChannelInfo;

@MyBatisRepository
public interface ChannelInfoDao {
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
	ChannelInfo get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<ChannelInfo> getList(Object parameters);
	
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
	 * 查询推广产品的所有渠道
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月13日 下午1:59:19
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	List<ChannelInfo> getByChannelApp(Object param);
	/**
	 * 
	 * 列表页查询，关联运营产品数量
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月18日 上午10:53:30
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	List<ChannelInfo> getForPage(Object param);

	/**
	 * 
	 * 查询推广某个产品版本的渠道
	 * 创建人：朱士竹
	 * 创建时间: 2015年8月28日 下午7:46:35
	 * 修改人：
	 * 修改时间：
	 * @param param
	 * @return
	 */
	List<ChannelInfo> getByVerId(Map<String, Object> param);
}
