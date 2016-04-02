package com.zhrt.action;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.AppVersionInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.CpInfo;
import com.zhrt.entity.NoticeInfo;
import com.zhrt.entity.NoticeTypeInfo;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.AppVersionInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.NoticeInfoService;
import com.zhrt.service.NoticeTypeService;
import com.zhrt.service.OperLogService;
import com.zhrt.util.CacheUtil;
import com.zhrt.util.Constant;
import com.zhrt.util.ErrorCode;


@Controller
@RequestMapping("/manager/zhrt/notice")
public class NoticeInfoController extends BaseController{

	@Autowired
	private NoticeInfoService noticeService;
	@Autowired
	private NoticeTypeService noticeTypeService;
	@Autowired
	private CpInfoService cpInfoService;
	
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AppVersionInfoService appVersionInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	
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
	public ModelAndView index(NoticeInfo notice,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		try {
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			//根据条件查询   noticeTitle  appName
			String noticeTitle = ServletRequestUtils.getStringParameter(request,"noticeTitle","");
			String appName = ServletRequestUtils.getStringParameter(request,"appName","");
			
			if(!StringUtils.isBlank(noticeTitle)){				
				paramMap.put("noticeTitle", noticeTitle.trim());
				view.addObject("noticeTitle",noticeTitle.trim());
			}
			if(!StringUtils.isBlank(appName)){				
				//通过产品名称获取产品id
				paramMap.put("appName", appName.trim());				
				view.addObject("appName",appName.trim());
			}			
			
			if (page.isAutoCount()) {
				int totalCount = noticeService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<NoticeInfo> noticeList = noticeService.getList(paramMap);
			view.addObject("noticeList",noticeList);
			page.setResult(noticeList);
			view.addObject("page", page);
			view.addObject("toPage","../notice/noticeInfoList.jsp");
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
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request,HttpServletResponse response, RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			NoticeInfo entity = new NoticeInfo();
			List<CpInfo> cpInfoList = new ArrayList<CpInfo>();//cp集合
			List<AppInfo> appInfoList = new ArrayList<>();//产品集合
			List<ChannelInfo> channelInfoList = new ArrayList<ChannelInfo>();//渠道集合
			List<AppVersionInfo> appVersionInfoList = new ArrayList<AppVersionInfo>();//产品版本集合
			List<NoticeTypeInfo> noticeTypeInfoList = new ArrayList<NoticeTypeInfo>();//通知类型集合
			
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = noticeService.getById(idparam);				
			}
			//查询所有的cp集合（上线）
			Map<String, Object> cpParam = new HashMap<String, Object>();
			cpParam.put("status", Constant.STATUS_VALID);
			cpInfoList =cpInfoService.getList(cpParam);
			
			//查询所有的产品（上线）
			Map<String, Object> appParam = new HashMap<String, Object>();
			appParam.put("status", Constant.STATUS_VALID);
			appInfoList = appInfoService.getList(appParam);
			//查询所有的渠道（上线）
			Map<String, Object> channelParam = new HashMap<String, Object>();
			channelParam.put("status", Constant.STATUS_VALID);
			channelInfoList = channelInfoService.getList(channelParam);
			//查询所有的产品版本集合
			Map<String, Object> appVersionParamMap = new HashMap<String, Object>();
			appParam.put("status", Constant.STATUS_VALID);
			appVersionInfoList = appVersionInfoService.getList(appVersionParamMap);
			
			//查询所有的通知类型
			Map<String, Object> noticeTypeParamMap = new HashMap<String, Object>();
			noticeTypeParamMap.put("status", Constant.STATUS_VALID);
			noticeTypeInfoList = noticeTypeService.getList(noticeTypeParamMap);
			
			view.addObject("cpInfoList",cpInfoList);
			view.addObject("appInfoList",appInfoList);
			view.addObject("channelInfoList",channelInfoList);
			view.addObject("appVersionInfoList",appVersionInfoList);
			view.addObject("noticeTypeInfoList",noticeTypeInfoList);
			view.addObject("m",m);
			view.addObject("entity", entity);
			view.addObject("toPage","../notice/noticeInfomodify.jsp");
			
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
	public ModelAndView modify(@ModelAttribute("entity") NoticeInfo entity,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			String id = entity.getId();
			if (StringUtils.isBlank(id)) {
				Date date = DateUtil.getCurDate();
				entity.setNoticeStatus(String.valueOf(Constant.STATUS_VALID));
				entity.setCuser("admin");
				entity.setCtime(date);
				entity.setUuser("admin");
				entity.setUtime(date);
				//根据产品id查出产品名称
				AppInfo appinfo = appInfoService.getById(entity.getAppId());
				if(StringUtils.isNotBlank(appinfo.getAppName())){
					entity.setAppName(appinfo.getAppName());
				}else{
					entity.setAppName("");					
				}
				noticeService.add(entity);
				//更新缓存
				int code = CacheUtil.cacheWrite(CacheUtil.NOTICE, true);
				if(ErrorCode.SYSTEM_ERROR==code){
					logger.error("缓存系统更新缓存失败");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "缓存系统更新缓存失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
			} else{
				//当前登录者
				entity.setUuser("admin");
				entity.setUtime(DateUtil.getCurDate());
				noticeService.update(entity);
				//更新缓存
				int code = CacheUtil.cacheWrite(CacheUtil.NOTICE, true);
				if(ErrorCode.SYSTEM_ERROR==code){
					logger.error("缓存系统更新缓存失败");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "缓存系统更新缓存失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/notice/index"));
	}
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		try {
			if(StringUtils.isNotBlank(idparam)){

				/*NoticeInfo oldNotice = noticeService.getById(idparam);
				if(!oldNotice.getNoticeStatus().trim().equals(Constant.STATUS_INVALID)){
					logger.error("下线之后的数据才可以删除");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "下线之后的数据才可以删除");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}*/
				
				NoticeInfo param = new NoticeInfo();
				param.setId(idparam);
				param.setNoticeStatus(String.valueOf(Constant.STATUS_DEL));
				param.setUuser("admin");
				param.setUtime(DateUtil.getCurDate());
				noticeService.update(param);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/notice/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				noticeService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			tipInfoMap.put(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/notice/index"));
	}
}
