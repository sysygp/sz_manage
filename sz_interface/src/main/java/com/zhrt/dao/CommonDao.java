package com.zhrt.dao;

import com.system.dao.MyBatisRepository;

@MyBatisRepository
public interface CommonDao {
	
	/**
	 * 
	 * 执行动态创建的sql
	 * 创建人：朱士竹
	 * 创建时间: 2015年9月10日 上午9:26:39
	 * 修改人：
	 * 修改时间：
	 * @param parameters
	 */
	void addPartTable(Object parameters);
	
}
