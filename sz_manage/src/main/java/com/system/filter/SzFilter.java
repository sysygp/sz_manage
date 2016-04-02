package com.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.system.util.datasource.DynamicDataSource;

public class SzFilter implements Filter {
	private FilterConfig config;
	private String redirectURL;

	@Override
	public void init(FilterConfig config) {
		this.config = config;
		this.redirectURL = config.getInitParameter("redirectURL");
	}

	@Override
	public void destroy() {
		this.config = null;
		this.redirectURL = null;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", -1);
		
		String servletPath = request.getServletPath();
		if(servletPath.startsWith("/util/image.jsp")){
			System.out.println("a");
		}
		
		if(request.getSession().getAttribute("operUser")==null 
//				&& (servletPath.contains(".")) 
				&& !(servletPath.startsWith("/login/in") || servletPath.startsWith("/login/out") || servletPath.startsWith("/login/check") || servletPath.startsWith("/util/image.jsp") || servletPath.startsWith("/WEB-INF/views/system/login.jsp"))
				&& !(servletPath.endsWith(".css") || servletPath.endsWith(".js") || servletPath.endsWith(".png") || servletPath.endsWith(".jpeg"))
				){
			request.getRequestDispatcher("/login/out").forward(request, response);
			return;
		}else{
			String moduleIdSession = request.getParameter("moduleIdSession");
			String funcId = request.getParameter("funcId");
			if(StringUtils.isNotBlank(moduleIdSession)){			
				request.getSession().setAttribute("moduleIdSession", moduleIdSession);
			}if(StringUtils.isNotBlank(funcId)){
				request.getSession().setAttribute("funcId", funcId);
			}
			
			chain.doFilter(request,response);
		}
		
		
		/*String refer = request.getHeader("Referer");
		try {
			if(StringUtils.isBlank(refer)){				
				response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+ redirectURL);
			}else{
				chain.doFilter(request,response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}