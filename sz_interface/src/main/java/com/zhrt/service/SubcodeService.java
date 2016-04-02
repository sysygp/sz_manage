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

import com.zhrt.dao.SubcodeDao;
import com.zhrt.entity.Subcode;
import com.zhrt.entity.Subcode;
import com.zhrt.util.Constant;
import com.zhrt.util.ValiUtil;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class SubcodeService {
	private static Logger logger = LoggerFactory.getLogger(SubcodeService.class);

	@Autowired
	private SubcodeDao subcodeDao;
	
	@Transactional
	public Subcode get(Map map){
		Subcode subcode = null;
		try {
			subcode = subcodeDao.get(map);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return subcode;
	}
	
	@Transactional
	public List<Subcode> getList(){
		List<Subcode> scodeList = null;
		try {
			Map paramMap = new HashMap();
			scodeList = subcodeDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return scodeList;
	}
	
	@Transactional
	public List<Subcode> getList(Map paramMap) {
		List<Subcode> scodeList = null;
		try {
			scodeList = subcodeDao.getList(paramMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return scodeList;
	}
	
	@Transactional
	public int getListCount(Map paramMap){
		int listCount = 0;
		try {
			listCount = subcodeDao.getListCount(paramMap);
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional
	public void add(Subcode scode){
		try {
			subcodeDao.add(scode);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void update(Subcode scode) {
		try {
			subcodeDao.update(scode);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delById(Map map){
		try {
			subcodeDao.delById(map);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据手机号查询号段信息
	 * @param phone
	 * @return
	 */
	public Subcode getByPhone(String phone){
		Subcode subcode = null;
		try {
			if(ValiUtil.valiPhone(phone)){				
				Map map = new HashMap();
				map.put("subcode", phone.substring(0, 7));
				subcode = subcodeDao.get(map);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return subcode;
	}
	
	
	
	@Transactional
	public Map<String, Subcode> getSubcodeMap() throws Exception{
		Map<String, Subcode> subcodeMap = new HashMap<String,Subcode>();
		
		try {
			List<Subcode> subcodeList = getList();
			for(Subcode subcode: subcodeList){
				subcodeMap.put(subcode.getSubcode(),subcode);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return subcodeMap;
	}
}
