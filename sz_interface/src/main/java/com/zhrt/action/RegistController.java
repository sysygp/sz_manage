package com.zhrt.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.controller.BaseController;
import com.system.util.http.HttpUtil;
import com.zhrt.service.ChargeService;
import com.zhrt.util.ErrorCode;
import com.zhrt.util.XmlUtil;

/**
 * 短信猫同步注册信息接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/regist")
public class RegistController extends BaseController {

	@Autowired
	private ChargeService chargeService;

	@RequestMapping(value = "/sync", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView sync(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return deal(request, response, "sync");
	}
	
	@SuppressWarnings({ "rawtypes" })
	private ModelAndView deal(HttpServletRequest request, HttpServletResponse response,String srcMethod) throws Exception {
		int code = ErrorCode.SYSTEM_RIGHT;
		
		StringBuilder logStr = new StringBuilder("");
		logStr.append(request.getRequestURI()+"\r\n");
		
		try {
			
			Map paramMap = HttpUtil.getHttpReqInfo(request);
			String reqContentOri = paramMap.get(HttpUtil.PARAM_STR_NAME).toString();
			logStr.append("\t"+reqContentOri+"\r\n");
			
			Map map = XmlUtil.xml2Map(reqContentOri);
			
			if("sync".equals(srcMethod)){
				
				if(map != null){
					chargeService.regist(map);
				}
				
			}
		
		} catch (Exception e) {
			code = ErrorCode.SYSTEM_ERROR;
			e.printStackTrace();
		}
		
		returnInfoStr(response, code+"");
		logger.info(logStr.toString());
		return null;
	}
	
}
