package com.system.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * 基础控制层框架
 * @author  Yang_gp 
 *
 */
@Controller
public class BaseController {
	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String LOGIN="system/login";
	public static final String INDEX="power/index";
	public static final String ADD="add";
	public static final String UPDATE="update";
	public static final String VIEW="view";
	public static final String ERROR_INFO="errorInfo";
	public static final String ERROR_PAGE="403";

	protected HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}
	
	
	/**
	 * 返回index页面，不包含提示信息
	 * @param response
	 * @return
	 * @throws IOException
	 */
	protected ModelAndView returnInfoJsp(ServletResponse response) {
		return returnInfoJsp(response, "index",null);
	} 
	/**
	 * 返回页面，包含提示信息
	 * @param response
	 * @param jspName 要返回的页面名称
	 * @param tipInfoMap 需要返回页面时包含的提示信息集合
	 * @return 返回页面，包含提示信息
	 */
	protected ModelAndView returnInfoJsp(ServletResponse response,String jspName,Map<String,Object> tipInfoMap) {
		ModelAndView view = new ModelAndView(jspName); 
		if(tipInfoMap != null && !tipInfoMap.isEmpty()){
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
	protected void returnInfoStr(ServletResponse response,String resContent) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(resContent);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 返回流形式的结果
	 * 
	 * @param response相应对象
	 * @throws IOException
	 */
	protected void returnInfoStr(ServletResponse response,byte[] resContent) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(resContent);
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	/**
	 * 返回json格式结果
	 * @param tipInfoMap Map
	 * @return
	 * @throws IOException
	 */
	protected ModelAndView returnInfoJson(Map<String,Object> tipInfoMap) throws  IOException {
		ModelAndView view = new ModelAndView(new MappingJacksonJsonView(),tipInfoMap); 
		return view;
	} 
	
}
