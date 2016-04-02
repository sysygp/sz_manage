package com.power.dao;

import java.util.List;

import com.power.entity.PowerRoleFun;
import com.system.dao.MyBatisRepository;

@MyBatisRepository
public interface PowerRoleFunDao {
	/**
	 * 添加信息
	 * @param parameters
	 * @return
	 */
	void add(Object parameters);
	/**
	 * 查询模块信息
	 * @param parameters
	 * @return
	 */
	PowerRoleFun get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<String> getOnefield(Object parameters);
	
	/**
	 * 根据ids查询某个字段集合
	 * @param parameters
	 * @return
	 */
	List<String> getOnefieldByids(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<PowerRoleFun> getList(Object parameters);
	
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
	void delete(Object parameters);
	
	/**
	 * 批量删除数据
	 * @param parameters
	 */
	void delMul(Object parameters);
}
