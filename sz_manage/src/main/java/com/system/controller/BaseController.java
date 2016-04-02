package com.system.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.power.entity.PowerUser;

/**
 * 基础控制层框架
 * 
 * @author Yang_gp
 *
 */
@Controller
public class BaseController {
	public final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String LOGIN = "system/login";
	public static final String INDEX = "power/index";
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String VIEW = "view";
	public static final String ERROR_INFO = "errorInfo";
	public static final String ERROR_INFO_CONTENT = "未知错误，请稍后重试或联系系统管理员";
	public static final String ERROR_PAGE = "403";

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**
	 * 获取session对象
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 设置session
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	protected void setSessAttr(String name, Object val) {
		getSession().setAttribute(name, val);
	}

	/**
	 * 从session中获取值，自动类型转换
	 * 
	 * @param key
	 * @return
	 */
	protected <T> T getSessAttr(String key) {
		Object obj = getSession().getAttribute(key);
		try {
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取登录用户信息
	 * 
	 * @return
	 */
	protected PowerUser getSessionUser() {
		return getSessAttr("operUser");
	}

	/**
	 * 返回index页面，不包含提示信息
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView returnInfoJsp(ServletResponse response) {
		return returnInfoJsp(response, "index", null);
	}

	/**
	 * 返回页面，包含提示信息
	 * 
	 * @param response
	 * @param jspName
	 *            要返回的页面名称
	 * @param tipInfoMap
	 *            需要返回页面时包含的提示信息集合
	 * @return 返回页面，包含提示信息
	 */
	public ModelAndView returnInfoJsp(ServletResponse response, String jspName, Map<String, Object> tipInfoMap) {
		ModelAndView view = new ModelAndView(jspName);
		if (tipInfoMap != null && !tipInfoMap.isEmpty()) {
			for (Map.Entry<String, Object> entry : tipInfoMap.entrySet()) {
				view.addObject(entry.getKey(), entry.getValue());
			}
		}
		return view;
	}

	/**
	 * 返回流形式的结果
	 * 
	 * @param response相应对象
	 * @throws IOException
	 */
	public void returnInfoErr(ServletResponse response, String tipInfo) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(tipInfo);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 返回json格式结果
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView returnInfoJson(ServletResponse response, Map<String, Object> tipInfoMap) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		Map<String, Object> model = new HashMap<String, Object>();
		if (tipInfoMap != null && !tipInfoMap.isEmpty()) {
			for (Map.Entry<String, Object> entry : tipInfoMap.entrySet()) {
				model.put(entry.getKey(), entry.getValue());
			}
		}
		ModelAndView view = new ModelAndView(new MappingJacksonJsonView(), model);
		return view;
	}

	/**
	 * 
	 * 获取客户端Ip 
	 * 创建人： 
	 * 创建时间: 2015年8月21日 上午11:58:20 
	 * 修改人： 
	 * 修改时间：
	 * 
	 * @param request
	 * @return
	 */
	public String getIp() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
