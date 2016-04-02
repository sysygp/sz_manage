package com.zhrt.action;

import java.util.ArrayList;
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

import com.power.entity.PowerUser;
import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.entity.SpcodeConvert;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.service.SpcodeConvertService;
import com.zhrt.util.Constant;

@Controller
@RequestMapping("/manager/zhrt/spcodeConvert")
public class SpcodeConvertController extends BaseController{

	@Autowired
	private SpcodeConvertService spcodeConvertService;
	@Autowired
	private AppInfoService appInfoService;
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
	public ModelAndView index(SpcodeConvert spcodeConvert,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			Map paramMap = new HashMap();
			paramMap.put("spCodeId", spcodeConvert.getSpCodeId());
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = spcodeConvertService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			//关联查询出运营的产品数量
			List<SpcodeConvert> cpInfoList = spcodeConvertService.getForPage(paramMap);
			view.addObject("cpInfoList",cpInfoList);
			page.setResult(cpInfoList);
			view.addObject("page", page);
			view.addObject("toPage","../spcodeConvert/list.jsp");
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	/**
	 * 单条记录查询
	 * @param idparam
	 * @param m
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/get/{idparam}/{m}")
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			SpcodeConvert entity = new SpcodeConvert();
			List<AppInfo> appList = new ArrayList<>();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = spcodeConvertService.getById(idparam);
				//查询cp下运营的产品
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("cpId", idparam);
				param.put("status", Constant.STATUS_VALID);
				appList = appInfoService.getList(param);
			}
			view.addObject("appList",appList);
			view.addObject("m",m);
			view.addObject("toPage","../spcodeConvert/modify.jsp");
			view.addObject("entity", entity);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	/**
	 * 新增或修改
	 * @param spcodeConvert
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(SpcodeConvert spcodeConvert,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		PowerUser user = getSessionUser();
		//生成操作日志
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperUser(user.getLoginName());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		try {
			String id = spcodeConvert.getId();
			if (StringUtils.isBlank(id)) {
				spcodeConvertService.add(spcodeConvert);
				log.setOperResult(Constant.DEAL_OK);
				
			} else{
				spcodeConvertService.update(spcodeConvert);
				log.setOperResult(Constant.DEAL_OK);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			log.setOperResult(Constant.DEAL_ERR);
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/spcodeConvert/index"));
	}
	
	/**
	 * 单条记录删除
	 * @param idparam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		PowerUser user = getSessionUser();
		//生成操作日志
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperUser(user.getLoginName());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_DEL);
		try {
			if(StringUtils.isNotBlank(idparam)){
				spcodeConvertService.delById(idparam);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			//记录操作日志
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/spcodeConvert/index"));
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ids", ids);
				spcodeConvertService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO,ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/spcodeConvert/index"));
	}
}
