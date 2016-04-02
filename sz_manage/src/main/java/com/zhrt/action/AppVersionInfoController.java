package com.zhrt.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.power.entity.PowerUser;
import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.system.util.http.LocalAndNetUrlUtil;
import com.system.util.property.PropertiesConfigDynamic;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.AppVersionInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.entity.Plat;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.AppVersionInfoService;
import com.zhrt.service.ChannelAppInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.service.PlatService;
import com.zhrt.service.PropInfoService;
import com.zhrt.util.CacheUtil;
import com.zhrt.util.Constant;

@Controller
@RequestMapping("/manager/zhrt/appversioninfo")
public class AppVersionInfoController extends BaseController{

	@Autowired
	private AppVersionInfoService appVersionInfoService;
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private ChannelAppInfoService channelAppInfoService;
	@Autowired
	private OperLogService operLogService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private PlatService platService;
	@Autowired
	private PropInfoService propInfoService;
	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(String cpId,AppVersionInfo avi,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		try {
			Map paramMap = new HashMap();
			paramMap.put("verNumber", avi.getVerNumber());
			paramMap.put("status", avi.getStatus());
			paramMap.put("appId", avi.getAppId());
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = appVersionInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<AppVersionInfo> aviList = appVersionInfoService.getList(paramMap);
			view.addObject("aviInfoList",aviList);
			/*List<CpInfo> cpList = cpInfoService.getList();
			view.addObject("cpList", cpList);*/
			/*if(StringUtils.isNotEmpty(cpId)){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cpId", cpId);
				List<AppInfo> appList = appInfoService.getList(map);
				view.addObject("appList", appList);
			}*/
			//从缓存中读取产品信息 json格式的map
			Object obj = CacheUtil.cacheRead(CacheUtil.APP);
			Map<String, Object> appMap = (Map<String, Object>) obj;
			view.addObject("appMap", appMap);
			page.setResult(aviList);
			view.addObject("avi",avi);
			view.addObject("page", page);
			view.addObject("cpId",cpId);
			view.addObject("toPage","../appversioninfo/appversioninfolist.jsp");
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
	public ModelAndView get(@PathVariable String idparam,String appId,@PathVariable String m,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		try {
			AppVersionInfo entity = new AppVersionInfo();
			//查询所有上线的渠道
			Map<String, Object> channelMap = new HashMap<String, Object>();
			channelMap.put("status", Constant.STATUS_VALID);
			List<ChannelInfo> channelList = channelInfoService.getList(channelMap);
			view.addObject("channelList", channelList);
			//查询产品信息
			AppInfo appInfo = appInfoService.getById(appId);
			view.addObject("appInfo", appInfo);	
			//编辑
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = appVersionInfoService.getById(idparam);			
				
				//查询推广该产品版本的上线的渠道
				channelMap.put("verId", idparam);
				List<ChannelInfo> verChannelList = channelInfoService.getByVerId(channelMap);
				Map<String, Object> verChanMap = new HashMap<String, Object>();
				for (ChannelInfo channelInfo : verChannelList) {
					verChanMap.put(channelInfo.getId(), "1");
				}
				view.addObject("verChanMap", verChanMap);
			}else{
				//新增
				//查询产品的所有版本
				Map<String, Object> verMap = new HashMap<String, Object>();
				verMap.put("appId", appId);
				verMap.put("orderBy", "createTime");
				verMap.put("order", "desc");
				List<AppVersionInfo> appVerList = appVersionInfoService.getList(verMap);
				//产品的最新版本
				AppVersionInfo latestAppVersionInfo = new AppVersionInfo();
				if(appVerList!=null&&!appVerList.isEmpty()){
					latestAppVersionInfo = appVerList.get(0);
				}
				//查询推广最新版本的渠道
				channelMap.put("verId", latestAppVersionInfo.getId());
				List<ChannelInfo> verChannelList = channelInfoService.getByVerId(channelMap);
				Map<String, Object> verChanMap = new HashMap<String, Object>();
				for (ChannelInfo channelInfo : verChannelList) {
					verChanMap.put(channelInfo.getId(), "1");
				}
				view.addObject("verChanMap", verChanMap);
			}
			
//			List<AppInfo> appInfoList = appInfoService.getList();
//			view.addObject("appInfoList", appInfoList);
			//查询启用的cp信息
			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", Constant.STATUS_VALID);
			List<CpInfo> cpList = cpInfoService.getList(map);
			view.addObject("cpList", cpList);*/
			
			view.addObject("m",m);
			view.addObject("appId", appId);
			view.addObject("toPage","../appversioninfo/appversioninfomodify.jsp");
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
	 * @param aviInfo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(AppVersionInfo aviInfo/*,@RequestParam("verPackName") MultipartFile verPackName*/,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		PowerUser user = getSessionUser();
		Plat plat = platService.getByplatId(user.getPlatId().toString());
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperUser(user.getLoginName());
		try {
			MultipartFile file = aviInfo.getFile();
			//文件名
			String originalFileName = file.getOriginalFilename();
			int index = originalFileName.indexOf(".");
			//文件类型
			String fileType = originalFileName.substring(index+1);
			if(!"".equals(originalFileName)&&file.getSize()>0){
				//过滤只能上传apk文件
				if(!"apk".equals(fileType)){
					logger.error("文件类型错误，只能上传apk文件");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "文件类型错误，只能上传apk文件");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
				//保存的文件名
				String fileName = System.currentTimeMillis()+originalFileName;
				String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator+fileName;
				//文件下载地址
				String verDownUrl = fileName;
				//保存文件
				file.transferTo(new File(filePath));
				aviInfo.setVerPackName(originalFileName);
				aviInfo.setVerPackFileType(fileType);
				aviInfo.setVerPackFileLength(String.valueOf(file.getSize()));
				aviInfo.setVerDownUrl(verDownUrl);
			}
			
			AppInfo appInfo = appInfoService.getById(aviInfo.getAppId());
			if(appInfo != null){
				aviInfo.setMyepayAppId(appInfo.getMyepayAppId());
			}
			
			//用户勾选的推广渠道id
			String[] channels = ServletRequestUtils.getStringParameters(request, "channels");
			
			String id = aviInfo.getId();
			if (StringUtils.isBlank(id)) {
				log.setOperType(Constant.OPERTYPE_ADD);
				log.setOperDesc(appInfo.getAppName()+" 产品版本："+aviInfo.getVerNumber());
				
				//查询产品的道具信息
				Map<String, Object> propMap = new HashMap<String, Object>();
				propMap.put("appId", aviInfo.getAppId());
				int propCount = propInfoService.getListCount(propMap);
				if(propCount <= 0){
					log.setOperResult(Constant.DEAL_ERR);
					logger.error("该产品下没有道具，请先添加道具信息");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "该产品下没有道具，请先添加道具信息");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
				
				aviInfo.setVerSource("1");
				aviInfo.setStatus("1");
				//获取当前登录用户
				aviInfo.setCreatePerson("admin");
				aviInfo.setCreateTime(DateUtil.getCurDate());
				aviInfo.setLastUpdatePerson("admin");
				aviInfo.setLastUpdateTime(DateUtil.getCurDate());
				appVersionInfoService.add(aviInfo,channels,user,plat);
				log.setOperResult(Constant.DEAL_OK);
			} else{
				//查询原来的文件版本
				AppVersionInfo oldAppVersionInfo = appVersionInfoService.getById(id);
				log.setOperType(Constant.OPERTYPE_MODIFY);
				log.setOperDesc(appInfo.getAppName()+" 产品版本："+oldAppVersionInfo.getVerNumber());
				//如果上传了新文件，将原来的文件删除
				if(!"".equals(originalFileName)&&file.getSize()>0){
					String fileName = oldAppVersionInfo.getVerDownUrl();
					String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator+fileName;
					File oldfile = new File(filePath);
					if(oldfile.exists()){
						oldfile.delete();
					}
				}
				//当前登录者
				aviInfo.setLastUpdatePerson("admin");
				aviInfo.setLastUpdateTime(DateUtil.getCurDate());
				appVersionInfoService.update(aviInfo);
				log.setOperResult(Constant.DEAL_OK);
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
		return new ModelAndView(new RedirectView("/manager/zhrt/appversioninfo/index?appId="+aviInfo.getAppId()));
	}
	/**
	 * 
	 * 产品版本下载
	 * 创建人：
	 * 创建时间: 2015年7月23日
	 * 修改人：
	 * 修改时间：
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/download",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView download(@RequestParam String id,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		AppVersionInfo appVersionInfo = appVersionInfoService.getById(id);
		if(appVersionInfo==null){
			logger.error("产品版本不存在");
		}else{
			String fileName = appVersionInfo.getVerPackName();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
	        	request.setCharacterEncoding("UTF-8");
	    		String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator;
	    		String downloadPath = filePath+appVersionInfo.getVerDownUrl();
	    		File downloadFile = new File(downloadPath);
		        bis = new BufferedInputStream(new FileInputStream(downloadFile)); 
		        bos = new BufferedOutputStream(response.getOutputStream());  
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
		
		String appId = ServletRequestUtils.getRequiredStringParameter(request, "appId");
		
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_DEL);
		log.setOperUser(user.getLoginName());
		try {
			if(StringUtils.isNotBlank(idparam)){
				AppVersionInfo appVersionInfo = appVersionInfoService.getById(idparam);
							
				
				AppInfo appInfo = appInfoService.getById(appId);
				log.setOperDesc(appInfo.getAppName()+" 产品版本："+appVersionInfo.getVerNumber());
				//查询是否有渠道占用
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("verId", idparam);
				map.put("status", Constant.STATUS_VALID);
				int count = channelAppInfoService.getListCount(map);
				if(count>0){
					log.setOperResult(Constant.DEAL_ERR);
					logger.info("产品版本有渠道在运营，删除产品版本失败");
					
					redirectAttributes.addFlashAttribute(ERROR_INFO, "产品版本有渠道在运营，删除产品版本失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}else{
					if(!appVersionInfo.getStatus().trim().equals(Constant.STATUS_INVALID+"")){
						log.setOperResult(Constant.DEAL_ERR);
						logger.info("产品版本处于上线状态，无法删除!");
						
						redirectAttributes.addFlashAttribute(ERROR_INFO, "产品版本处于上线状态，无法删除!");
						return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
					}						
//					appVersionInfoService.delById(idparam);
					AppVersionInfo param = new AppVersionInfo();
					param.setId(idparam);
					param.setStatus(String.valueOf(Constant.STATUS_DEL));
					param.setLastUpdatePerson("admin");
					param.setLastUpdateTime(DateUtil.getCurDate());
					appVersionInfoService.update(param);
					log.setOperResult(Constant.DEAL_OK);
					String fileName = appVersionInfo.getVerDownUrl();
					String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator+fileName;
					File file = new File(filePath);
					if(file.exists()){
						file.delete();
					}
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
		return new ModelAndView(new RedirectView("/manager/zhrt/appversioninfo/index?appId="+appId));
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
				appVersionInfoService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO,ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/appversioninfo/index"));
	}
	
}
