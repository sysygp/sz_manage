package com.zhrt.dao;

import java.util.List;
import java.util.Map;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.SpcodeConvert;

@MyBatisRepository
public interface SpcodeConvertDao {
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
	SpcodeConvert get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<SpcodeConvert> getList(Object parameters);
	
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
	 * 查询列表页，关联出产品数量
	 * 创建人：
	 * 创建时间: 2015年8月10日 下午4:13:14
	 * 修改人：
	 * 修改时间：
	 * @param paramMap
	 * @return
	 */
	List<SpcodeConvert> getForPage(Map paramMap);
}
