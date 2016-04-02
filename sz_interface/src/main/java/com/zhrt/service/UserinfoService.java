package com.zhrt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhrt.dao.UserinfoDao;
import com.zhrt.entity.Settlement;
import com.zhrt.entity.UserRegist;
import com.zhrt.entity.Userinfo;
import com.zhrt.util.DateUtil;
import com.zhrt.util.ValiUtil;

/**
 * 账户信息业务层
 *
 */
@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class UserinfoService {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserinfoService.class);

	@Autowired
	private UserinfoDao userinfoDao;
	@Autowired
	private SettlementService settlementService;
	@Autowired
	private UserRegistService userRegistService;
	
	@Transactional
	public Userinfo getById(String id) {
		Userinfo entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			entity = userinfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Transactional
	public Userinfo getByImsi(String imsi) {
		Userinfo entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("imsi", imsi);
			entity = userinfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Transactional
	public Userinfo getByUuid(String uuid) {
		Userinfo entity = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("uuid", uuid);
			entity = userinfoDao.get(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Transactional
	public Userinfo getByImsiOrUuid(String imsi,String uuid) {
		Userinfo entity = null;

		try {
			Map<String, Object> paramMap = null;
			
			if(ValiUtil.valiImei(imsi)){
				paramMap = new HashMap<String, Object>();
				paramMap.put("imsi", imsi);
				entity = userinfoDao.get(paramMap);
				if(entity != null){
					return entity;
				}
			}else{
				if(StringUtils.isNotBlank(uuid) && uuid.length()>0){
					entity = getByUuid(uuid);
					if(entity != null){
						return entity;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Transactional
	public List<Userinfo> getList() {
		List<Userinfo> entityList = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			entityList = userinfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}

	@Transactional
	public List<Userinfo> getList(Map<String, Object> paramMap) {
		List<Userinfo> entityList = null;
		try {
			entityList = userinfoDao.getList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entityList;
	}

	@Transactional
	public int getListCount(Map<String, Object> paramMap) {
		int listCount = 0;
		try {
			listCount = userinfoDao.getListCount(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCount;
	}

	@Transactional
	public void add(Userinfo entity) {
		try {
			userinfoDao.add(entity);
			
			Settlement settlement = settlementService.getByChannel(entity.getChannelId());
			if(settlement != null && Double.parseDouble(settlement.getSettPercent()) > 0 
					&& Double.parseDouble(settlement.getSettPercent()) < 1){
				String registDate = DateUtil.DF_YMD_TRIM.format(new Date());
				String channelId = entity.getChannelId();
				UserRegist userRegistOld = userRegistService.getByRegistdateAndChannel(registDate, channelId);
				
				UserRegist userRegist = null;
				int userCount = 1;
				if(userRegistOld == null){
					userRegist = new UserRegist();
					userCount = 1;
				}else{
					userRegist = userRegistOld;
					userCount = userRegist.getUserCount()+1;
				}
				int channelUserCount = (int)(userCount*Double.parseDouble(settlement.getSettPercent()));
				if(channelUserCount==0){channelUserCount=1;}
				userRegist.setChannelId(channelId);
				userRegist.setChannelUserCount(channelUserCount);
				userRegist.setUserCount(userCount);
				userRegist.setUserRegistDate(registDate);
				if(userRegistOld == null){
					userRegistService.add(userRegist);
				}else{
					userRegistService.update(userRegist);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(Userinfo entity) {
		try {
			userinfoDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delById(String id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("id", id);
			userinfoDao.delById(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delByIds(Map<String, Object> idsMap) {
		try {
			userinfoDao.delBatch(idsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
