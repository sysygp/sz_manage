package com.zhrt.action;

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
import com.system.util.system.RandomUtil;
import com.zhrt.entity.AppInfo;
import com.zhrt.entity.ChannelInfo;
import com.zhrt.entity.OperLog;
import com.zhrt.entity.Plat;
import com.zhrt.service.AppInfoService;
import com.zhrt.service.ChannelInfoService;
import com.zhrt.service.OperLogService;
import com.zhrt.service.PlatService;
import com.zhrt.service.SeqService;
import com.zhrt.util.CacheUtil;
import com.zhrt.util.Constant;

/**
 * 渠道信息管理
 * 
 * @author 
 * @createDate 2015年7月16日
 * @vision 1.0
 */
@Controller
@RequestMapping("/manager/zhrt/channelinfo")
public class ChannelInfoController extends BaseController {

	public final Logger tomyepaylog = LoggerFactory.getLogger("tomyepaylog");
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private PlatService platService;
	@Autowired
	private AppInfoService appInfoService;
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
	public ModelAndView index(ChannelInfo channelInfo,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView(INDEX);
		
		try {
			Map paramMap = new HashMap();
//			paramMap.put("channelCode", channelInfo.getChannelCode());
			String parentId = channelInfo.getParentId();
			//parentId为空或者为0时，查询所有的父渠道
			if(StringUtils.isBlank(parentId)||"0".equals(parentId)){
				paramMap.put("parentId", "0");
			}else{
				paramMap.put("parentId", parentId);
			}
			paramMap.put("cnName", channelInfo.getCnName());
			paramMap.put("status", channelInfo.getStatus());
			Page page = new Page(20);
			String pageNoParam = ServletRequestUtils.getStringParameter(request, "pageNo", "");
			String orderByParam = ServletRequestUtils.getStringParameter(request, "orderBy", "");
			String orderParam = ServletRequestUtils.getStringParameter(request, "order", "");
			page.setPage(page, paramMap, pageNoParam, orderByParam, orderParam);
			if (page.isAutoCount()) {
				int totalCount = channelInfoService.getListCount(paramMap);
				page.setTotalCount(totalCount);
			}
			List<ChannelInfo> channelInfoList = channelInfoService.getForPage(paramMap);
			view.addObject("channelInfoList", channelInfoList);
			page.setResult(channelInfoList);
			view.addObject("page", page);
			if(StringUtils.isBlank(parentId)||"0".equals(parentId)){
				//跳转到父渠道页面
				view.addObject("toPage", "../channel/channelinfolist.jsp");
			}else{
				//跳转到子渠道页面
				view.addObject("toPage", "../channel/childchannelinfolist.jsp");
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
			String parentId = ServletRequestUtils.getStringParameter(request, "parentId", "0");
			List<Plat> platList = platService.getList();
			ChannelInfo entity = new ChannelInfo();
			if (StringUtils.isNotBlank(idparam) && !idparam.equals("0")) {
				entity = channelInfoService.getById(idparam);
			}
			view.addObject("parentId", parentId);
			view.addObject("platList", platList);
			view.addObject("m", m);
			view.addObject("toPage", "../channel/channelinfomodify.jsp");
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
	 * @param channelInfo
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView modify(ChannelInfo channelInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
		
		PowerUser user = getSessionUser();
		OperLog log = new OperLog();
		log.setBussName("");
		log.setIp(getIp());
		log.setOperUser(user.getLoginName());
		log.setOperRole("");
		log.setOperTime(DateUtil.getCurDate());
		try {
			String id = channelInfo.getId();
			if (StringUtils.isBlank(id)) {
				log.setOperType(Constant.OPERTYPE_ADD);
				log.setOperDesc("渠道："+channelInfo.getCnName());

				try {
					Integer appSeq = seqService.getNextByName("channelSeq");
					channelInfo.setChannelId(String.valueOf(appSeq));
					
					//生成随机密码
					String passWord = RandomUtil.random(6);
					channelInfo.setPassWord(passWord);
					// 获取当前登录用户
					channelInfo.setCreatePerson("admin");
					channelInfo.setCreateTime(DateUtil.getCurDate());
					channelInfo.setLastUpdatePerson("admin");
					channelInfo.setLastUpdateTime(DateUtil.getCurDate());
					channelInfoService.add(channelInfo);
					log.setOperResult(Constant.DEAL_OK);
				} catch (Exception e) {
					tomyepaylog.info("本地创建渠道 "+channelInfo.getCnName()+" 失败，开始向myepay同步删除渠道");
					log.setOperResult(Constant.DEAL_ERR);
					redirectAttributes.addFlashAttribute(ERROR_INFO, "渠道创建失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
			
			
			} else {
				ChannelInfo oldChannelInfo = channelInfoService.getById(id);
				log.setOperType(Constant.OPERTYPE_MODIFY);
				log.setOperDesc("渠道："+oldChannelInfo.getCnName());
				// 是否修改了cnName
				boolean flag = !channelInfo.getCnName().equals(oldChannelInfo.getCnName());


				try {
					// 当前登录者
					channelInfo.setLastUpdatePerson("admin");
					channelInfo.setLastUpdateTime(DateUtil.getCurDate());
					channelInfoService.update(channelInfo);
					log.setOperResult(Constant.DEAL_OK);
				} catch (Exception e) {
					tomyepaylog.error("本地修改渠道 "+channelInfo.getCnName()+" 失败");
					log.setOperResult(Constant.DEAL_ERR);
					
					redirectAttributes.addFlashAttribute(ERROR_INFO, "渠道信息修改失败");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}
				int code = CacheUtil.cacheWrite(CacheUtil.CHANNEL, true);
			
			
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
		
		return new ModelAndView(new RedirectView("/manager/zhrt/channelinfo/index?parentId="+channelInfo.getParentId()));
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
		ChannelInfo channelInfo = new ChannelInfo();
		try {
			if (StringUtils.isNotBlank(idparam)) {
				channelInfo = channelInfoService.getById(idparam);
				log.setOperDesc("渠道："+channelInfo.getCnName());
				//查询渠道运营的产品
				List<AppInfo> appList = appInfoService.getByChannel(idparam);
				if(appList!=null&&!appList.isEmpty()){
					log.setOperResult(Constant.DEAL_ERR);
					logger.info(channelInfo.getCnName()+"下运营产品，无法删除!");
					
					redirectAttributes.addFlashAttribute(ERROR_INFO, channelInfo.getCnName()+"下运营产品，无法删除!");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}else if(String.valueOf(Constant.STATUS_VALID).equals(channelInfo.getStatus())){
					log.setOperResult(Constant.DEAL_ERR);
					logger.info(channelInfo.getCnName()+"当前处于上线状态，无法删除!");
					
					redirectAttributes.addFlashAttribute(ERROR_INFO, channelInfo.getCnName()+"当前处于上线状态，无法删除!");
					return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
				}else{
					String channelId = "";
					if (channelInfo != null) {
						channelId = channelInfo.getChannelId();
					}

//					channelInfoService.delById(idparam);
					try {
						ChannelInfo param = new ChannelInfo();
						param.setId(idparam);
						param.setStatus(String.valueOf(Constant.STATUS_DEL));
						param.setLastUpdatePerson("admin");
						param.setLastUpdateTime(DateUtil.getCurDate());
						channelInfoService.update(param);
						log.setOperResult(Constant.DEAL_OK);
					} catch (Exception e) {
						tomyepaylog.error("本地删除渠道 "+channelInfo.getCnName()+" 失败");
						log.setOperResult(Constant.DEAL_ERR);
						redirectAttributes.addFlashAttribute(ERROR_INFO, "渠道信息删除失败");
						return new ModelAndView(new RedirectView(request.getHeader("REFERER")));
					}
				
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
		return new ModelAndView(new RedirectView("/manager/zhrt/channelinfo/index?parentId="+channelInfo.getParentId()));
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delmul", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView delBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> tipInfoMap = new HashMap<String, Object>();
		try {
			String[] ids = ServletRequestUtils.getStringParameters(request, "ids");
			if (ids != null && ids.length > 0) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("ids", ids);
				channelInfoService.delByIds(paramMap);
			}
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			tipInfoMap.put(ERROR_INFO, ERROR_INFO_CONTENT);
			return this.returnInfoJsp(response, INDEX, tipInfoMap);
		}
		return new ModelAndView(new RedirectView("/manager/zhrt/channelinfo/index"));
	}
	
	/**
	 * 
	 * 重置渠道密码
	 * 创建人：
	 * 创建时间: 2015年8月18日 上午11:09:40
	 * 修改人：
	 * 修改时间：
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
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
			//渠道Id
			String id = ServletRequestUtils.getStringParameter(request, "channelId");
			String passWord = RandomUtil.random(6);
			ChannelInfo channelInfo = channelInfoService.getById(id);
			log.setOperDesc("渠道："+channelInfo.getCnName());
			channelInfo.setId(id);
			channelInfo.setPassWord(passWord);
			channelInfo.setLastUpdatePerson("admin");
			channelInfo.setLastUpdateTime(DateUtil.getCurDate());
			channelInfoService.update(channelInfo);
			log.setOperResult(Constant.DEAL_OK);
			map.put("userName", channelInfo.getUserName());
			map.put("passWord", passWord);
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
	
	/**
	 * 
	 * 查询推广某个产品版本的渠道
	 * 创建人：
	 * 创建时间: 2015年9月7日 下午4:19:25
	 * 修改人：
	 * 修改时间：
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getByVerId",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ModelAndView getByVerId(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		ModelAndView view = new ModelAndView();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			String verId = ServletRequestUtils.getStringParameter(request, "verId");
			String status = ServletRequestUtils.getStringParameter(request, "status");
			param.put("verId", verId);
			param.put("status", status);
			List<ChannelInfo> channelList = channelInfoService.getByVerId(param);
			map.put("result", channelList);
			view = returnInfoJson(response, map);
		} catch (Exception e) {
			logger.error(ERROR_INFO_CONTENT);
			logger.error(e.getMessage());
		}
		return view;
	}
}
