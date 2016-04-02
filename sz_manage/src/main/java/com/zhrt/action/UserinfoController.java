package com.zhrt.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.Latn;
import com.zhrt.entity.Userinfo;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.LatnService;
import com.zhrt.service.UserinfoService;
import com.zhrt.util.Constant;


@Controller
@RequestMapping("/manager/zhrt/userinfo")
public class UserinfoController extends BaseController {

	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private LatnService latnService;

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
			String channelId = request.getParameter("channelId");
			paramMap.put("channelId", channelId);
			view.addObject("channelId",channelId);
			
			Page page = new Page(50);
			String pn = request.getParameter("pageNo");
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","cTime");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","desc");
			view.addObject("orderBy",orderByParam);
			view.addObject("order",orderParam);
			view.addObject("pageNo",pageNoParam);
			
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = userinfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<Userinfo> userinfoList = userinfoService.getList(paramMap);
			page.setResult(userinfoList);
			view.addObject("page", page);
			
			
			List<ChannelInfo> channelList = channelInfoService.getList();
			view.addObject("channelList", channelList);
			Map<String,ChannelInfo> channelMap = new HashMap<String,ChannelInfo>();
			for(ChannelInfo channelInfo:channelList){
				channelMap.put(channelInfo.getId(), channelInfo);
			}
			view.addObject("channelMap", channelMap);
			
			List<Latn> proList = latnService.getProList();
			Map<String,String> latnMap = new HashMap<String,String>();
			for(Latn latn:proList){
				latnMap.put(latn.getLatnId()+"", latn.getLatnName());
			}
			view.addObject("latnMap",latnMap);
			
			view.addObject("toPage","../userinfo/userinfolist.jsp");
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
//	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
//	@ResponseBody
//	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
//		ModelAndView view = new ModelAndView(INDEX);
//		
//		try {
//			Userinfo entity = new Userinfo();
//			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
//				entity = userinfoService.getById(idparam);				
//			}
//			if("v".equals(m)){				
//				view.addObject("syncUrl",PropertiesConfigDynamic.getConfig("billSynUrl")+idparam);
//			}
//			view.addObject("m",m);
//			view.addObject("toPage","../spinfo/spinfomodify.jsp");
//			view.addObject("entity", entity);
//		} catch (Exception e) {
//			logger.error(ERROR_INFO_CONTENT);
//			logger.error(e.getMessage());
//			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
//			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
//		}
//		return view;
//	}
		
}
