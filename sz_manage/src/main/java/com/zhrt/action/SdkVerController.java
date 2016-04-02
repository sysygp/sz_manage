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

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.system.util.http.LocalAndNetUrlUtil;
import com.system.util.property.PropertiesConfigDynamic;
import com.zhrt.entity.SdkVerInfo;
import com.zhrt.service.SdkVerService;
import com.zhrt.util.Constant;


/**
 * 
 * sdk版本管理
 * @author ：
 * @vision : 1.0.0
 * @createDate : 2015年8月26日 下午2:44:27
 * @email  ：zhu_shz@myepay.cn
 */
@Controller
@RequestMapping("/manager/zhrt/sdkver")
public class SdkVerController extends BaseController {

	@Autowired
	private SdkVerService sdkVerService;

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
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
			
			//根据条件查询   sdkVerCode  isUpdate  status
			String sdkVerCode = ServletRequestUtils.getStringParameter(request,"sdkVerCode","");
			String isUpdate = ServletRequestUtils.getStringParameter(request,"isUpdate","");
			String status = ServletRequestUtils.getStringParameter(request,"status","");
			
			if(!StringUtils.isBlank(sdkVerCode)){
				paramMap.put("sdkVerCode", sdkVerCode.trim());
				view.addObject("sdkVerCode",sdkVerCode);
			}
			if(!StringUtils.isBlank(isUpdate)){
				paramMap.put("isUpdate", isUpdate.trim());
				view.addObject("isUpdate",isUpdate.trim());
			}
			if(!StringUtils.isBlank(status)){
				paramMap.put("status", status.trim());
				view.addObject("status",status.trim());
			}
			if (page.isAutoCount()) {
				int totalCount = sdkVerService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<SdkVerInfo> sdkVerList = sdkVerService.getList(paramMap);
			view.addObject("sdkVerList",sdkVerList);
			page.setResult(sdkVerList);
			view.addObject("page", page);
			view.addObject("toPage","../sdkver/sdkverlist.jsp");
		} catch (Exception e) {	
			logger.error(e.getMessage());
			logger.error(ERROR_INFO_CONTENT);
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	/**
	 * 
	 * 单条记录查询
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午4:09:42
	 * 修改人：
	 * 修改时间：
	 * @param idparam
	 * @param m
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			SdkVerInfo entity = new SdkVerInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = sdkVerService.getById(idparam);				
			}
			view.addObject("m",m);
			view.addObject("toPage","../sdkver/sdkvermodify.jsp");
			view.addObject("entity", entity);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error(ERROR_INFO_CONTENT);
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	/**
	 * 
	 * 新增或修改
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午4:10:01
	 * 修改人：
	 * 修改时间：
	 * @param entity
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(SdkVerInfo entity,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		
		MultipartFile file = entity.getFile();
		try {
			
			//文件名
			String originalFileName = file.getOriginalFilename();
			//保存的文件名
			String fileName = System.currentTimeMillis()+originalFileName;
			String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator+fileName;
			//保存文件
			file.transferTo(new File(filePath));
			String id = entity.getId();
			if (StringUtils.isBlank(id)) {
								
				Date date = DateUtil.getCurDate();
				entity.setStatus(String.valueOf(Constant.STATUS_VALID));
				entity.setUpdateFile(fileName);
				entity.setCreateUser("admin");
				entity.setCreateTime(date);
				entity.setUpdateUser("admin");
				entity.setUpdateTime(DateUtil.getCurDate());
				sdkVerService.add(entity);
			} else{
				
				//查询原来的文件
				SdkVerInfo oldSdkVerInfo = sdkVerService.getById(id);							
				//如果上传了新文件，将原来的文件删除
				if(!"".equals(originalFileName)&&file.getSize()>0){
					String oldFilaName = oldSdkVerInfo.getUpdateFile();
					String oldFilePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator+oldFilaName;
					File oldfile = new File(oldFilePath);
					if(oldfile.exists()){
						oldfile.delete();
					}
				}
				entity.setUpdateFile(fileName);
				//当前登录者
				entity.setUpdateUser("admin");
				entity.setUpdateTime(DateUtil.getCurDate());
				sdkVerService.update(entity);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());			
			logger.error(ERROR_INFO_CONTENT);
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/sdkver/index"));
	}
	
	
	/**
	 * 
	 * 删除
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午4:10:21
	 * 修改人：
	 * 修改时间：
	 * @param idparam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			if(StringUtils.isNotBlank(idparam)){
				SdkVerInfo oldparam = sdkVerService.getById(idparam);						
				if(!oldparam.getStatus().trim().equals(Constant.STATUS_INVALID+"")){//下线之后的数据才可以删除
					logger.error("下线之后的数据才可以删除");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "下线之后的数据才可以删除");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
				
				SdkVerInfo param = new SdkVerInfo();

				param.setId(idparam);
				param.setStatus(String.valueOf(Constant.STATUS_DEL));
				param.setUpdateUser("admin");
				param.setUpdateTime(DateUtil.getCurDate());
				sdkVerService.update(param);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("未知错误，请稍后重试或联系系统管理员");
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/sdkver/index"));
	}
	/**
	 * 
	 * 批量删除
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午4:10:32
	 * 修改人：
	 * 修改时间：
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				sdkVerService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/sdkver/index"));
	}
	
	/**
	 * 
	 * sdk更新文件下载
	 * 创建人：
	 * 创建时间: 2015年8月26日 下午4:11:09
	 * 修改人：
	 * 修改时间：
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/download",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView download(@RequestParam String id,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		SdkVerInfo sdkVer = sdkVerService.getById(id);
		if(sdkVer==null){
			logger.error("sdk版本不存在");
		}else{
			String fileName = sdkVer.getUpdateFile();
			String actualFileName = fileName.substring(13);
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
	        	request.setCharacterEncoding("UTF-8");
	    		String filePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath")+File.separator;
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
				logger.error(e.getMessage());
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}  finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
				if(bos!=null){
					try {
						bos.close();
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		return null;
	}
	/**
	 * 
	 * 简单描述: 同步请求校验此sdkCode是否合格
	 *
	 * @author 王坤
	 * @Create Date 2015年9月8日 10点30分30秒
	 */
	@RequestMapping(value="/checkSdkVerCode",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView checkSdkVerCode(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String sdkVerCode = ServletRequestUtils.getRequiredStringParameter(request, "sdkVerCode");
						
			//查询传过来的版本是否每一位是在0-99范围内
			boolean isCHeck = sdkVerService.checkSdkCodeOnebit(sdkVerCode);
			if(!isCHeck){				
				map.put("result", "1");
				view = returnInfoJson(response, map);
				return view;
			}
			
			//与数据库中所有的sdk版本号比较，不能小于数据库中已有的版本号，即每一位都需要比较。sdk版本号格式1.2.3.4
		    boolean isTrue = sdkVerService.compareSDKCode(sdkVerCode);//新增版本号不能比之前版本号低				  
		    if(!isTrue){				
				map.put("result", "2");
				view = returnInfoJson(response, map);
				return view;
			}		
		    
		    map.put("result", "0");
			view = returnInfoJson(response, map);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
		}
		return view;
	}
}
