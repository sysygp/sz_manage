package com.zhrt.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zhrt.entity.AppTypeInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.CpInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.AppTypeService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.service.SeqService;
import com.zhrt.util.Constant;
import com.zhrt.util.ExcelUtil;

@Controller
@RequestMapping("/manager/zhrt/appinfo")
public class AppInfoController extends BaseController {
	
	public final Logger tomyepaylog = LoggerFactory.getLogger("tomyepaylog");

	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private AppTypeService appTypeService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private SeqService seqService;

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
	public ModelAndView index(AppInfo appInfo,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			Map paramMap = new HashMap();
//			paramMap.put("cpId", appInfo.getCpId());
			paramMap.put("appName", appInfo.getAppName());
			paramMap.put("appSort", appInfo.getAppSort());
			paramMap.put("status", appInfo.getStatus());
			Page page = new Page(20);
			String pageNoParam = ServletRequestUtils.getStringParameter(request, "pageNo", "");
			String orderByParam = ServletRequestUtils.getStringParameter(request, "orderBy", "");
			String orderParam = ServletRequestUtils.getStringParameter(request, "order", "");
			page.setPage(page, paramMap, pageNoParam, orderByParam, orderParam);
			if (page.isAutoCount()) {
				int totalCount = appInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			//关联查询出产品的道具数量
			List<AppInfo> appInfoList = appInfoService.getForPage(paramMap);
			view.addObject("appInfoList", appInfoList);
			page.setResult(appInfoList);
/*			List<CpInfo> cpList = cpInfoService.getList();
			view.addObject("cpList", cpList);*/
			List<AppTypeInfo> appSortList = appTypeService.getList();
			view.addObject("appSortList", appSortList);
			view.addObject("page", page);
			view.addObject("toPage", "../appinfo/appinfolist.jsp");
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
	public ModelAndView get(@PathVariable String idparam, @PathVariable String m, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", Constant.STATUS_VALID);
			//查询启用的cp信息
			List<CpInfo> cpInfoList = cpInfoService.getList(map);
			
			AppInfo entity = new AppInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				//查询产品详情
				entity = appInfoService.getById(idparam);
				//查询运营产品的渠道
				List<ChannelInfo> channelList = channelInfoService.getByChannelApp(entity.getId());
				view.addObject("channelList", channelList);
			}
			
			//查询产品所有启用的分类
			List<AppTypeInfo> appSortList = appTypeService.getList(map);
			
			view.addObject("cpInfoList", cpInfoList);
			view.addObject("appSortList", appSortList);
			view.addObject("m", m);
			view.addObject("toPage", "../appinfo/appinfomodify.jsp");
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
	 * @param appInfo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(AppInfo appInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperUser(user.getLoginName());
		try {
			// 查询关联信息
			String cpId = appInfo.getCpId();
			if (cpId != null && !"".equals(cpId)) {
				CpInfo cpInfo = cpInfoService.getById(cpId);
				if (cpInfo != null) {
					// 设置cpCode
					appInfo.setCpCode(cpInfo.getCpCode());
				}
			}
			
			String id = appInfo.getId();
			if (StringUtils.isBlank(id)) {
				log.setOperType(Constant.OPERTYPE_ADD);
				log.setOperDesc("产品："+appInfo.getAppName());

				
				//新增产品数据
				appInfo.setCreatePerson("admin");
				appInfo.setCreateTime(DateUtil.getCurDate());
				appInfo.setLastUpdatePerson("admin");
				appInfo.setLastUpdateTime(DateUtil.getCurDate());
				try {
					Integer appSeq = seqService.getNextByName("appSeq");
					appInfo.setAppSeq(appSeq);
					
					//增加排行榜标识。默认是1000
					appInfo.setAppRankSeq(1000);
					
					//新增产品
					appInfoService.add(appInfo,user.getPlatId());
					log.setOperResult(Constant.DEAL_OK);
				} catch (Exception e) {
					redirectAttributes.addFlashAttribute(ERROR_INFO, "产品创建失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
			
			} else {
				AppInfo oldAppInfo = appInfoService.getById(id);
				log.setOperType(Constant.OPERTYPE_MODIFY);
				log.setOperDesc("产品："+oldAppInfo.getAppName());
				boolean ifNameChanged = !appInfo.getAppName().equals(oldAppInfo.getAppName());
				

				try {
					// 当前登录者，修改产品和渠道产品
					appInfo.setLastUpdatePerson("admin");
					appInfo.setLastUpdateTime(DateUtil.getCurDate());
					appInfoService.update(appInfo);
					log.setOperResult(Constant.DEAL_OK);
				} catch (Exception e) {
					tomyepaylog.error("本地修改产品 "+appInfo.getAppName()+" 失败");
					log.setOperResult(Constant.DEAL_ERR);
					redirectAttributes.addFlashAttribute(ERROR_INFO, "产品信息修改失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
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
		return new ModelAndView(new RedirectView("/manager/zhrt/appinfo/index"));
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
	public ModelAndView del(@PathVariable String idparam, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
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
				AppInfo appInfo = appInfoService.getById(idparam);
				log.setOperDesc("产品："+appInfo.getAppName());
				//查询运营产品的渠道
				List<ChannelInfo> channelList = channelInfoService.getByChannelApp(idparam);
				if(channelList!=null&&channelList.size()>0){
					log.setOperResult(Constant.DEAL_ERR);
					logger.error("该产品已在某些渠道推广，无法删除！");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "该产品已在某些渠道推广，无法删除！");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
				//产品为上线状态
				if(String.valueOf(Constant.STATUS_VALID).equals(appInfo.getStatus())){
					log.setOperResult(Constant.DEAL_ERR);
					logger.error("该产品处于上线状态，无法删除！");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "该产品处于上线状态，无法删除！");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}

				try {
					AppInfo param = new AppInfo();
					param.setId(idparam);
					param.setStatus(String.valueOf(Constant.STATUS_DEL));
					param.setLastUpdatePerson("admin");
					param.setLastUpdateTime(DateUtil.getCurDate());
					appInfoService.update(param);
					log.setOperResult(Constant.DEAL_OK);
				} catch (Exception e) {
					tomyepaylog.error("本地删除产品 "+appInfo.getAppName()+" 失败");
					log.setOperResult(Constant.DEAL_ERR);
					redirectAttributes.addFlashAttribute(ERROR_INFO, "产品信息删除失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
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
		return new ModelAndView(new RedirectView("/manager/zhrt/appinfo/index"));
	}
	
	/**
	 * 
	 * ajax查询cp下的所有产品
	 * 创建人：
	 * 创建时间: 2015年7月25日
	 * 修改人：
	 * 修改时间：
	 * @param cpId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/findAppByCp",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView findAppByCp(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String cpId = ServletRequestUtils.getRequiredStringParameter(request, "cpId");
			String status = ServletRequestUtils.getRequiredStringParameter(request, "status");
			param.put("cpId", cpId);
			param.put("status", status);
			List<AppInfo> appList = appInfoService.getList(param);
			map.put("result", appList);
			view = returnInfoJson(response, map);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
		}
		return view;
	}
	
	/**
	 * 
	 * 导出产品信息
	 * 创建人：
	 * 创建时间: 2015年8月12日 下午3:00:02
	 * 修改人：
	 * 修改时间：
	 * @param appInfo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/exportToExcel",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void exportToExcel(AppInfo appInfo,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cpId", appInfo.getCpId());
		paramMap.put("appName", appInfo.getAppName());
		paramMap.put("appSort", appInfo.getAppSort());
		List<AppInfo> appInfoList = appInfoService.getForPage(paramMap);
		//文件名
		String fileName ="AppInfo_"+DateUtil.Date()+".xls";
		String[] headers = {"产品ID","产品名称","产品类别","所属CP","道具数量","状态"};
		String[] fieldName = {"id","appName","appSort","cpId","propCount","status"};
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+ fileName);
			OutputStream out = response.getOutputStream();
			ExcelUtil<AppInfo> util = new ExcelUtil<AppInfo>();
			util.exportExcel(fileName, headers,fieldName, appInfoList, out);
		} catch (IOException e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
		}
	}
}
