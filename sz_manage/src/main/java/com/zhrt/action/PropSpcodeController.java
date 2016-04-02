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
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.Constant;
import com.system.util.date.DateUtil;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.PropInfo;
import com.zhrt.entity.PropSpcode;
import com.zhrt.entity.Spcode;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.PropInfoService;
import com.zhrt.service.PropSpcodeService;
import com.zhrt.service.SpcodeService;

@Controller
@RequestMapping("/manager/zhrt/propspcode")
public class PropSpcodeController extends BaseController{

	@Autowired
	private PropSpcodeService propSpcodeService;
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private SpcodeService spcodeService;
	@Autowired
	private PropInfoService propInfoService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		List<PropSpcode> propSpcodeList = new ArrayList<PropSpcode>();
		Map<String,Spcode> spCodeMap = new HashMap<String,Spcode>();
		PropInfo propInfo = new PropInfo();
		try {
			String propId = ServletRequestUtils.getStringParameter(request, "propId", "");
			if(StringUtils.isNotBlank(propId)){
				Map paramMap = new HashMap();
				paramMap.put("propId", propId);
				propSpcodeList = propSpcodeService.getList(paramMap);
				
				Map paramMapSpcode = new HashMap();
				paramMapSpcode.put("status", "1");
				List<Spcode> spcodeList = spcodeService.getList(paramMapSpcode);
				if(spcodeList != null){
					for(Spcode spcode : spcodeList){
						spCodeMap.put(spcode.getId(), spcode);
					}
				}
				
				
				propInfo = propInfoService.getById(propId);
			}
			
			view.addObject("propInfo",propInfo);
			view.addObject("propSpcodeList",propSpcodeList);
			view.addObject("spCodeMap",spCodeMap);
			view.addObject("toPage","../propspcode/propspcodelist.jsp");
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
	@RequestMapping(value="/get/{idparam}/{m}/{propId}")
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,@PathVariable String propId,HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView(INDEX);
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			if(StringUtils.isNotBlank(propId)){
				PropSpcode entity = new PropSpcode();
				if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
					entity = propSpcodeService.getById(idparam);	
				}
				view.addObject("entity", entity);
				
				Map paramMapSpcode = new HashMap();
				paramMapSpcode.put("status", "1");
				List<Spcode> spcodeList = spcodeService.getList(paramMapSpcode);
				view.addObject("spcodeList", spcodeList);
				
			}
			
			view.addObject("propId",propId);
			view.addObject("m",m);
			view.addObject("toPage","../propspcode/propspcodemodify.jsp");
			
			
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
	public ModelAndView modify(PropSpcode propSpcode,HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			String id = propSpcode.getId();
			if (StringUtils.isBlank(id)) {
				propSpcode.setCreatePerson("admin");
				propSpcode.setCreateTime(DateUtil.getCurDate());
				propSpcode.setLastUpdatePerson("admin");
				propSpcode.setLastUpdateTime(DateUtil.getCurDate());
				propSpcodeService.add(propSpcode);
			} else{
				propSpcode.setLastUpdatePerson("admin");
				propSpcode.setLastUpdateTime(DateUtil.getCurDate());
				propSpcodeService.update(propSpcode);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/propspcode/index?propId="+propSpcode.getPropId()));
	}
	
	/**
	 * 鍗曟潯璁板綍鍒犻櫎
	 * @param idparam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{idparam}/{propId}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,@PathVariable String propId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			if(StringUtils.isNotBlank(idparam)){
				propSpcodeService.delById(idparam);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/propspcode/index?propId="+propId));
	}
	
	/**
	 * 鎵归噺鍒犻櫎
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delmul/{propId}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(@PathVariable String propId,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ids", ids);
				propSpcodeService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO,ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/propspcode/index?propId="+propId));
	}
}
