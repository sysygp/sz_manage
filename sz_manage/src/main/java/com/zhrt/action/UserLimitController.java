package com.zhrt.action;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.system.controller.BaseController;
import com.system.page.Page;
import com.system.util.date.DateUtil;
import com.system.util.stringutil.StringDateConstant;
import com.zhrt.entity.Spcode;
import com.zhrt.entity.UserLimit;
import com.zhrt.service.UserLimitService;
import com.zhrt.service.SpcodeService;


@Controller
@RequestMapping("/manager/zhrt/userLimit")
public class UserLimitController extends BaseController {

	@Autowired
	private UserLimitService userLimitService;
	@Autowired
	private SpcodeService spcodeService;

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
			if (page.isAutoCount()) {
				int totalCount = userLimitService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<UserLimit> spcodeLimitList = userLimitService.getList(paramMap);
			page.setResult(spcodeLimitList);
			view.addObject("page", page);
			
			List<Spcode> spcodeList = spcodeService.getList();
			view.addObject("spcodeList", spcodeList);
			
			Map<String,Spcode> spcodeMap = new HashMap<String,Spcode>();
			for(Spcode spcode : spcodeList){
				spcodeMap.put(spcode.getId(), spcode);
			}
			view.addObject("spcodeMap", spcodeMap);
			
			view.addObject("toPage","../userLimit/userLimitlist.jsp");
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	@RequestMapping(value = "/get/{idparam}/{m}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			UserLimit entity = new UserLimit();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = userLimitService.getById(idparam);				
			}
			view.addObject("m",m);
			view.addObject("toPage","../userLimit/userLimitmodify.jsp");
			view.addObject("entity", entity);
			
			List<Spcode> spcodeList = spcodeService.getList();
			view.addObject("spcodeList", spcodeList);
			
			
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(UserLimit entity,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			String id = entity.getId();
			if (StringUtils.isBlank(id)) {
				Date date = DateUtil.getCurDate();
				userLimitService.add(entity);
			} else{
				//当前登录者
				userLimitService.update(entity);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/userLimit/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			if(StringUtils.isNotBlank(idparam)){

				/*UserLimit oldspcode = userLimitService.getById(idparam);
				if(!oldspcode.getStatus().trim().equals(Constant.STATUS_INVALID)){
					logger.error("下线之后的数据才可以删除");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "下线之后的数据才可以删除");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}*/
								
				UserLimit param = new UserLimit();
				param.setId(idparam);
				userLimitService.update(param);
				/**
				 * 往日志中添加删除人的信息
				 */
				String loginName = getSessionUser().getLoginName();
				SimpleDateFormat sdf = new SimpleDateFormat(StringDateConstant.FMT_YMDHMS);
				Date date = new Date();
				String dateStr = sdf.format(date);                				
				
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/userLimit/index"));
	}
	
		
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				userLimitService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/userLimit/index"));
	}
}
