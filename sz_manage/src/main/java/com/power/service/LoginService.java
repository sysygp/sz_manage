package com.power.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.power.bo.ModuleAndFuncBo;
import com.power.entity.PowerFunction;
import com.power.entity.PowerModule;
import com.power.entity.PowerUser;
import com.zhrt.util.Constant;

@Service
@Component
@Transactional(rollbackFor = Exception.class)
public class LoginService {
	private static Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private PowerModuleService powerModuleService;
	@Autowired
	private PowerUserFunService powerUserFunService;
	@Autowired
	private PowerFunctionService powerFunctionService;
	@Autowired
	private PowerUserService powerUserService;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<ModuleAndFuncBo> getMenu(PowerUser operUser)   {

		String userId = operUser.getId();
		List<String> userFuncs = powerUserFunService.getFunidsByUserid(userId);
		
		List<ModuleAndFuncBo> menuList = new ArrayList<ModuleAndFuncBo>();
		try {
			
			List<PowerModule> powerModuleList = powerModuleService.getList();
			if(powerModuleList != null && powerModuleList.size() > 0){
				for(PowerModule powerModule : powerModuleList){
					String moduleId = powerModule.getModuleId();
					List<PowerFunction> functionList = powerFunctionService.getListByModule(moduleId);
					
					List<PowerFunction> functionListOk =new ArrayList<PowerFunction>();
					if(functionList!=null && functionList.size()>0){
						
						for(PowerFunction pf:functionList){
							if(userFuncs.contains(pf.getId())){
								functionListOk.add(pf);
							}
						}
						
						if(functionListOk.size()>0){							
							ModuleAndFuncBo bo = new ModuleAndFuncBo();
							bo.setModuleId(moduleId);
							bo.setModuleName(powerModule.getModuleName());
							bo.setFunctionList(functionListOk);
							menuList.add(bo);
						}
						
					}
				}
			}
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return menuList;
	}
	
	
	/**
	 * 查看当前登录用户
	 * 
	 * @param loginId
	 * @param loginPwd
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PowerUser findLoginUser(String loginId, String loginPwd){
		PowerUser powerUser = null;
		try {
			powerUser = powerUserService.findPowerUser(loginId, Constant.genePwdMD5(loginPwd));
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return powerUser;
	}

    /**
     * 查询登录名是否存在
     * 
     * @param loginId
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String findLoginUserName(String loginId) {
		String name = "";
		try {
			name = powerUserService.findPowerUserNameByLoginId(loginId);
			
		}catch (Exception e) {
			 e.printStackTrace();
		}
		return name;
	}
}
