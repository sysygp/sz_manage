package com.zhrt.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.power.entity.PowerUser;
import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.system.util.http.LocalAndNetUrlUtil;
import com.system.util.json.JsonDateValueProcessor;
import com.system.util.property.PropertiesConfigDynamic;
import com.zhrt.bo.InfoResVO;
import com.zhrt.bo.ResBaseVO;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.AppVersionInfo;
import com.zhrt.entity.ChannelAppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.CpInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.entity.Plat;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.AppVersionInfoService;
import com.zhrt.service.ChannelAppInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.ChargeFileService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.service.PlatService;
import com.zhrt.service.SeqService;
import com.zhrt.util.Constant;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/manager/zhrt/channelappinfo")
public class ChannelAppInfoController extends BaseController {

	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	@Autowired
	private PlatService platService;
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private SeqService seqService;
	@Autowired
	private ChargeFileService chargeFileService;
	@Autowired
	private OperLogService operLogService;

	/**
	 * 列表查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			Map paramMap = new HashMap();
			String channelId = request.getParameter("channelId");
			String cpId = request.getParameter("cpId");
			String appId = request.getParameter("appId");
			paramMap.put("channelId", channelId);
			paramMap.put("cpId", cpId);
			paramMap.put("appId", appId);
			
			view.addObject("channelId", channelId);
			view.addObject("cpId", cpId);
			
			Page page = new Page(20);
			String pageNoParam = ServletRequestUtils.getStringParameter(request, "pageNo", "");
			String orderByParam = ServletRequestUtils.getStringParameter(request, "orderBy", "");
			String orderParam = ServletRequestUtils.getStringParameter(request, "order", "");
			page.setPage(page, paramMap, pageNoParam, orderByParam, orderParam);
			
			if (page.isAutoCount()) {
				int totalCount = channelAppInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<ChannelAppInfo> channelAppInfoList = channelAppInfoService.getList(paramMap);
			view.addObject("channelAppInfoList", channelAppInfoList);
			page.setResult(channelAppInfoList);
			
			
			List<ChannelInfo> channelList = channelInfoService.getList();
			view.addObject("channelList", channelList);
			
			Map<String,ChannelInfo> channelMap = new HashMap<String,ChannelInfo>();
			for(ChannelInfo channelInfo:channelList){
				channelMap.put(channelInfo.getId(), channelInfo);
			}
			view.addObject("channelMap", channelMap);
			
			//cp列表
			List<CpInfo> cpList = cpInfoService.getList();
			view.addObject("cpList", cpList);
			
			//产品列表
			if(StringUtils.isNotEmpty(cpId)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cpId", cpId);
				List<AppInfo> appList = appInfoService.getList(map);
				view.addObject("appList", appList);
			}
			
			view.addObject("page", page);
			view.addObject("toPage", "../channelapp/channelappinfolist.jsp");
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
	 * 
	 * @param idparam
	 * @param m
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/get/{idparam}/{m}")
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam, @PathVariable String m, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			
			String cpId = null;
			ChannelAppInfo entity = null;
			
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = channelAppInfoService.getById(idparam);
				cpId = entity.getCpId();
			}else{
				entity = new ChannelAppInfo();
			}
			view.addObject("entity", entity);
			
			List<ChannelInfo> channelList = channelInfoService.getList();
			view.addObject("channelList", channelList);
			
			//cp列表
			List<CpInfo> cpList = cpInfoService.getList();
			view.addObject("cpList", cpList);
			
			//产品列表
			if(StringUtils.isNotEmpty(cpId)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cpId", cpId);
				List<AppInfo> appList = appInfoService.getList(map);
				view.addObject("appList", appList);
			}
			
			if("v".equals(m)){
				String chargefile = getChargeFile(entity.getChannelId(),entity.getAppId());
				view.addObject("chargefile", chargefile);
			}
			
			
			view.addObject("toPage", "../channelapp/channelappinfomodify.jsp");
			view.addObject("m", m);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}

	/**
	 * 修改，只修改渠道产品的状态
	 * 
	 * @param channelInfo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(ChannelAppInfo channelAppInfo,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_MODIFY);
		log.setOperUser(user.getLoginName());
		try {
			AppInfo appInfo = appInfoService.getById(channelAppInfo.getAppId());
			log.setOperDesc("渠道产品："+appInfo.getAppName());
			
			channelAppInfo.setAppName(appInfo.getAppName());
			channelAppInfo.setLastUpdatePerson("admin");
			channelAppInfo.setLastUpdateTime(DateUtil.getCurDate());
			
			if(StringUtils.isNotBlank(channelAppInfo.getId())){
				channelAppInfoService.update(channelAppInfo);
			}else{
				Integer chanAppVerSeq = seqService.getNextByName("chanAppVerSeq");
				channelAppInfo.setChanAppVerSeq(chanAppVerSeq);
				channelAppInfoService.add(channelAppInfo);
			}
			
			
			log.setOperResult(Constant.DEAL_OK);
		} catch (Exception e) {
			log.setOperResult(Constant.DEAL_ERR);
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/channelappinfo/index?channelId="+channelAppInfo.getChannelId()));
	}

	@RequestMapping(value = "/pak/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView pak(@PathVariable String idparam, @PathVariable String m, HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		//session用户信息
		PowerUser user = getSessionUser();
		String platId = user.getPlatId().toString();
		Plat plat = platService.getByplatId(platId);
		String platKey = plat.getPlatkey();
		
		//操作日志信息
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_ADD);
		log.setOperUser(user.getLoginName());
		//渠道信息
		String channelId = ServletRequestUtils.getStringParameter(request, "channelId");
		try {
			ChannelInfo channelInfo = channelInfoService.getById(channelId);
			//需要打包的产品版本id
			String[] ids = ServletRequestUtils.getStringParameters(request, "ids");
			
			for (String verId : ids) {
				//产品版本信息
				AppVersionInfo appVerInfo = appVersionInfoService.getById(verId);
				//产品信息
				AppInfo appInfo = appInfoService.getById(appVerInfo.getAppId());
				String myepayChanAppId = appInfo.getMyepayChanAppId();
				String myepayAppId = appInfo.getMyepayAppId();
				Integer chanAppVerSeq = seqService.getNextByName("chanAppVerSeq");
				String appVerId = appVerInfo.getId();
				String sdkVer = appVerInfo.getSdkVer();
				
				log.setOperDesc("渠道产品："+appInfo.getAppName());
				
				if (StringUtils.isNotBlank(m) && (m.equals("1") || m.equals("2")) 
						&& appVerInfo != null && StringUtils.isNotBlank(appVerInfo.getVerDownUrl())) {


					ChannelAppInfo channelAppInfo = new ChannelAppInfo();
					channelAppInfo.setPlatId(platId);
					channelAppInfo.setChanAppVerSeq(chanAppVerSeq);
					channelAppInfo.setChannelId(channelId);
					channelAppInfo.setCpId(appInfo.getCpId());
					channelAppInfo.setAppId(appInfo.getId());
					channelAppInfo.setVerNumber(appVerInfo.getVerNumber());
					channelAppInfo.setVerId(appVerId);
					channelAppInfo.setAppName(appInfo.getAppName());
					channelAppInfo.setAppType(appInfo.getAppType());
					channelAppInfo.setSdkVer(appVerInfo.getSdkVer());
					channelAppInfo.setCreatePerson(user.getLoginName());
					channelAppInfo.setCreateTime(DateUtil.getCurDate());
					channelAppInfo.setLastUpdatePerson(user.getLoginName());
					channelAppInfo.setLastUpdateTime(DateUtil.getCurDate());
					channelAppInfo.setMainFlag(channelInfo.getMainFlag());
					channelAppInfo.setFileName(appVerInfo.getVerPackName());
					channelAppInfo.setFileType(appVerInfo.getVerPackFileType());
					channelAppInfo.setFileLen(appVerInfo.getVerPackFileLength());
					channelAppInfo.setMyepaySdkVer(appInfo.getMyepaySdkVer());
					channelAppInfo.setMyepayChanAppId(myepayChanAppId);
					channelAppInfo.setMyepayChannelId(channelInfo.getChannelId());
					channelAppInfo.setMyepayChannelCode(channelInfo.getChannelCode());
					channelAppInfo.setMyepayAppId(appInfo.getMyepayAppId());
					channelAppInfoService.add(channelAppInfo);
					log.setOperResult(Constant.DEAL_OK);
				
				
				}
			}
			
		} catch (Exception e) {
			log.setOperResult(Constant.DEAL_ERR);
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/channelappinfo/index?channelId="+channelId));
	}
	

	/**
	 * 单条记录删除
	 * 
	 * @param idparam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		Map<String, Object> tipInfoMap = new HashMap<String, Object>();
		
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_DEL);
		log.setOperUser(user.getLoginName());
		String channelId = ServletRequestUtils.getRequiredStringParameter(request, "channelId");
		try {
			if (StringUtils.isNotBlank(idparam)) {
//				channelAppInfoService.delById(idparam);
				ChannelAppInfo param = channelAppInfoService.getById(idparam);
				log.setOperDesc("渠道产品："+param.getAppName());
				//上线状态时不能删除
				if(String.valueOf(Constant.STATUS_VALID).equals(param.getStatus())){
					log.setOperResult(Constant.DEAL_ERR);
					logger.error("渠道产品："+param.getAppName()+"处于上线状态，不能删除！");
					tipInfoMap.put(ERROR_INFO, "渠道产品："+param.getAppName()+"处于上线状态，不能删除！");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "渠道产品："+param.getAppName()+"处于上线状态，不能删除！");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}else{
					param.setId(idparam);
					param.setStatus(String.valueOf(Constant.STATUS_DEL));
					param.setLastUpdatePerson("admin");
					param.setLastUpdateTime(DateUtil.getCurDate());
					channelAppInfoService.update(param);
					log.setOperResult(Constant.DEAL_OK);
				}
			}
		} catch (Exception e) {
			log.setOperResult(Constant.DEAL_ERR);
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/channelappinfo/index?channelId="+channelId));
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> tipInfoMap = new HashMap<String, Object>();
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request, "ids");
			if (ids != null && ids.length > 0) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ids", ids);
				channelAppInfoService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response, INDEX, tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/channelappinfo/index"));
	}
	
	/**
	 * 
	 * 查询产品的所有版本
	 * 创建人：
	 * 创建时间: 2015年7月24日
	 * 修改人：
	 * 修改时间：
	 * @param myepayAppId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/getByAppId")
	@ResponseBody
	public ModelAndView getByAppId(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();
		try {
			String appId = ServletRequestUtils.getRequiredStringParameter(request, "appId");
			String status = ServletRequestUtils.getRequiredStringParameter(request, "status");
			if(StringUtils.isNotBlank(appId)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("appId", appId);
				param.put("status", status);
				List<AppVersionInfo> result = appVersionInfoService.getList(param);
				map.put("result", result);
			}
			view = returnInfoJson(response, map);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
		}
		return view;
	}
	
	
	@RequestMapping(value="/download",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView download(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		ChannelAppInfo entity = channelAppInfoService.getById(id);;
		if(entity==null){
			logger.error("文件不存在");
		}else{
			String downUrl = entity.getDownUrl();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
	        	request.setCharacterEncoding("UTF-8");
	    		String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_channel_appl_filepath")+File.separator;
	    		String downloadPath = filePath+downUrl;
	    		File downloadFile = new File(downloadPath);
		        bis = new BufferedInputStream(new FileInputStream(downloadFile)); 
		        bos = new BufferedOutputStream(response.getOutputStream());  
	    		//保存文件时的文件名
		        String fileName = entity.getFileName();
//	    		fileName = fileName.substring(13);
	    		response.setContentType("application/x-msdownload;");  
				response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		        response.setHeader("Content-Length", String.valueOf(downloadFile.length()));
	    		logger.info("开始下载文件..."+downloadPath);
	            byte[] buff = new byte[2048];  
	            int bytesRead;  
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	                bos.write(buff, 0, bytesRead);  
	            }  
	            logger.info("文件下载完成...");
			} catch (UnsupportedEncodingException e) {
				logger.error(ERROR_INFO_CONTENT);
				logger.error(e.getMessage());
			} catch (FileNotFoundException e) {
				logger.error(ERROR_INFO_CONTENT);
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(ERROR_INFO_CONTENT);
				logger.error(e.getMessage());
			}  finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						logger.error(ERROR_INFO_CONTENT);
						logger.error(e.getMessage());
					}
				}
				if(bos!=null){
					try {
						bos.close();
					} catch (IOException e) {
						logger.error(ERROR_INFO_CONTENT);
						logger.error(e.getMessage());
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * 批量更新渠道运营产品状态
	 * 创建人：
	 * 创建时间: 2015年9月11日 下午5:16:51
	 * 修改人：
	 * 修改时间：
	 * @return
	 */
	@RequestMapping(value="/updateBatch",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView updateBatch(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String channelId = "";
		try {
			channelId = ServletRequestUtils.getRequiredStringParameter(request, "channelId");
			String[] ids = ServletRequestUtils.getRequiredStringParameters(request, "ids");
			map.put("ids", ids);
			map.put("status", Constant.STATUS_INVALID);
			channelAppInfoService.updateBatch(map);
		} catch (ServletRequestBindingException e) {
			logger.error("批量更新失败");
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/channelappinfo/index?channelId="+channelId));
	}
	
	private String getChargeFile(String channelId,String appId){
		
		//计费方案信息
		List<InfoResVO> infoResVOList = chargeFileService.getChargeFile(channelId,appId);
		ResBaseVO resBaseVO = new ResBaseVO();
		resBaseVO.setCode("10000");
		resBaseVO.setInfo(infoResVOList);
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor()); 
		JSONObject jsonObject = JSONObject.fromObject(resBaseVO,jsonConfig);
		
		return jsonObject.toString();
	}
}
