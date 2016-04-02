package com.zhrt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.dao.DataFileAppVerDao;
import com.zhrt.dao.DataPackInfoDao;
import com.zhrt.entity.DataFileAppVer;
import com.zhrt.entity.DataPackInfo;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class DataPackInfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(DataPackInfoService.class);

	@Autowired
	private DataPackInfoDao dataPackInfoDao;
	@Autowired
	private DataFileAppVerDao dataFileAppVerDao;

	@Transactional
	public DataPackInfo getById(String id) {
		DataPackInfo dataPack = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		dataPack = dataPackInfoDao.get(paramMap);
		return dataPack;
	}

	@Transactional
	public List<DataPackInfo> getList() {
		List<DataPackInfo> dataPackList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		dataPackList = dataPackInfoDao.getList(paramMap);
		return dataPackList;
	}

	@Transactional
	public List<DataPackInfo> getList(Map<String, Object> paramMap) {
		List<DataPackInfo> dataPackList = null;
		dataPackList = dataPackInfoDao.getList(paramMap);
		return dataPackList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		listCount = dataPackInfoDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(DataPackInfo dataPackInfo) {
		dataPackInfoDao.add(dataPackInfo);
	}

	/**
	 * 
	 * 修改数据文件和产品版本关联
	 * 创建人：
	 * 创建时间: 2015年8月17日 下午7:47:32
	 * 修改人：
	 * 修改时间：
	 * @param dataPackInfo
	 * @param verIds
	 */
	@Transactional
	public void update(DataPackInfo dataPackInfo,String[] verIds) {
		dataPackInfoDao.update(dataPackInfo);
		//删除数据文件时需要删除文件和产品版本关联关系
		if(String.valueOf(Constant.STATUS_DEL).equals(dataPackInfo.getStatus())){
			dataFileAppVerDao.delByDataId(dataPackInfo);
		}
		//修改时删除文件和产品版本关联，在新增新的关联关系
		else{
			dataFileAppVerDao.delByDataId(dataPackInfo);
			DataFileAppVer dataFileAppVer = new DataFileAppVer();
			dataFileAppVer.setDataFileId(dataPackInfo.getId());
			for (int i = 0; i < verIds.length; i++) {
				dataFileAppVer.setAppVerId(verIds[i]);
				dataFileAppVerDao.add(dataFileAppVer);
			}
		}
			
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		dataPackInfoDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		dataPackInfoDao.delBatch(idsMap);
	}

	/**
	 * 
	 *	增加数据文件和产品版本中间表关联
	 * 创建人：
	 * 创建时间: 2015年8月17日 下午5:24:55
	 * 修改人：
	 * 修改时间：
	 * @param dataPackInfo
	 * @param verIds
	 */
	@Transactional
	public void addVersion(DataPackInfo dataPackInfo, String[] verIds) {
		dataPackInfoDao.add(dataPackInfo);
		DataFileAppVer dataFileAppVer = new DataFileAppVer();
		dataFileAppVer.setDataFileId(dataPackInfo.getId());
		for (int i = 0; i < verIds.length; i++) {
			dataFileAppVer.setAppVerId(verIds[i]);
			dataFileAppVerDao.add(dataFileAppVer);
		}
	}

	@Transactional
	public List<DataFileAppVer> getSuportVersion(String idparam) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dataFileId", idparam);
		return dataFileAppVerDao.getList(param);
	}
}
