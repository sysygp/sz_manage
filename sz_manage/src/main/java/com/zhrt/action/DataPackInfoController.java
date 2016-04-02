package com.zhrt.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.zhrt.entity.DataFileAppVer;
import com.zhrt.entity.DataPackInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.AppVersionInfoService;
import com.zhrt.service.DataPackInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.util.Constant;

@Controller
@RequestMapping("/manager/zhrt/datapackinfo")
public class DataPackInfoController extends BaseController {

	@Autowired
	private DataPackInfoService dataPackInfoService;
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	@Autowired
	private AppInfoService appInfoService;
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
	public ModelAndView index(DataPackInfo dataPackInfo, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			Map paramMap = new HashMap();
			paramMap.put("appId", dataPackInfo.getAppId());
//			paramMap.put("supportVersion", dataPackInfo.getSupportVersion());
			paramMap.put("dataFileId", dataPackInfo.getDataFileId());
			paramMap.put("status", dataPackInfo.getStatus());
			Page page = new Page(20);
			String pageNoParam = ServletRequestUtils.getStringParameter(request, "pageNo", "");
			String orderByParam = ServletRequestUtils.getStringParameter(request, "orderBy", "");
			String orderParam = ServletRequestUtils.getStringParameter(request, "order", "");
			page.setPage(page, paramMap, pageNoParam, orderByParam, orderParam);
			if (page.isAutoCount()) {
				int totalCount = dataPackInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			//产品的数据打包文件
			List<DataPackInfo> dataPackInfoList = new ArrayList<DataPackInfo>();
			//产品的所有版本
//			List<AppVersionInfo> appVerList  = new ArrayList<AppVersionInfo>();
			if (StringUtils.isNotBlank(dataPackInfo.getAppId())) {
				dataPackInfoList = dataPackInfoService.getList(paramMap);
				//查询产品所有的版本
//				Map<String, Object> verMap = new HashMap<String, Object>();
//				verMap.put("appId", dataPackInfo.getAppId());
//				appVerList = appVersionInfoService.getList(verMap);
			}
//			view.addObject("appVerList", appVerList);
			view.addObject("dataPackInfoList", dataPackInfoList);
			page.setResult(dataPackInfoList);
			view.addObject("page", page);
			view.addObject("toPage", "../datapack/datapackinfolist.jsp");
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
	public ModelAndView get(@PathVariable String idparam, @PathVariable String m,String appId, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		Map<String, Object> tipInfoMap = new HashMap<String, Object>();
		try {
			DataPackInfo entity = new DataPackInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = dataPackInfoService.getById(idparam);
			}
			List<AppVersionInfo> appVerList = new ArrayList<AppVersionInfo>();
			if(StringUtils.isNotBlank(appId)){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("appId", appId);
				
				//查询产品上线的所有版本
				param.put("status", Constant.STATUS_VALID);
				appVerList = appVersionInfoService.getList(param);
				
				//查询数据文件支持的所有版本
				List<DataFileAppVer> supportVersions = dataPackInfoService.getSuportVersion(idparam);
				Map<String, String> supportVersionMap = new HashMap<String, String>();
				for (DataFileAppVer dataFileAppVer : supportVersions) {
					supportVersionMap.put(dataFileAppVer.getAppVerId(), "1");
				}
				view.addObject("supportVersionMap", supportVersionMap);
			}
			view.addObject("m", m);
			view.addObject("appId",appId);
			view.addObject("appVerList", appVerList);
			view.addObject("toPage", "../datapack/datapackinfomodify.jsp");
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
	 * 
	 * @param
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(DataPackInfo dataPackInfo, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		//操作日志
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperUser(user.getLoginName());
		try {
			AppInfo appInfo = appInfoService.getById(dataPackInfo.getAppId());
			log.setOperDesc(appInfo.getAppName()+"：数据升级");
			MultipartFile file = dataPackInfo.getFile();
			//文件名
			String originalFileName = file.getOriginalFilename();
			if(file!=null&&!file.isEmpty()){
				//重命名文件
				String fileName = System.currentTimeMillis()+originalFileName;
				String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_data_filepath")+File.separator+fileName;
				//保存文件
				file.transferTo(new File(filePath));
				dataPackInfo.setDataFile(fileName);
			}
			String id = dataPackInfo.getId();
			//版本id使用，分隔
			String supportVersion = dataPackInfo.getSupportVersion();
			String[] verIds = supportVersion.split(",");
			if (StringUtils.isBlank(id)) {
				
				log.setOperType(Constant.OPERTYPE_ADD);
				
				// 获取当前登录用户
				Date date = DateUtil.getCurDate();
				String person = "admin";
				dataPackInfo.setCreateUser(person);
				dataPackInfo.setCreateTime(date);
				dataPackInfo.setUpdateUser(person);
				dataPackInfo.setUpdateTime(date);
				dataPackInfoService.addVersion(dataPackInfo,verIds);
				
				log.setOperResult(Constant.DEAL_OK);
			} else {
				log.setOperType(Constant.OPERTYPE_MODIFY);
				dataPackInfo.setUpdateUser("admin");
				dataPackInfo.setUpdateTime(DateUtil.getCurDate());
				dataPackInfoService.update(dataPackInfo,verIds);
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
		return new ModelAndView(new RedirectView("/manager/zhrt/datapackinfo/index?appId="+dataPackInfo.getAppId()));
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
	public ModelAndView del(@PathVariable String idparam,String appId, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_DEL);
		log.setOperUser(user.getLoginName());
		try {
			if (StringUtils.isNotBlank(idparam)) {
				DataPackInfo param = dataPackInfoService.getById(idparam);
				log.setOperDesc("数据文件："+param.getDataFile().substring(13));
				param.setId(idparam);
				param.setStatus(String.valueOf(Constant.STATUS_DEL));
				param.setUpdateUser("admin");
				param.setUpdateTime(DateUtil.getCurDate());
				dataPackInfoService.update(param,null);
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
		return new ModelAndView(new RedirectView("/manager/zhrt/datapackinfo/index?appId="+appId));
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
				dataPackInfoService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response, INDEX, tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/datapackinfo/index"));
	}
	
	/**
	 * 
	 * 数据文件下载
	 * 创建人：
	 * 创建时间: 2015年8月17日 下午7:10:16
	 * 修改人：
	 * 修改时间：
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/download",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView download(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){
		DataPackInfo dataPackInfo = dataPackInfoService.getById(id);
		if(dataPackInfo==null){
			logger.error("数据文件不存在");
		}else{
			String fileName = dataPackInfo.getDataFile();
			String actualFileName = fileName.substring(13);
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
	        	request.setCharacterEncoding("UTF-8");
	    		String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_data_filepath")+File.separator;
	    		String downloadPath = filePath+fileName;
	    		File downloadFile = new File(downloadPath);
		        bis = new BufferedInputStream(new FileInputStream(downloadFile)); 
		        bos = new BufferedOutputStream(response.getOutputStream());  
	    		response.setContentType("application/x-msdownload;");  
				response.setHeader("Content-disposition", "attachment; filename="+ new String(actualFileName.getBytes("utf-8"), "ISO8859-1"));
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
}
