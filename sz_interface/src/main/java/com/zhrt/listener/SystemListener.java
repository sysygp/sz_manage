package com.zhrt.listener;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.zhrt.service.AppInfoService;
import com.zhrt.service.AppTypeService;
import com.zhrt.service.AppVersionInfoService;
import com.zhrt.service.ChannelAppInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.LatnService;
import com.zhrt.service.PayTypeService;
import com.zhrt.service.PropInfoService;
import com.zhrt.service.SubcodeService;
import com.zhrt.util.Constant;

/** 
 * 监听类
 * 
 */
@Component
public class SystemListener implements InitializingBean, ServletContextAware {
	private static Logger logger = LoggerFactory.getLogger(SystemListener.class);
	
	@Autowired
	private LatnService latnService;
	@Autowired
	private SubcodeService subcodeService;
	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private AppInfoService appInfoservice;
	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	@Autowired
	private PropInfoService propInfoService;
//	@Autowired
//	private PowerFunctionService powerFunctionService;
	@Autowired
	private PayTypeService payTypeService;
//	@Autowired
//	private PowerModuleService powerModuleService;
	@Autowired
	private AppTypeService appTypeService;
	
	@Override
	public void setServletContext(ServletContext context) {
		logger.info("初始化插件begin.....");
		initPlugin();
		logger.info("初始化插件end.....");
	}

	@Override
	public void afterPropertiesSet() throws Exception { }
	
	/**
	 * 初始化插件
	 */
	private void initPlugin(){
		try {
			Constant.subcodeMap = subcodeService.getSubcodeMap();
			Constant.LATN_MAP = latnService.getLatnMap();
			Constant.cpMap = cpInfoService.getCpMap();
			Constant.channelMap = channelInfoService.getChannelMap();
			Constant.appMap = appInfoservice.getAppMap();
			Constant.channelAppMap = channelAppInfoService.getChannelAppInfoMap();
			Constant.appVersionMap = appVersionInfoService.getAppVersionInfoMap();
			Constant.propMap = propInfoService.getPropMap();
//			Constant.funcMap = powerFunctionService.getFunMap();
			Constant.payTypeMap = payTypeService.getPayTypeMap();
//			Constant.moduleMap = powerModuleService.getModuleMap();
			Constant.appSortMap = appTypeService.getAppSortMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
