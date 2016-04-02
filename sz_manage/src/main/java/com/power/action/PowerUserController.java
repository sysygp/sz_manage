package com.power.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.power.bo.DtreeBo;
import com.power.entity.PowerRole;
import com.power.entity.PowerUser;
import com.power.service.DtreeService;
import com.power.service.PowerDomainService;
import com.power.service.PowerRoleService;
import com.power.service.PowerUserFunService;
import com.power.service.PowerUserRoleService;
import com.power.service.PowerUserService;
import com.system.controller.BaseController;
import com.system.page.Page;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.util.Constant;
import com.zhrt.util.ValiUtil;


@Controller
@RequestMapping("/manager/power/user")
public class PowerUserController extends BaseController {

	@Autowired
	private PowerUserService powerUserService;
	@Autowired
	private PowerRoleService powerRoleService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private PowerUserRoleService powerUserRoleService;
	@Autowired
	private PowerUserFunService powerUserFunService;
	@Autowired
	private PowerDomainService powerDomainService;
	@Autowired
	private DtreeService dtreeService;

	/**
	 * 指定域下用户列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String operUserDomainId = ((PowerUser)getSessAttr("operUser")).getDomainId();
//			String domainId = ServletRequestUtils.getStringParameter(request,"domainId");
			String roleId = ServletRequestUtils.getStringParameter(request,"roleId");
//			if(StringUtils.isBlank(domainId)){
//				domainId = operUserDomainId;
//			}
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
//			paramMap.put("domainId", domainId);
			paramMap.put("roleId", roleId);
			paramMap.put("status", Constant.STATUS_VALID);
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if(page.isAutoCount()){
				int totalCount = powerUserService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<PowerUser> entityList = powerUserService.getList(paramMap);
			view.addObject("entityList","entityList");
			page.setResult(entityList);
			
//			view.addObject("domainId", domainId);
			view.addObject("page", page);
			view.addObject("toPage","../power/userlist.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return view;
	}
	
	/**
	 * 当前登录域下用户列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView findUserListByDomain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String domainId = ServletRequestUtils.getStringParameter(request,"domainId","");
			String roleId = ServletRequestUtils.getStringParameter(request,"roleId","");
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			paramMap.put("roleId", roleId);
			paramMap.put("domainId", domainId);
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if(page.isAutoCount()){
				int totalCount = powerUserRoleService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<PowerUser> entityList = powerUserRoleService.findUsersByRoleId(paramMap);
			view.addObject("entityList","entityList");
			page.setResult(entityList);
			
			view.addObject("domainId", domainId);
			view.addObject("roleId", roleId);
			view.addObject("page", page);
			view.addObject("toPage","../power/userlist.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return view;
	}
	
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam, @PathVariable String m,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		String operUserDomainId = ((PowerUser)getSessAttr("operUser")).getDomainId();
		String domainId = ServletRequestUtils.getStringParameter(request,"domainId");
		if(StringUtils.isBlank(domainId)){
			domainId = operUserDomainId;
		}
		
		try {
			PowerUser entity = new PowerUser();
			
			Map<String,String> userRoleMap = new HashMap<String,String>();
			if(ValiUtil.valiStr(idparam) && !idparam.equals("0")) {
				entity = powerUserService.getById(idparam);
				
				//用户原有角色
				List<String> userRoleidList = powerUserRoleService.getRolesByUserid(entity.getId());
				if(userRoleidList != null && userRoleidList.size() > 0){
					for(String userRoleId : userRoleidList){
						userRoleMap.put(userRoleId, "1");
					}
				}
			}
			
			Map queryMap = new HashMap();
			queryMap.put("domainId", domainId);
			List<PowerRole> roleList = powerRoleService.getList(queryMap);
			
			List<ChannelInfo> channelInfoList = channelInfoService.getList();
			view.addObject("channelInfoList", channelInfoList);
			
			view.addObject("m",m);
			view.addObject("entity", entity);
			view.addObject("roleList", roleList);
			view.addObject("userRoleMap", userRoleMap);
			view.addObject("toPage","../power/usermodify.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return view;
	}
	
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String platid = getSessionUser().getPlatId().toString();
//			String domainId =  getSessionUser().getDomainId().toString();
			
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			String loginId = ServletRequestUtils.getStringParameter(request,"loginId","");
			String loginName = ServletRequestUtils.getStringParameter(request,"loginName","");
			String loginPwd = ServletRequestUtils.getStringParameter(request,"loginPwd","");
			String email = ServletRequestUtils.getStringParameter(request,"email","");
			String phone = ServletRequestUtils.getStringParameter(request,"phone","");
			String channelId = ServletRequestUtils.getStringParameter(request,"channelId","");
			String[] roleIds = ServletRequestUtils.getStringParameters(request,"roleIds");
			
			PowerUser entity = new PowerUser();
			entity.setId(id);
			entity.setLoginId(loginId);
			entity.setLoginName(loginName);
			entity.setLoginPwd(Constant.genePwdMD5(loginPwd));
//			entity.setPhone(phone);
//			entity.setEmail(email);
			entity.setDomainId(channelId);
			entity.setStatus(Constant.STATUS_VALID);
			
			if(!ValiUtil.valiStr(id)){
				entity.setPlatId(Integer.parseInt(platid));
				
				powerUserService.add(entity,roleIds);
			} else {
				powerUserService.update(entity,roleIds);
			}
			
			return new ModelAndView(new RedirectView("/manager/power/user/index"));
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
	}
	
	
	@RequestMapping(value = "/getfun/{idparam}/{domainid}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView getfun(@PathVariable String idparam, @PathVariable String domainid, @PathVariable String m,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			PowerUser entity = new PowerUser();
			
			DtreeBo dtreeBo = null;
			if(ValiUtil.valiStr(idparam) && !idparam.equals("0")) {
				entity = powerUserService.getById(idparam);
				
				dtreeBo = dtreeService.getFunctionTreeEdit(entity, getSessionUser());
				
				view.addObject("m",m);
				view.addObject("domainId", domainid);
				view.addObject("entity", entity);
				view.addObject("dtreeBo", dtreeBo);
				view.addObject("toPage","../power/usermodifyfun.jsp");
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return view;
	}
	
	
	@RequestMapping(value = "/savefun", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView savefun(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			PowerUser entity = new PowerUser();
			
			String[] funids = ServletRequestUtils.getStringParameters(request,"powerCode");
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			if(ValiUtil.valiStr(id) && !id.equals("0")) {
				entity = powerUserService.getById(id);
				powerUserService.updateUserFun(entity, funids);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/power/user/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			if(StringUtils.isNotBlank(idparam)){
				powerUserService.delById(idparam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/user/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delmul(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids.length > 0){
				powerUserService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/user/index"));
	}
}
