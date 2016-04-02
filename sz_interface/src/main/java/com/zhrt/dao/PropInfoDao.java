package com.zhrt.dao;

import java.util.List;
import java.util.Map;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.PropInfo;

@MyBatisRepository
public interface PropInfoDao {
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
	PropInfo get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<PropInfo> getList(Object parameters);
	
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
	 * 简单描述: 查询某个产品下的所有道具
	 *
	 * @author 王坤
	 * @Create Date 2015年9月14日   
	 *
	 * @UpdateAuthor 
	 * @Update Date 2015年8月27日 
	 *
	 */
	List<PropInfo> getPropsByAppId(Object parameters);
}
