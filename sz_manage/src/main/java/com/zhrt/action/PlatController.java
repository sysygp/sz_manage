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
import com.zhrt.entity.Plat;
import com.zhrt.service.PlatService;
import com.zhrt.service.SeqService;


@Controller
@RequestMapping("/manager/zhrt/plat")
public class PlatController extends BaseController {

	@Autowired
	private PlatService platService;
	@Autowired
	private SeqService seqService;

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
				int totalCount = platService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<Plat> platList = platService.getList(paramMap);
			view.addObject("platList","platList");
			page.setResult(platList);
			
			view.addObject("page", page);
			
			view.addObject("toPage","../plat/platlist.jsp");
			
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
			Plat entity = new Plat();
			
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = platService.getById(idparam);				
			}
			
			view.addObject("m",m);
			view.addObject("toPage","../plat/platmodify.jsp");
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
	public ModelAndView modify(Plat entity,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
			
		try {
			String id = entity.getId();
			if (StringUtils.isBlank(id)) {
				Date date = DateUtil.getCurDate();
				Integer verSeq = seqService.getNextByName("platseq");
				entity.setPlatid(verSeq);
				entity.setCuser("admin");
				entity.setCtime(date);
				entity.setUuser("admin");
				entity.setUtime(date);
				
				platService.add(entity);
			} else{
				/*entity = platService.getById(id);*/
				//当前登录者
				entity.setUuser("admin");
				entity.setUtime(DateUtil.getCurDate());
				platService.update(entity);
			}
			
			/*entity.setPlatname(platname);
			entity.setRemark(remark);*/
			/*
			if (StringUtils.isBlank(id)) {
				platService.add(entity);
			} else {
				platService.update(entity);
			}*/
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/plat/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
			
		try {
			if(StringUtils.isNotBlank(idparam)){
				platService.delById(idparam);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/plat/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				platService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/plat/index"));
	}
}
