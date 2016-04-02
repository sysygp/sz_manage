package com.zhrt.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.power.entity.PowerUser;
import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.UserRegist;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.UserRegistService;


@Controller
@RequestMapping("/manager/zhrt/UserRegist")
public class UserRegistController extends BaseController {

	@Autowired
	private UserRegistService userRegistService;
	@Autowired
	private ChannelInfoService channelInfoService;

	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			PowerUser operUser = getSessAttr("operUser");
			if(operUser != null && (operUser.getLoginId().equals("admin0") || operUser.getLoginId().equals("admin9"))){

				//过滤条件，必须提前设置好各参数，才可以调用查询语句。
				Map paramMap = new HashMap();
				Page page = new Page(20);
				String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
				String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
				String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
				
				String beginTime =ServletRequestUtils.getStringParameter(request,"beginTime","");
				String endTime =ServletRequestUtils.getStringParameter(request,"endTime","");
				String flag =ServletRequestUtils.getStringParameter(request,"flag","1");
				
				view.addObject("beginTime", beginTime);
				view.addObject("endTime", endTime);
				view.addObject("flag", flag);
				
				if(StringUtils.isBlank(beginTime)){
					beginTime = DateUtil.DF_YMD_TRIM.format(new Date());
				}
				if(StringUtils.isBlank(endTime)){
					endTime = DateUtil.DF_YMD_TRIM.format(new Date());
				}
				if(beginTime.contains("-")){
					beginTime = beginTime.replace("-", "");
				}
				if(endTime.contains("-")){
					endTime = endTime.replace("-", "");
				}
				
				List<UserRegist> UserRegistList = new ArrayList<UserRegist>();
				//按时间查询
				if(flag.equals("1")){
					GregorianCalendar beginCalendar = new GregorianCalendar(Integer.parseInt(beginTime.substring(0, 4)),Integer.parseInt(beginTime.substring(4, 6)), Integer.parseInt(beginTime.substring(6)));
					beginCalendar.add(GregorianCalendar.MONTH, -1);
					long d = (DateUtil.DF_YMD_TRIM.parse(endTime).getTime()-DateUtil.DF_YMD_TRIM.parse(beginTime).getTime())/1000/60/60/24;
					for(int i = 0 ; i <= d; i++){
						String date = DateUtil.DF_YMD_TRIM.format(beginCalendar.getTime());
						UserRegist userRegist = userRegistService.getAllByUserRegistDate(date,null);
						if(userRegist !=null){
							UserRegistList.add(userRegist);
						}
						beginCalendar.add(GregorianCalendar.DATE, 1);
					}
				}
				//按渠道查询
				else if(flag.equals("2")){
					List<ChannelInfo> channelInfoList = channelInfoService.getList();
					for(ChannelInfo channelInfo:channelInfoList){
						UserRegist userRegist = userRegistService.getByChannelIdBetweenTime(channelInfo.getId(), beginTime, endTime);
						if(userRegist !=null){
							UserRegistList.add(userRegist);
						}
					}
				}
				
				page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
				
				
				view.addObject("UserRegistList",UserRegistList);
				page.setResult(UserRegistList);
				page.setTotalCount(UserRegistList.size());
				view.addObject("page", page);
				
				view.addObject("toPage","../UserRegist/UserRegistlist.jsp");
				
				List<ChannelInfo> channelList = channelInfoService.getList();
				view.addObject("channelList", channelList);
				
				Map<String,ChannelInfo> channelMap = new HashMap<String,ChannelInfo>();
				for(ChannelInfo channelInfo:channelList){
					channelMap.put(channelInfo.getId(), channelInfo);
				}
				view.addObject("channelMap", channelMap);
				
			}
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	/**
	 * 列表查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mine", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView mine(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			
			PowerUser operUser = getSessAttr("operUser");
			if(operUser == null){
				return null;
			}
			String channelId = operUser.getDomainId();
			if(StringUtils.isEmpty(channelId)){
				return null;
			}
			
			//过滤条件，必须提前设置好各参数，才可以调用查询语句。
			Map paramMap = new HashMap();
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			
			String beginTime =ServletRequestUtils.getStringParameter(request,"beginTime","");
			String endTime =ServletRequestUtils.getStringParameter(request,"endTime","");
			String flag =ServletRequestUtils.getStringParameter(request,"flag","1");
			
			view.addObject("beginTime", beginTime);
			view.addObject("endTime", endTime);
			view.addObject("flag", flag);
			
			if(StringUtils.isBlank(beginTime)){
				beginTime = DateUtil.DF_YMD_TRIM.format(new Date());
			}
			if(StringUtils.isBlank(endTime)){
				endTime = DateUtil.DF_YMD_TRIM.format(new Date());
			}
			if(beginTime.contains("-")){
				beginTime = beginTime.replace("-", "");
			}
			if(endTime.contains("-")){
				endTime = endTime.replace("-", "");
			}
			
			List<UserRegist> UserRegistList = new ArrayList<UserRegist>();
			//按时间查询
			if(flag.equals("1")){
				GregorianCalendar beginCalendar = new GregorianCalendar(Integer.parseInt(beginTime.substring(0, 4)),Integer.parseInt(beginTime.substring(4, 6)), Integer.parseInt(beginTime.substring(6)));
				beginCalendar.add(GregorianCalendar.MONTH, -1);
				long d = (DateUtil.DF_YMD_TRIM.parse(endTime).getTime()-DateUtil.DF_YMD_TRIM.parse(beginTime).getTime())/1000/60/60/24;
				for(int i = 0 ; i <= d; i++){
					String date = DateUtil.DF_YMD_TRIM.format(beginCalendar.getTime());
					UserRegist userRegist = userRegistService.getAllByUserRegistDate(date,channelId);
					if(userRegist !=null){
						UserRegistList.add(userRegist);
					}
					beginCalendar.add(GregorianCalendar.DATE, 1);
				}
			}
			//按渠道查询
			else if(flag.equals("2")){
				List<ChannelInfo> channelInfoList = channelInfoService.getList();
//				for(ChannelInfo channelInfo:channelInfoList){
					UserRegist userRegist = userRegistService.getByChannelIdBetweenTime(channelId, beginTime, endTime);
					if(userRegist !=null){
						UserRegistList.add(userRegist);
					}
//				}
			}
			
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			
			view.addObject("UserRegistList",UserRegistList);
			page.setResult(UserRegistList);
			page.setTotalCount(UserRegistList.size());
			view.addObject("page", page);
			
			view.addObject("toPage","../UserRegist/UserRegistmine.jsp");
			
			List<ChannelInfo> channelList = channelInfoService.getList();
			view.addObject("channelList", channelList);
			
			Map<String,ChannelInfo> channelMap = new HashMap<String,ChannelInfo>();
			for(ChannelInfo channelInfo:channelList){
				channelMap.put(channelInfo.getId(), channelInfo);
			}
			view.addObject("channelMap", channelMap);
			
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		try {
			UserRegist entity = new UserRegist();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = userRegistService.getById(idparam);				
			}
			view.addObject("m",m);
			view.addObject("toPage","../UserRegist/UserRegistmodify.jsp");
			view.addObject("entity", entity);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
}
