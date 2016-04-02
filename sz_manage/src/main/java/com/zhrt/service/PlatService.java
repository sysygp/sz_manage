package com.zhrt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.power.dao.PowerDomainDao;
import com.power.dao.PowerRoleDao;
import com.power.dao.PowerRoleFunDao;
import com.power.dao.PowerUserDao;
import com.power.service.PowerUserRoleService;
import com.system.util.system.RandomUtil;
import com.zhrt.dao.CommonDao;
import com.zhrt.dao.PlatDAO;
import com.zhrt.entity.Plat;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class PlatService {
	private static Logger logger = LoggerFactory.getLogger(PlatService.class);

	@Autowired
	private PlatDAO platDao;
	@Autowired
	private PowerDomainDao powerDomainDao;
	@Autowired
	private PowerRoleDao powerRoleDao;
	@Autowired
	private PowerRoleFunDao powerRoleFunDao;
	@Autowired
	private PowerUserDao powerUserDao;
	@Autowired
	private PowerUserRoleService powerUserRoleService;
	
	private CommonDao commonDao;
	
	@Transactional
	public Plat getById(String id){
		Plat plat = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("id", id);
			
			plat = platDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return plat;
	}
	
	@Transactional
	public Plat getByplatId(String platId){
		Plat plat = null;
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("platid", platId);
			
			plat = platDao.get(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return plat;
	}
	
	
	@Transactional
	public List<Plat> getList()   {
		List<Plat> platList = null;
		try {
			Map paramMap = new HashMap();
			platList = platDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return platList;
	}
	
	@Transactional
	public List<Plat> getList(Map paramMap)   {
		List<Plat> platList = null;
		try {
			platList = platDao.getList(paramMap);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return platList;
	}
	
	@Transactional
	public int getListCount(Map paramMap)   {
		int listCount = 0;
		try {
			listCount = platDao.getListCount(paramMap);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return listCount;
	}
	
	@Transactional(propagation = Propagation.REQUIRED ,rollbackFor=MySQLSyntaxErrorException.class)
	public void add(Plat plat){
			
			//创建平台信息
			plat.setPlatkey(RandomUtil.genUuid8());
			platDao.add(plat);
			
			String sql = "CREATE TRIGGER `trig1` BEFORE INSERT ON `t1` FOR EACH ROW BEGIN insert into ttotal (id,name) values (new.id,new.name);end;";
			Map paramMap = new HashMap();
			paramMap.put("sql", sql);
			commonDao.addPartTable(paramMap);
//			System.out.println(1/0);
		
	}
	
	@Transactional
	public String update(Plat plat)   {
		String id = null;
		try {
			
			platDao.update(plat);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	
	@Transactional
	public void delById(String id){
		try {
			Plat plat = new Plat();
			plat.setId(id);
			platDao.delById(id);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void delByIds(String[] ids)   {
		try {
			Map idsMap = new HashMap();
			idsMap.put("ids", ids);
			platDao.delBatch(idsMap);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Transactional
	public Map<String, Object> getPlatMap() {
		Map<String, Object> platMap = new HashMap<String, Object>();
		Map<String, String> param = new HashMap<String, String>();
		List<Plat> platList = platDao.getList(param);
		for (Plat plat : platList) {
			platMap.put(String.valueOf(plat.getPlatid()), plat);
		}
		return platMap;
	}
	
}
