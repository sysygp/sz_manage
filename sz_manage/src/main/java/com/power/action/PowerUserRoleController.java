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

import com.power.entity.PowerUser;
import com.power.entity.PowerUserRole;
import com.power.service.PowerUserRoleService;
import com.system.controller.BaseController;
import com.system.page.Page;


@Controller
@RequestMapping("/manager/power/userrole")
public class PowerUserRoleController extends BaseController {

	@Autowired
	private PowerUserRoleService powerUserRoleService;

	/**
	 * 列表查询
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
			view.addObject("toPage","../power/userrolelist.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			PowerUserRole entity = new PowerUserRole();
			
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = powerUserRoleService.getById(idparam);				
			}
			
			view.addObject("m",m);
			view.addObject("toPage","../power/userrolemodify.jsp");
			view.addObject("entity", entity);
			
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
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			
			PowerUserRole entity = null;
			if (StringUtils.isBlank(id)) {
				entity = new PowerUserRole();
			} else {
				entity = powerUserRoleService.getById(id);
			}
			
			
			if (StringUtils.isBlank(id)) {
				powerUserRoleService.add(entity);
			} else {
				powerUserRoleService.update(entity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/userrole/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			if(StringUtils.isNotBlank(idparam)){
//				powerUserRoleService.delById(idparam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/userrole/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delmul(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids.length > 0){
				powerUserRoleService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/userrole/index"));
	}
}
