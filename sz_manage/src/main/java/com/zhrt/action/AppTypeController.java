package com.zhrt.action;

import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.zhrt.entity.AppTypeInfo;
import com.zhrt.service.AppTypeService;
import com.zhrt.util.CacheUtil;
import com.zhrt.util.Constant;
import com.zhrt.util.ErrorCode;


@Controller
@RequestMapping("/manager/zhrt/apptype")
public class AppTypeController extends BaseController {

	@Autowired
	private AppTypeService appTypeService;

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = appTypeService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<AppTypeInfo> appTypeList = appTypeService.getList(paramMap);
			view.addObject("appTypeList",appTypeList);
			page.setResult(appTypeList);
			view.addObject("page", page);
			view.addObject("toPage","../apptype/apptypelist.jsp");
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			AppTypeInfo entity = new AppTypeInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = appTypeService.getById(idparam);				
			}
			view.addObject("m",m);
			view.addObject("toPage","../apptype/apptypemodify.jsp");
			view.addObject("entity", entity);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(AppTypeInfo entity,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		try {
			String id = entity.getId();
			if (StringUtils.isBlank(id)) {
				Date date = DateUtil.getCurDate();
				entity.setStatus(String.valueOf(Constant.STATUS_VALID));
				entity.setCreateUser("admin");
				entity.setCreateTime(date);
				entity.setUpdateUser("admin");
				entity.setUpdateTime(date);
				appTypeService.add(entity);
				int code = CacheUtil.cacheWrite(CacheUtil.APPSORT, true);
				if(ErrorCode.SYSTEM_ERROR==code){
					logger.error("缓存系统更新缓存失败");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "缓存系统更新缓存失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
			} else{
				//当前登录者
				entity.setUpdateUser("admin");
				entity.setUpdateTime(DateUtil.getCurDate());
				appTypeService.update(entity);
				int code = CacheUtil.cacheWrite(CacheUtil.APPSORT, true);
				if(ErrorCode.SYSTEM_ERROR==code){
					logger.error("缓存系统更新缓存失败");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "缓存系统更新缓存失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/apptype/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		try {
			if(StringUtils.isNotBlank(idparam)){
//				appTypeService.delById(idparam);
				AppTypeInfo param = new AppTypeInfo();
				param.setId(idparam);
				param.setStatus(String.valueOf(Constant.STATUS_DEL));
				param.setUpdateUser("admin");
				param.setUpdateTime(DateUtil.getCurDate());
				appTypeService.update(param);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/apptype/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				appTypeService.delByIds(ids);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/apptype/index"));
	}
}
