package com.power.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.SetUniqueList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.power.bo.DtreeBo;
import com.power.dao.PowerFunctionDao;
import com.power.dao.PowerModuleDao;
import com.power.dao.PowerUserFunDao;
import com.power.entity.PowerFunction;
import com.power.entity.PowerRole;
import com.power.entity.PowerUser;
import com.power.entity.PowerUserFun;
import com.power.util.DtreeUtil;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class DtreeService {
	private static Logger logger = LoggerFactory.getLogger(DtreeService.class);

	
	@Autowired
	private PowerModuleDao powerModuleDao;
	@Autowired
	private PowerFunctionDao powerFunctionDao;
	@Autowired
	private PowerUserFunDao powerUserFunDao;
	@Autowired
	private PowerRoleFunService powerRoleFunService;
	@Autowired
	private PowerUserFunService powerUserFunService;
	
	
	/**
	 * 修改角色页面获取权限树
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public DtreeBo getFunctionTreeEdit(Object entity, PowerUser loginUser){
		DtreeBo dtreeBo = new DtreeBo();
		
		try {
			
			List<String> hidePowerFunIds = null;
			
			//界面中菜单树要显示的模块
			List<String> displayModuleidList = new ArrayList<String>();
			//界面中菜单树要显示的权限
			List<PowerFunction> displayFunList = new ArrayList<PowerFunction>();
			//界面中菜单树要显示的默认选中的模块
			List<String> selectedFunidList = new ArrayList<String>();
			
			
			if(loginUser.getId().equals("1")){
				
				//所有模块
				Map moduleQueryMap = new HashMap();
				moduleQueryMap.put(com.system.util.Constant.DATABASE_QUERY_FILED, "moduleId");
				displayModuleidList = powerModuleDao.getOnefield(moduleQueryMap);
				
				//所有权限
				Map funcQueryMap = new HashMap();
				funcQueryMap.put("status",  Constant.STATUS_VALID);
				displayFunList = powerFunctionDao.getList(funcQueryMap);
				
			}else{
				
				String userId = "";
				if(entity != null){
					if(entity.getClass().getSimpleName().equals("PowerRole")){	
						userId = loginUser.getId();
					}else if(entity.getClass().getSimpleName().equals("PowerUser")){
						userId = ((PowerUser) entity).getId();
					}
				}
				
				//当前登录用户所有的权限
				Map userFunMap = new HashMap();
				userFunMap.put("userId", userId);
				List<PowerUserFun> userFunList = powerUserFunDao.getList(userFunMap);
				
				
				//当前用户对应权限id
				List<String> user_FunidList = new ArrayList<String>();
				
				//循环该用户所有权限，为了得到所有要显示的权限和模块
				for(PowerUserFun userFun : userFunList){
					
					Map queryFunMap = new HashMap();
					queryFunMap.put("id", userFun.getFunctionId());
					PowerFunction powerFunction = powerFunctionDao.get(queryFunMap);
					
					user_FunidList.add(userFun.getFunctionId());
					displayFunList.add(powerFunction);
					displayModuleidList.add(powerFunction.getModuleId());
				}
				//模块去重复
				displayModuleidList = SetUniqueList.decorate(displayModuleidList);
				
			}
			
			//修改界面需要自动选中已有权限
			if(entity != null){
				if(entity.getClass().getSimpleName().equals("PowerRole")){	
					PowerRole powerRole = (PowerRole) entity;
					selectedFunidList = powerRoleFunService.getFunidsByRoleid(powerRole.getId());
				}else if(entity.getClass().getSimpleName().equals("PowerUser")){
					PowerUser powerUser = (PowerUser) entity;
					selectedFunidList = powerUserFunService.getFunidsByUserid(powerUser.getId());
				}
			}
			
			
			String dtree = DtreeUtil.getDtreeStr(displayModuleidList, displayFunList, selectedFunidList);
			dtree += "document.write(mytree);\n";
			
			dtreeBo.setDtree(dtree.toString());
			dtreeBo.setHidePowerFunIds(hidePowerFunIds);
//			powerRoleFunBo.setPlatId(powerRole.getPlatId());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dtreeBo;
	}
	
	
}
