package com.zhrt.dao;

import java.util.List;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.Plat;

@MyBatisRepository
public interface PlatDAO {
	/**
	 * 添加平台信息
	 * @param parameters
	 * @return
	 */
	void add(Object parameters);
	void addPartTable(Object parameters);
	
	/**
	 * 查询平台信息
	 * @param parameters
	 * @return
	 */
	Plat get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<Plat> getList(Object parameters);
	
	/**
	 * 查询集合总数
	 * @param parameters
	 * @return
	 */
	int getListCount(Object parameters);
	
	/**
	 * 修改平台信息
	 * @param parameters
	 */
	void update(Object obj);

	/**
	 * 删除平台信息
	 * @param parameters
	 */
	void delById(Object parameters);
	
	/**
	 * 批量删除数据
	 * @param parameters
	 */
	void delBatch(Object parameters);
	
	/**
	 * 创建用户分表
	 * @param parameters
	 */
	void createUserTables(Object parameters);
}
