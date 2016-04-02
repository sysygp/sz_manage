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
import com.zhrt.entity.MsgTypeInfo;
import com.zhrt.entity.NoticeTypeInfo;
import com.zhrt.service.NoticeTypeService;
import com.zhrt.util.Constant;


@Controller
@RequestMapping("/manager/zhrt/noticetype")
public class NoticeTypeController extends BaseController {

	@Autowired
	private NoticeTypeService noticeTypeService;

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
				int totalCount = noticeTypeService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<NoticeTypeInfo> noticeTypeList = noticeTypeService.getList(paramMap);
			view.addObject("noticeTypeList",noticeTypeList);
			page.setResult(noticeTypeList);
			view.addObject("page", page);
			view.addObject("toPage","../noticetype/noticetypelist.jsp");
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
			NoticeTypeInfo entity = new NoticeTypeInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = noticeTypeService.getById(idparam);				
			}
			view.addObject("m",m);
			view.addObject("toPage","../noticetype/noticetypemodify.jsp");
			view.addObject("entity", entity);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		return view;
	}
	
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(NoticeTypeInfo entity,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			String id = entity.getId();
			if (StringUtils.isBlank(id)) {
				Date date = DateUtil.getCurDate();
				entity.setStatus(String.valueOf(Constant.STATUS_VALID));
				entity.setCreateUser("admin");
				entity.setCreateTime(date);
				entity.setUpdateUser("admin");
				entity.setUpdateTime(date);
				noticeTypeService.add(entity);
			} else{
				//当前登录者
				entity.setUpdateUser("admin");
				entity.setUpdateTime(DateUtil.getCurDate());
				noticeTypeService.update(entity);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/noticetype/index"));
	}
	
	
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
				
		try {
			if(StringUtils.isNotBlank(idparam)){

				/*NoticeTypeInfo oldNoticeType = noticeTypeService.getById(idparam);
				if(!oldNoticeType.getStatus().trim().equals(Constant.STATUS_INVALID)){
					logger.error("下线之后的数据才可以删除");
					redirectAttributes.addFlashAttribute(ERROR_INFO, "下线之后的数据才可以删除");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}*/
				
				NoticeTypeInfo param = new NoticeTypeInfo();
				param.setId(idparam);
				param.setStatus(String.valueOf(Constant.STATUS_DEL));
				param.setUpdateUser("admin");
				param.setUpdateTime(DateUtil.getCurDate());
				noticeTypeService.update(param);
				
				/**
				 * 往日志中添加删除人的信息
				 */
				String loginName = getSessionUser().getLoginName();
				SimpleDateFormat sdf = new SimpleDateFormat(StringDateConstant.FMT_YMDHMS);
				Date date = new Date();
				String dateStr = sdf.format(date);                				
				logger.info(loginName+"于"+dateStr+"在平台删除了"+param.getNoticeTypeName()+"通知类型");
				
				
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, "未知错误，请稍后重试或联系系统管理员");
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/noticetype/index"));
	}
	
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				noticeTypeService.delByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}
		
		return new ModelAndView(new RedirectView("/manager/zhrt/noticetype/index"));
	}
}
