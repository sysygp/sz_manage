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
import com.system.util.datasource.DynamicDataSource;
import com.zhrt.entity.Subcode;
import com.zhrt.service.SubcodeService;

@Controller
@RequestMapping("/manager/zhrt/subcode")
public class SubcodeController extends BaseController {

	@Autowired
	private SubcodeService subcodeService;

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
				int totalCount = subcodeService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<Subcode> entityList = subcodeService.getList(paramMap);
			view.addObject("entityList","entityList");
			page.setResult(entityList);
			
			view.addObject("page", page);
			view.addObject("toPage","../mobilephone/subcodelist.jsp");
			
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
			Map paramMap = new HashMap();
			Subcode entity = new Subcode();
			
			if (StringUtils.isNotBlank(idparam) && idparam.contains("_")) {
				String[] idElements = idparam.split("_");
				if(idElements.length == 2){
					String id = idElements[0];
					String subcode = idElements[1];
					
					paramMap.put("id", id);
					paramMap.put("subcode", subcode);
					entity = subcodeService.get(paramMap);
				}
			}
			
			view.addObject("m",m);
			view.addObject("toPage","../mobilephone/subcodemodify.jsp");
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
			Map paramMap = new HashMap();
			
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			String subcode = ServletRequestUtils.getStringParameter(request,"subcode","");
			
			Subcode entity = null;
			if (StringUtils.isBlank(id)) {
				entity = new Subcode();
			} else {
				paramMap.put("id", id);
				paramMap.put("subcode", subcode);
				entity = subcodeService.get(paramMap);
			}
			
			entity.setSubcode(subcode);
			entity.setLatinProvince("测试");
			entity.setLatinCity("市民测试");
			entity.setLatinId(102061);
			entity.setUserType(21);
			entity.setOperator(0);
			
			if (StringUtils.isBlank(id)) {
				subcodeService.add(entity);
			} else {
				entity.setId(Integer.parseInt(id));
				subcodeService.update(entity);
			}
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/subcode/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
			
		try {
			Map paramMap = new HashMap();
			
//			idparam = "1_111";
			
			if (StringUtils.isNotBlank(idparam) && idparam.contains("_")) {
				String[] idElements = idparam.split("_");
				if(idElements.length == 2){
					String id = idElements[0];
					String subcode = idElements[1];
					
					paramMap.put("id", id);
					paramMap.put("subcode", subcode);
					
					subcodeService.delById(paramMap);
				}
			}
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/subcode/index"));
	}
	
//	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
//	@ResponseBody
//	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
//		
//		try {
//			Map paramMap = new HashMap();
//			
////			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
//			String[] ids = {"3_333","2_222"};
//			
//			if(ids!=null && ids.length > 0){
//				subcodeService.delByIds(paramMap);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
//			return this.returnInfoJsp(response,INDEX,tipInfoMap);
//		}
//		
//		return new ModelAndView(new RedirectView("/manager/zhrt/subcode/index"));
//	}
}
