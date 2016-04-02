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
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.Constant;
import com.system.util.date.DateUtil;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.PropInfo;
import com.zhrt.entity.Spcode;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.PropInfoService;
import com.zhrt.service.SpcodeService;

@Controller
@RequestMapping("/manager/zhrt/propinfo")
public class PropInfoController extends BaseController{

	@Autowired
	private PropInfoService propInfoService;
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private SpcodeService spcodeService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			Map paramMap = new HashMap();
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = propInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<PropInfo> propInfoList = propInfoService.getList(paramMap);
			view.addObject("propInfoList",propInfoList);
			page.setResult(propInfoList);
			view.addObject("page", page);
			view.addObject("toPage","../propinfo/propinfolist.jsp");
		} catch (Exception e) {
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			logger.error(ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return view;
	}
	
	/**
	 * 鍗曟潯璁板綍鏌ヨ
	 * @param idparam
	 * @param m
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/get/{idparam}/{m}")
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			
			Map okSpcodeMap = new HashMap();
			PropInfo entity = new PropInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = propInfoService.getById(idparam);	
				String okSpcodeStr = entity.getSpcodeIds();
				if(StringUtils.isNotBlank(okSpcodeStr)){
					String[] okSpcodeArray = okSpcodeStr.split(",");
					for(String okSpcode : okSpcodeArray){
						okSpcodeMap.put(okSpcode, "1");
					}
				}
			}
			view.addObject("entity", entity);
			view.addObject("okSpcodeMap", okSpcodeMap);
			
			List<AppInfo> appInfoList = appInfoService.getList();
			view.addObject("appInfoList", appInfoList);
			
			Map paramMap = new HashMap();
			paramMap.put(Constant.DATABASE_ORDERBY_FILED, "spId");
			paramMap.put(Constant.DATABASE_ORDER_FILED, "asc");
			List<Spcode> spCodeList = spcodeService.getList(paramMap);
			view.addObject("spCodeList",spCodeList);
			
			view.addObject("m",m);
			view.addObject("toPage","../propinfo/propinfomodify.jsp");
			
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return view;
	}
	
	/**
	 * 鏂板鎴栦慨鏀�	 * @param appInfo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(PropInfo propInfo,HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			String id = propInfo.getId();
			if (StringUtils.isBlank(id)) {
				//鑾峰彇褰撳墠鐧诲綍鐢ㄦ埛
				propInfo.setCreatePerson("admin");
				propInfo.setCreateTime(DateUtil.getCurDate());
				propInfoService.add(propInfo);
			} else{
				//褰撳墠鐧诲綍鑰�				propInfo.setLastUpdatePerson("admin");
				propInfo.setLastUpdateTime(DateUtil.getCurDate());
				propInfoService.update(propInfo);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/propinfo/index"));
	}
	
	/**
	 * 鍗曟潯璁板綍鍒犻櫎
	 * @param idparam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			if(StringUtils.isNotBlank(idparam)){
				propInfoService.delById(idparam);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/propinfo/index"));
	}
	
	/**
	 * 鎵归噺鍒犻櫎
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
				propInfoService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO,ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/propinfo/index"));
	}
}
