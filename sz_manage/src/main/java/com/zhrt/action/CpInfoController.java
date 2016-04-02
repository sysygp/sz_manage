package com.zhrt.action;

import java.util.ArrayList;
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
import com.system.util.system.RandomUtil;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.CpInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.CpInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.util.CacheUtil;
import com.zhrt.util.Constant;
import com.zhrt.util.ErrorCode;

@Controller
@RequestMapping("/manager/zhrt/cpinfo")
public class CpInfoController extends BaseController{

	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private AppInfoService appInfoService;
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
	public ModelAndView index(CpInfo cpInfo,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			Map paramMap = new HashMap();
//			paramMap.put("cpCode", cpInfo.getCpCode());
			paramMap.put("status", cpInfo.getStatus());
			paramMap.put("cpName", cpInfo.getCpName());
			Page page = new Page(20);
			String pageNoParam =ServletRequestUtils.getStringParameter(request,"pageNo","");
			String orderByParam =ServletRequestUtils.getStringParameter(request,"orderBy","");
			String orderParam =ServletRequestUtils.getStringParameter(request,"order","");
			page.setPage(page, paramMap, pageNoParam,orderByParam,orderParam);
			if (page.isAutoCount()) {
				int totalCount = cpInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			//关联查询出运营的产品数量
			List<CpInfo> cpInfoList = cpInfoService.getForPage(paramMap);
			view.addObject("cpInfoList",cpInfoList);
			page.setResult(cpInfoList);
			view.addObject("page", page);
			view.addObject("toPage","../cpinfo/cpinfolist.jsp");
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
	public ModelAndView get(@PathVariable String idparam,@PathVariable String m,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			CpInfo entity = new CpInfo();
			List<AppInfo> appList = new ArrayList<>();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = cpInfoService.getById(idparam);
				//查询cp下运营的产品
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("cpId", idparam);
				param.put("status", Constant.STATUS_VALID);
				appList = appInfoService.getList(param);
			}
			view.addObject("appList",appList);
			view.addObject("m",m);
			view.addObject("toPage","../cpinfo/cpinfomodify.jsp");
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
	 * @param cpInfo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(CpInfo cpInfo,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		PowerUser user = getSessionUser();
		//生成操作日志
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperUser(user.getLoginName());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		try {
			String id = cpInfo.getId();
			if (StringUtils.isBlank(id)) {
				log.setOperType(Constant.OPERTYPE_ADD);
				log.setOperDesc("CP："+cpInfo.getCpName());
				//生成随机密码
				String passWord = RandomUtil.random(6);
				cpInfo.setPassWord(passWord);
				//获取当前登录用户
				cpInfo.setCreatePerson("admin");
				cpInfo.setCreateTime(DateUtil.getCurDate());
				cpInfo.setLastUpdatePerson("admin");
				cpInfo.setLastUpdateTime(DateUtil.getCurDate());
				cpInfoService.add(cpInfo);
				log.setOperResult(Constant.DEAL_OK);
				
			} else{
				log.setOperType(Constant.OPERTYPE_MODIFY);
				log.setOperDesc("CP："+cpInfo.getCpName());
				//将cp下线时判断cp下是否有运营产品
				if(String.valueOf(Constant.STATUS_INVALID).equals(cpInfo.getStatus())){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("cpId", cpInfo.getId());
					paramMap.put("status",Constant.STATUS_VALID);
					//查询cp下运营产品
					List<AppInfo> appList = appInfoService.getList(paramMap);
					if(appList!=null&&appList.size()>0){
						logger.info(cpInfo.getCpName()+"下运营产品，不能被下线!");
						log.setOperResult(Constant.DEAL_ERR);						
						redirectAttributes.addFlashAttribute(ERROR_INFO, cpInfo.getCpName()+"下运营产品，不能被下线!");
						return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
					}
				}
				//当前登录者
				cpInfo.setLastUpdatePerson("admin");
				cpInfo.setLastUpdateTime(DateUtil.getCurDate());
				cpInfoService.update(cpInfo);
				log.setOperResult(Constant.DEAL_OK);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			log.setOperResult(Constant.DEAL_ERR);
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/cpinfo/index"));
	}
	
	/**
	 * 单条记录删除
	 * @param idparam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del/{idparam}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView del(@PathVariable String idparam,HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws Exception {
		
		PowerUser user = getSessionUser();
		//生成操作日志
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperUser(user.getLoginName());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_DEL);
		try {
			if(StringUtils.isNotBlank(idparam)){
				CpInfo cpInfo = cpInfoService.getById(idparam);
				log.setOperDesc("CP："+cpInfo.getCpName());
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("cpId", idparam);
				paramMap.put("status",Constant.STATUS_VALID);
				//查询cp下运营产品
				List<AppInfo> appList = appInfoService.getList(paramMap);
				if(appList!=null&&!appList.isEmpty()){
					logger.info(cpInfo.getCpName()+"下运营产品，无法删除!");
					
					log.setOperResult(Constant.DEAL_ERR);
					redirectAttributes.addFlashAttribute(ERROR_INFO, cpInfo.getCpName()+"下运营产品，无法删除!");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
				//查询cp是否为上线状态
				if(String.valueOf(Constant.STATUS_VALID).equals(cpInfo.getStatus())){
					logger.info(cpInfo.getCpName()+"当前处于上线状态，无法删除!");
					
					log.setOperResult(Constant.DEAL_ERR);
					
					redirectAttributes.addFlashAttribute(ERROR_INFO, cpInfo.getCpName()+"当前处于上线状态，无法删除!");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
//				cpInfoService.delById(idparam);
				CpInfo param = new CpInfo();
				param.setId(idparam);
				param.setStatus(String.valueOf(Constant.STATUS_DEL));
				param.setLastUpdatePerson("admin");
				param.setLastUpdateTime(DateUtil.getCurDate());
				cpInfoService.delById(idparam);
				log.setOperResult(Constant.DEAL_OK);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
			redirectAttributes.addFlashAttribute(ERROR_INFO, ERROR_INFO_CONTENT);
			return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
		}finally{
			//记录操作日志
			operLogService.add(log);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/cpinfo/index"));
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> tipInfoMap = new HashMap<String,Object>();
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request,"ids");
			if(ids!=null && ids.length > 0){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ids", ids);
				cpInfoService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO,ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response,INDEX,tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/cpinfo/index"));
	}
	/**
	 * 
	 * 重置cp密码
	 * 创建人：
	 * 创建时间: 2015年8月10日 下午5:15:27
	 * 修改人：
	 * 修改时间：
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/resetpwd",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView resetPwd(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();
		PowerUser user = getSessionUser();
		//生成操作日志
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperUser(user.getLoginName());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		log.setOperType(Constant.OPERTYPE_RESETPWD);
		try {
			String id = ServletRequestUtils.getRequiredStringParameter(request, "cpId");
//			String pwd = ServletRequestUtils.getRequiredStringParameter(request, "pwd");
			view = new ModelAndView();
			CpInfo cp = cpInfoService.getById(id);
			log.setOperDesc("CP："+cp.getCpName());
			cp.setId(id);
			String pwd = RandomUtil.random(6);
			cp.setPassWord(pwd);
			cp.setLastUpdatePerson("admin");
			cp.setLastUpdateTime(DateUtil.getCurDate());
			cpInfoService.update(cp);
			log.setOperResult(Constant.DEAL_OK);
			map.put("userName", cp.getUserName());
			map.put("passWord", cp.getPassWord());
			view = returnInfoJson(response, map);
		} catch (Exception e) {
			log.setOperResult(Constant.DEAL_ERR);
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
		}finally{
			operLogService.add(log);
		}
		return view;
	}
}
