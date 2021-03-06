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

import com.power.entity.PowerDomain;
import com.power.service.PowerDomainService;
import com.system.controller.BaseController;
import com.system.page.Page;
import com.zhrt.entity.Plat;
import com.zhrt.service.PlatService;
import com.zhrt.util.ValiUtil;

@Controller
@RequestMapping("/manager/power/domain")
public class PowerDomainController extends BaseController {

	@Autowired
	private PowerDomainService powerDomainService;
	@Autowired
	private PlatService platService;

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
			String domainIdSession = getSessionUser().getDomainId();
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			paramMap.put("parentId", domainIdSession);
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if (page.isAutoCount()) {
				int totalCount = powerDomainService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<PowerDomain> entityList = powerDomainService.getList(paramMap);
			view.addObject("entityList","entityList");
			page.setResult(entityList);
			
			view.addObject("page", page);
			view.addObject("toPage","../power/domainlist.jsp");
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam, @PathVariable String m, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			PowerDomain entity = new PowerDomain();
			
			if(ValiUtil.valiStr(idparam) && !idparam.equals("0")) {
				entity = powerDomainService.getById(idparam);				
			}
			List<Plat> platList = platService.getList();
			
			view.addObject("m",m);
			view.addObject("toPage","../power/domainmodify.jsp");
			view.addObject("entity", entity);
			view.addObject("platList", platList);
			
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
			String name = ServletRequestUtils.getStringParameter(request,"name","");
			
			PowerDomain entity = null;
			if(!ValiUtil.valiStr(id)) {
				entity = new PowerDomain();
			} else{
				entity = powerDomainService.getById(id);
			}
			
			entity.setName(name);
			entity.setParentId(getSessionUser().getDomainId());
			entity.setPlatId(getSessionUser().getPlatId());
			
			if(!ValiUtil.valiStr(id)) {
				powerDomainService.add(entity);
			} else {
				powerDomainService.update(entity);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/domain/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			if(ValiUtil.valiStr(idparam)){
				powerDomainService.delById(idparam);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/domain/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delmul(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				powerDomainService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/power/domain/index"));
	}
}
