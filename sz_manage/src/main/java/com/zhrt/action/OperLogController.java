package com.zhrt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.zhrt.entity.OperLog;
import com.zhrt.service.OperLogService;

@Controller
@RequestMapping("/manager/zhrt/operlog")
public class OperLogController extends BaseController{

	@Autowired
	private OperLogService operLogService;
	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		try {
			String startTime = ServletRequestUtils.getStringParameter(request, "startTime");
			String endTime = ServletRequestUtils.getStringParameter(request, "endTime");
			String bussName = ServletRequestUtils.getStringParameter(request, "bussName");
			String operType = ServletRequestUtils.getStringParameter(request, "operType");
			Map paramMap = new HashMap();
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("bussName", bussName);
			paramMap.put("operType", operType);
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = operLogService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<OperLog> logList = operLogService.getList(paramMap);
			view.addObject("logList",logList);
			page.setResult(logList);
			view.addObject("startTime", startTime);
			view.addObject("endTime", endTime);
			view.addObject("bussName", bussName);
			view.addObject("operType", operType);
			view.addObject("page", page);
			view.addObject("toPage","../operlog/operloglist.jsp");
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
}
