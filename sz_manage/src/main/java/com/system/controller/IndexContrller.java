package com.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexContrller extends BaseController {
	
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("system/login");
		return view;
	}
	
//	@RequestMapping(value = "/views/common/**", method = { RequestMethod.GET, RequestMethod.POST })
	@RequestMapping(value = "/views/common/{errorpage}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView common(@PathVariable String errorpage,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView("common/403");
		return view;
	}
	
}
