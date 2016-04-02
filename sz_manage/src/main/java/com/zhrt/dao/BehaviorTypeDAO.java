package com.zhrt.dao;

import java.util.List;

import com.system.dao.MyBatisRepository;
import com.zhrt.entity.BehaviorType;

@MyBatisRepository
public interface BehaviorTypeDAO {
	/**
	 * 添加用户行为类型信息
	 * @param parameters
	 * @return
	 */
	void add(Object parameters);
	/**
	 * 查询用户行为类型信息
	 * @param parameters
	 * @return
	 */
	BehaviorType get(Object parameters);
	
	/**
	 * 查询集合
	 * @param parameters
	 * @return
	 */
	List<BehaviorType> getList(Object parameters);
	
	/**
	 * 查询集合总数
	 * @param parameters
	 * @return
	 */
	int getListCount(Object parameters);
	/**
	 * 修改用户行为类型信息
	 * @param parameters
	 */
	void update(Object obj);

	/**
	 * 删除用户行为类型信息
	 * @param parameters
	 */
	void delById(Object parameters);
	
	/**
	 * 批量删除数据
	 * @param parameters
	 */
	void delBatch(Object parameters);
}
