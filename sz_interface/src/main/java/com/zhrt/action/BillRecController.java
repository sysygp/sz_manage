package com.zhrt.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.system.controller.BaseController;
import com.system.util.http.HttpUtil;
import com.zhrt.service.DealBillService;
import com.zhrt.util.Constant;
import com.zhrt.util.ErrorCode;

/**
 * 接收流水同步接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bill")
public class BillRecController extends BaseController {

	@Autowired
	private DealBillService billRecService;

	/**
	 * 默认同一业务下不同资费的通道格式一致
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/receive/{spid}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView receive(@PathVariable String spid,HttpServletRequest request, HttpServletResponse response) throws Exception {
		return deal(spid,request, response, "receive");
	}
	
	@SuppressWarnings({ "rawtypes" })
	private ModelAndView deal(String spid,HttpServletRequest request, HttpServletResponse response,String srcMethod) throws Exception {
		String code = ErrorCode.SYSTEM_RIGHT+"";
		
		StringBuilder logStr = new StringBuilder("");
		logStr.append(request.getRequestURI()+"\r\n");
		
		try {
			
			Map paramMap = HttpUtil.getHttpReqInfo(request);
			String reqContentOri = paramMap.get(HttpUtil.PARAM_STR_NAME).toString();
			logStr.append("\t"+reqContentOri+"\r\n");
			
			
			if("receive".equals(srcMethod)){
				code = billRecService.addBill(spid,paramMap);
			}
		
		} catch (Exception e) {
			code = ErrorCode.SYSTEM_ERROR+"";
		}
		logStr.append("\t"+code+"\r\n");
		
		returnInfoStr(response, code);
		logger.info(logStr.toString());
		return null;
	}
	
}
