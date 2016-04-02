package com.power.dao;

import com.power.entity.PowerOperlog;
import com.system.dao.MyBatisRepository;

@MyBatisRepository
public interface PowerOperlogDao {
	/**
	 * 添加账户信息
	 * @param parameters
	 * @return
	 */
	void add(Object parameters);
	/**
	 * 查询账户信息
	 * @param parameters
	 * @return
	 */
	PowerOperlog get(Object parameters);
	/**
	 * 修改账户信息
	 * @param parameters
	 */
	void update(Object obj);

	/**
	 * 删除账户信息
	 * @param parameters
	 */
	void del(Object parameters);
}
