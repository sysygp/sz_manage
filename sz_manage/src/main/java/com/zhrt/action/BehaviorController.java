package com.zhrt.action;

import java.net.URLDecoder;
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

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.zhrt.entity.Behavior;
import com.zhrt.entity.BehaviorType;
import com.zhrt.service.BehaviorService;
import com.zhrt.service.BehaviorTypeService;
import com.zhrt.util.ValiUtil;

@Controller
@RequestMapping("/manager/zhrt/behavior")
public class BehaviorController extends BaseController {

	@Autowired
	private BehaviorService behaviorService;
	@Autowired
	private BehaviorTypeService behaviorTypeService;

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
		String platid = "";
		
		try{
			platid = ServletRequestUtils.getStringParameter(request,"platid","");
			if(ValiUtil.valiStr(platid)){
				platid = URLDecoder.decode(platid, "UTF-8");
			}else{
				if(!getSessionUser().getPlatId().toString().equals("0")){
					platid = getSessionUser().getPlatId().toString();
				}
			}
			
			//查询用户行为类型
			List<BehaviorType> behTypeList = behaviorTypeService.getList();
			view.addObject("behTypeList", behTypeList);
			
			String mobileTimeStart = ServletRequestUtils.getStringParameter(request, "mobileTimeStart");
			String mobileTimeEnd = ServletRequestUtils.getStringParameter(request, "mobileTimeEnd");
			String userType = ServletRequestUtils.getStringParameter(request, "userType");
			String behavId = ServletRequestUtils.getStringParameter(request, "behavId");
			String userid = ServletRequestUtils.getStringParameter(request,"userid","");
			String phone = ServletRequestUtils.getStringParameter(request, "phone");
			
			Map paramMap = new HashMap();
			paramMap.put("platid", platid);
			paramMap.put("userid", userid);
			paramMap.put("mobileTimeStart", mobileTimeStart);
			paramMap.put("mobileTimeEnd", mobileTimeEnd);
			paramMap.put("userType", userType);
			paramMap.put("behavId", behavId);
			paramMap.put("phone", phone);
			
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","createTime");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","desc");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			
			if (page.isAutoCount()) {
				int totalCount = behaviorService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			
			List<Behavior> entityList = behaviorService.getList(paramMap);
			view.addObject("entityList",entityList);
			page.setResult(entityList);
			
			view.addObject("platid", platid);
			view.addObject("userid", userid);
			view.addObject("mobileTimeStart", mobileTimeStart);
			view.addObject("mobileTimeEnd", mobileTimeEnd);
			view.addObject("userType", userType);
			view.addObject("behavId", behavId);
			view.addObject("phone", phone);
			view.addObject("page", page);
			view.addObject("toPage","../behavior/behaviorlist.jsp");
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
			Behavior entity = new Behavior();
			
			if(ValiUtil.valiStr(idparam) && !idparam.equals("0")) {
				entity = behaviorService.getById(idparam);				
			}
			
			view.addObject("m",m);
			view.addObject("toPage","../behavior/behaviormodify.jsp");
			view.addObject("entity", entity);
			
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
	public ModelAndView modify(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		try {
			String id = ServletRequestUtils.getStringParameter(request,"id","");
			String behavId = ServletRequestUtils.getStringParameter(request,"behavId","");
			String behavName = ServletRequestUtils.getStringParameter(request,"behavName","");
			String platname = ServletRequestUtils.getStringParameter(request,"platname","");
			
			Behavior entity = null;
			if (StringUtils.isBlank(id)) {
				entity = new Behavior();
				
			} else {
				entity = behaviorService.getById(id);
			}
			
			entity.setUserid("5_z33333331");
			entity.setUserType(1);
			entity.setPhone("15225665107");
			entity.setProvince("河南");
			entity.setProvinceName("开封");
			entity.setAccountid("789");
			entity.setPlatid("5");
			entity.setPlatname(platname);
			entity.setImei("5555");
			entity.setTermType(-1);
			entity.setBehavId(behavId);
			entity.setBehavName(behavName);
			entity.setTermModel("安卓5.6");
			entity.setVersion("IOS");
			entity.setLongitude("115.3658825");
			entity.setLatitude("52.35678923");
			entity.setCreateTime(DateUtil.getCurDate());
			entity.setMobileTime(DateUtil.getCurDate());
			
			if (StringUtils.isBlank(id)) {
				behaviorService.add(entity);
			} else {
				entity.setId(id);
				behaviorService.update(entity);
			}
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/behavior/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		try {
			if(StringUtils.isNotBlank(idparam)){
				behaviorService.delById(idparam);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/behavior/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		try {
//			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			String[] ids = {"3","4"};
			if(ids!=null && ids.length > 0){
				behaviorService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/behavior/index"));
	}
}
