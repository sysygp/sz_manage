package com.zhrt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.dao.SdkVerDao;
import com.zhrt.entity.SdkVerInfo;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SdkVerService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SdkVerService.class);
	@Autowired
	private SdkVerDao sdkVerDao;

	@Transactional
	public SdkVerInfo getById(String id) {
		SdkVerInfo sdkVerInfo = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		sdkVerInfo = sdkVerDao.get(paramMap);
		return sdkVerInfo;
	}

	@Transactional
	public List<SdkVerInfo> getList() {
		List<SdkVerInfo> sdkVerInfoList = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		sdkVerInfoList = sdkVerDao.getList(paramMap);
		return sdkVerInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List<SdkVerInfo> getList(Map paramMap) {
		List<SdkVerInfo> sdkVerInfoList = null;
		sdkVerInfoList = sdkVerDao.getList(paramMap);
		return sdkVerInfoList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public int getListCount(Map paramMap) {
		int listCount = 0;
		listCount = sdkVerDao.getListCount(paramMap);
		return listCount;
	}

	@Transactional
	public void add(SdkVerInfo sdkVerInfo) {
		sdkVerDao.add(sdkVerInfo);
	}

	@Transactional
	public String update(SdkVerInfo sdkVerInfo) {
		String id = "";
		sdkVerDao.update(sdkVerInfo);
		return id;
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		sdkVerDao.delById(paramMap);
	}

	@Transactional
	public void delByIds(String[] ids) {
		Map<String, Object> idsMap = new HashMap<String,Object>();
		idsMap.put("ids", ids);
		sdkVerDao.delBatch(idsMap);
	}

	/**
	 * 
	 * 简单描述: 比较sdk版本号
	 *
	 * @author 王坤
	 * @Create Date 2015年9月8日 10点30分30秒
	 */
	public boolean compareSDKCode(String newSdkVerCode) {
		boolean isTrue = true;
		//查询出数据库中已经存在的所有版本号
		List<SdkVerInfo> sdkVers = getList();
		if(sdkVers!=null){
		 for(SdkVerInfo oldSdkVer : sdkVers){
			boolean isSdkCode = this.compareNewAndOldSdkCode(newSdkVerCode, oldSdkVer);
			if(!isSdkCode){
				return false;
			}
		 }
		}
		return isTrue;
	}
	
	/**
	 * 
	 * 简单描述:版本号以.分割后的每一位只能是0-99
	 *
	 * @author 王坤
	 * @Create Date 2015年9月8日 10点30分30秒
	 */
	public boolean checkSdkCodeOnebit(String sdkVerCode) {
		if(StringUtils.isNotBlank(sdkVerCode)){
		   String[] sdkVerCodes = sdkVerCode.split("\\.");
		   if(sdkVerCodes.length<4){
			   return false;
		   }
		   
		   for(int i = 0; i<sdkVerCodes.length; i++){
			   if(Integer.parseInt(sdkVerCodes[i])<0 || Integer.parseInt(sdkVerCodes[i])>99){
				  return false;
			   }
		   }
		   
		   return true;
		}
		
		return false;
	}
		
	
	/**
	 * 
	 * 简单描述: 比较新老版本的sdkCode
	 *
	 * @author 王坤
	 * @Create Date 2015年9月8日 10点30分30秒
	 */
	public boolean compareNewAndOldSdkCode(String newSdkVerCode,
			SdkVerInfo oldSdkVer) {
		String[] newSdkVerCodes = newSdkVerCode.split("\\."); 
		String[] oldSdkVerCodes = oldSdkVer.getSdkVerCode().split("\\."); 
		for(int i = 0; i<4;){
			if(Integer.parseInt(newSdkVerCodes[i]) > Integer.parseInt(oldSdkVerCodes[i])){
				return true;
			}else if(Integer.parseInt(newSdkVerCodes[i]) < Integer.parseInt(oldSdkVerCodes[i])){
				return false;
			}else{
				i++;
				if(4 == i){
					//表示版本号相同
					return false;
				}
			}
		}		
		return true;
	}
		
}
