package com.zhrt.action;

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
import com.zhrt.entity.BehaviorType;
import com.zhrt.service.BehaviorTypeService;


@Controller
@RequestMapping("/manager/zhrt/behaviortype")
public class BehaviorTypeController extends BaseController {

	@Autowired
	private BehaviorTypeService behaviorTypeService;

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
			String pn = request.getParameter("pageNo");
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if (page.isAutoCount()) {
				int totalCount = behaviorTypeService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<BehaviorType> behavTypeList = behaviorTypeService.getList(paramMap);
			view.addObject("behavTypeList","behavTypeList");
			page.setResult(behavTypeList);
			
			view.addObject("page", page);
			
			view.addObject("toPage","../behavior/behaviortypelist.jsp");
			
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
			BehaviorType entity = new BehaviorType();
			
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = behaviorTypeService.getById(idparam);				
			}
			
			view.addObject("m",m);
			view.addObject("toPage","../behavior/behaviortypemodify.jsp");
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
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			String behavId = ServletRequestUtils.getStringParameter(request,"behavId","");
			String behavName = ServletRequestUtils.getStringParameter(request,"behavName","");
			String remark = ServletRequestUtils.getStringParameter(request,"remark","");
			
			BehaviorType entity = null;
			if (StringUtils.isBlank(id)) {
				entity = new BehaviorType();
				
				//当前登录者
				entity.setCuser("admin");
				entity.setCtime(DateUtil.getCurDate());
			} else {
				entity = behaviorTypeService.getById(id);
				
				//当前登录者
				entity.setUuser("admin");
				entity.setUtime(DateUtil.getCurDate());
			}
			
			entity.setBehavId(behavId);
			entity.setBehavName(behavName);
			entity.setRemark(remark);
			
			if (StringUtils.isBlank(id)) {
				behaviorTypeService.add(entity);
			} else {
				behaviorTypeService.update(entity);
			}
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/behaviortype/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			if(StringUtils.isNotBlank(idparam)){
				behaviorTypeService.delById(idparam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/behaviortype/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids.length > 0){
				behaviorTypeService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/behaviortype/index"));
	}
}
