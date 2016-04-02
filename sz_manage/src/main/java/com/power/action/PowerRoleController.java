package com.power.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.system.controller.BaseController;
import com.system.page.Page;
import com.zhrt.service.PlatService;
import com.zhrt.util.Constant;
import com.zhrt.util.ValiUtil;

@Controller
@RequestMapping("/manager/power/role")
public class PowerRoleController extends BaseController {

	@Autowired
	private PowerRoleService powerRoleService;
	@Autowired
	private DtreeService dtreeService;
	@Autowired
	private PowerDomainService powerDomainService;
	@Autowired
	private PlatService platService;

	/**
	 * 指定域下角色列表查询
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
			String domainId = ServletRequestUtils.getStringParameter(request,"domainId","");
			if(StringUtils.isBlank(domainId)){
				domainId = operUserDomainId;
			}
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			paramMap.put("domainId", domainId);
			paramMap.put("status", Constant.STATUS_VALID);
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if (page.isAutoCount()) {
				int totalCount = powerRoleService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<PowerRole> entityList = powerRoleService.getList(paramMap);
			view.addObject("entityList","entityList");
			page.setResult(entityList);
			
			view.addObject("domainId", domainId);
			view.addObject("page", page);
			view.addObject("toPage","../power/rolelist.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return view;
	}
	
	
	/**
	 * 当前登录域下角色列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView findRoleListByDomain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String domainIdSession = getSessionUser().getDomainId();
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			paramMap.put("domainId", domainIdSession);
			paramMap.put("status", Constant.STATUS_VALID);
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if (page.isAutoCount()) {
				int totalCount = powerRoleService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<PowerRole> entityList = powerRoleService.getList(paramMap);
			view.addObject("entityList","entityList");
			page.setResult(entityList);
			
			view.addObject("domainId", domainIdSession);
			view.addObject("page", page);
			view.addObject("toPage","../power/rolelist.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{domainid}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam, @PathVariable String domainid, @PathVariable String m,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try{
			PowerRole powerRole = new PowerRole();
			
			DtreeBo dtreeBo = null;
			if(ValiUtil.valiStr(idparam) && !idparam.equals("0")) {
				powerRole = powerRoleService.getById(idparam);
				dtreeBo = dtreeService.getFunctionTreeEdit(powerRole, getSessionUser());
			}else{
				dtreeBo = dtreeService.getFunctionTreeEdit(null,getSessionUser());
			}
			
			view.addObject("m", m);
			view.addObject("domainId", domainid);
			view.addObject("entity", powerRole);
			view.addObject("dtreeBo", dtreeBo);
			view.addObject("toPage","../power/rolemodify.jsp");
			
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
		String result = "";
		
		try {
			String platid = getSessionUser().getPlatId().toString();
			String domainId =  getSessionUser().getDomainId().toString();
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			String roleName = ServletRequestUtils.getStringParameter(request,"name","");
			String[] funids = ServletRequestUtils.getStringParameters(request,"powerCode");
			
			PowerRole entity = null;
			if(!ValiUtil.valiStr(id)) {
				entity = new PowerRole();
			} else {
				entity = powerRoleService.getById(id);
			}
			
			entity.setName(roleName);
			
			if(!ValiUtil.valiStr(id)){
				entity.setStatus(Constant.STATUS_VALID);
				entity.setDomainId(domainId);
				entity.setPlatId(Integer.parseInt(platid));
				
				result = powerRoleService.addRole(entity, funids);
			} else{
				result = powerRoleService.editRole(entity, funids);
			}

		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/power/role/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		String result = "";
		
		try {
			if(ValiUtil.valiStr(idparam)){
				result = powerRoleService.delById(idparam);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/role/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delmul(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		String result = "";
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length>0){
				result = powerRoleService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/role/index"));
	}
	
}

