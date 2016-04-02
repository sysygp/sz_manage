package com.power.action;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.power.bo.ModuleAndFuncBo;
import com.power.entity.PowerUser;
import com.power.service.LoginService;
import com.system.controller.BaseController;
import com.system.util.datasource.DynamicDataSource;
import com.system.util.date.DateUtil;
import com.zhrt.entity.OperLog;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.util.Constant;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private OperLogService operLogService;
	
	@Autowired 
	ChannelInfoService channelInfoService;//渠道业务类
	@Autowired
	private CpInfoService cpInfoService;//cp业务类
	@Autowired
	private AppInfoService appInfoService;//产品业务类
	/**
	 * 校验登录名是否在数据库存在、校验验证码是否正确
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/check", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATA_SOURCE_USERSYSQUERY);
		//返回给终端的code  0:成功   1:验证码有误  2:登录名或密码有误
		String code = "0";
		
		String loginName =ServletRequestUtils.getStringParameter(request,"loginName","");
		if(!StringUtils.isBlank(loginName)){
			loginName = loginName.trim();
		}
		String loginPwd =ServletRequestUtils.getStringParameter(request,"loginPwd","");
		if(!StringUtils.isBlank(loginPwd)){
			loginPwd = loginPwd.trim();
		}
		String checkCode =ServletRequestUtils.getStringParameter(request,"checkCode","");
		if(!StringUtils.isBlank(checkCode)){
			checkCode = checkCode.trim();
		}
		logger.info("用户名:"+loginName+",密码:"+loginPwd+"来平台校验了");
		//获取session中存的验证码   key值为 rand
		String checkCodeSession = getSessAttr("rand");
		
		//判断验证码是否相等
//		if(StringUtils.isBlank(checkCodeSession) || StringUtils.isBlank(checkCode) || !checkCode.endsWith(checkCodeSession.trim())){
//		   //验证码有问题, 没必要再去查询数据库
//			code = "1";
//		}else{
		
			//去数据库查询此用户是否存在		
			//查询用户名是否存在
			String name = loginService.findLoginUserName(loginName);
			//查询密码是否正确(根据用户名和密码去查找，如果没此用户则密码错误)
			if(!StringUtils.isBlank(name) && loginName.endsWith(name.trim())){
			    PowerUser operUser = loginService.findLoginUser(loginName, loginPwd);
			    if(operUser == null){
				  code = "3";//密码不正确
			    }
			  
			}else{
				code = "2";//用户名不存在
			}
//		}
		//删除session中的rand值  即验证码
		request.getSession().removeAttribute("rand");
		
		PrintWriter out = response.getWriter();
		out.write(code);
		out.flush();
		out.close();
		
		DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATA_SOURCE_USERSYS);
		
	}
	
	
	
	
	
	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/in", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATA_SOURCE_USERSYSQUERY);
		
		String loginName =ServletRequestUtils.getStringParameter(request,"loginName","");
		String loginPwd =ServletRequestUtils.getStringParameter(request,"loginPwd","");
		
		
		PowerUser operUser = loginService.findLoginUser(loginName, loginPwd);
		
		//生成操作日志
		OperLog log = new OperLog(); 
		log.setBussName("");// TODO 设置业务名称
		log.setIp(getIp());
		log.setOperDesc("用户"+loginName+"登录");
		log.setOperType(Constant.OPERTYPE_LOGIN);
		log.setOperUser(loginName);
		log.setOperTime(DateUtil.getCurDate());
		
		if(operUser!=null){
			log.setOperResult(Constant.DEAL_OK);
			log.setOperRole("");// TODO 设置登录用户角色
			setSessAttr("operUser", operUser);
			
			if(getSessAttr("menuList") == null) {
				List<ModuleAndFuncBo> menuList = loginService.getMenu(operUser);
				setSessAttr("menuList", menuList);
			}
			
			//
			
			//获取用户的platid
			String platid = getSessionUser().getPlatId()+"";
			
			if(!StringUtils.isBlank(platid) && !platid.equals("0")){
				Map<String,Object> platCountNap = new HashMap<String, Object>(); 
				//渠道数量   默认就是上线的渠道
				int channelCount = channelInfoService.getListCount(null);
				//cp数量    默认就是上线的cp
				int cpCount = cpInfoService.getListCount(null);
				//运营产品数量  默认就是上线的产品
				int appCount = appInfoService.getListCount(null);
				
				Map<String, Object> zbMaps = new HashMap<String, Object>();
				zbMaps.put("platid", platid);
				zbMaps.put("appSeq", "total");
				
				platCountNap.put("channelCount", channelCount);
				platCountNap.put("cpCount", cpCount);
				platCountNap.put("appCount", appCount);
				
				view.addObject("platCountNap", platCountNap);	
				
				view.addObject("toPage","../collectioninfo/collectioninfo.jsp");
			}else{
				view.addObject("toPage","../power/userlist.jsp");
			}
			
		}else{
			log.setOperResult(Constant.DEAL_ERR);
			view.addObject("toPage","../system/login.jsp");
		}
		DynamicDataSource.setCurrentLookupKey(DynamicDataSource.DATA_SOURCE_USERSYS);
		operLogService.add(log);
		return view;
	}
	
	@RequestMapping(value = "/out", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(LOGIN);
		
		request.getSession().setMaxInactiveInterval(1);
		request.getSession().invalidate();
		
		return view;
	}
}
