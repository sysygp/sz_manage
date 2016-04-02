package com.zhrt.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.SignLog;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.PlatService;
import com.zhrt.service.SignLogService;
import com.zhrt.util.ValiUtil;

@Controller
@RequestMapping("/manager/zhrt/signlog")
public class SignLogController extends BaseController {

	@Autowired
	private SignLogService signLogService;
	@Autowired
	private PlatService platService;
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		
		String platid = "";
		try {
			platid = ServletRequestUtils.getStringParameter(request,"platid","");
			if(ValiUtil.valiStr(platid)){
				platid = URLDecoder.decode(platid, "UTF-8");
			}else{
				platid = getSessionUser().getPlatId().toString();
			}
			if(ValiUtil.valiStr(platid)){
				//查询所有的产品信息
				List<AppInfo> appList = appInfoService.getList();
				view.addObject("appList", appList);
				//查询所有渠道信息
				List<ChannelInfo> channelList = channelInfoService.getList();
				view.addObject("channelList", channelList);
				String appId = ServletRequestUtils.getStringParameter(request, "appId");
				String userId = ServletRequestUtils.getStringParameter(request, "userId");
				String channelId = ServletRequestUtils.getStringParameter(request, "channelId");
				if(StringUtils.isNotBlank(userId)){
					userId = userId.trim();
				}
				//过滤条件，必须提前设置好各参数，才可以调用查询语句。
				Map paramMap = new HashMap();
				paramMap.put("platid", platid);
				paramMap.put("userId", userId);
				paramMap.put("channelId", channelId);
				if(StringUtils.isNotBlank(appId)){
					AppInfo appInfo = appInfoService.getById(appId);
					paramMap.put("appSeq", appInfo.getAppSeq());
				}else{
					paramMap.put("appSeq", "total");
				}
				Page page = new Page(20);
				String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
				String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","signDate");
				String orderParam =ServletRequestUtils.getStringParameter(request,"order","desc");
				page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
				
				if (page.isAutoCount()) {
					int totalCount = signLogService.getListCount(paramMap);
					page.setTotalCount(totalCount);
				}
				
				 List<SignLog> entityList = signLogService.getList(paramMap);
				view.addObject("entityList",entityList);
				page.setResult(entityList);
				view.addObject("appId", appId);
				view.addObject("userId", userId);
				view.addObject("channelId", channelId);
				view.addObject("page", page);
			}
			view.addObject("platid", platid);
			view.addObject("toPage","../signlog/signloglist.jsp");
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return view;
	}
}
