package com.power.dao;

import java.util.List;

import com.power.entity.PowerUser;
import com.system.dao.MyBatisRepository;

@MyBatisRepository
public interface PowerUserDao {
	/**
	 * 添加模块信息
	 * @param parameters
	 * @return
	 */
	void add(Object parameters);
	/**
	 * 查询模块信息
	 * @param parameters
	 * @return
	 */
	PowerUser get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<PowerUser> getList(Object parameters);
	
	/**
	 * 查询集合总数
	 * @param parameters
	 * @return
	 */
	int getListCount(Object parameters);
	/**
	 * 修改模块信息
	 * @param parameters
	 */
	void update(Object obj);

	/**
	 * 删除模块信息
	 * @param parameters
	 */
	void delById(Object parameters);
	
	/**
	 * 批量删除数据
	 * @param parameters
	 */
	void delMul(Object parameters);
}
