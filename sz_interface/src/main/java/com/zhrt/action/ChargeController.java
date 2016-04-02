package com.zhrt.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.system.controller.BaseController;
import com.system.util.encode.BASE64;
import com.system.util.encryp.CryptoEncode;
import com.system.util.http.HttpUtil;
import com.system.util.json.JsonDateValueProcessor;
import com.system.util.json.JsonUtil;
import com.zhrt.bo.InfoReqVO;
import com.zhrt.bo.ResBaseVO;
import com.zhrt.bo.ResultReqVO;
import com.zhrt.service.ChargeService;
import com.zhrt.util.Constant;
import com.zhrt.util.EU;
import com.zhrt.util.ErrorCode;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 获取注册短信或计费方案接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/charge")
public class ChargeController extends BaseController {

	@Autowired
	private ChargeService chargeService;

	/**
	 * 注册接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getregistinfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView getregistinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return deal(request, response, "getregistinfo");
	}
	
	/**
	 * 更新计费方案接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getchargeinfo", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView getchargeinfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return deal(request, response, "getchargeinfo");
	}
	
	/**
	 * 上传计费结果接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/result", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ModelAndView result(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return deal(request, response, "result");
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ModelAndView deal(HttpServletRequest request, HttpServletResponse response,String srcMethod) throws Exception {
		int code = ErrorCode.SYSTEM_RIGHT;
		Object info = null;
		
		StringBuilder logStr = new StringBuilder("");
		logStr.append(request.getRequestURI()+"\r\n");
		
		try {
			
			InputStream is = request.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int size = 0;
			while ((size = is.read(b)) != -1) {
				out.write(b, 0, size);
			}
			byte[] bytes = EU.getInstance().decode(out.toByteArray());
			String reqContentOri = new String(bytes);
			is.close();
			out.flush();
			out.close();
			logStr.append("\t"+reqContentOri+"\r\n");
			
			
			String reqContentReal = reqContentOri;
			
			
			if("getregistinfo".equals(srcMethod)){
				
				InfoReqVO infoReqVO = (InfoReqVO) JsonUtil.json2Clazz(reqContentReal.toString(), InfoReqVO.class);
				String reqIp = request.getRemoteAddr();
				infoReqVO.setReqIp(reqIp);
				if(infoReqVO != null){
//					info = chargeService.getregistinfo(infoReqVO);
					
					//获取注册信息和获取计费通道信息调用同一个service，返回相同的报文
					info = chargeService.generateChargeinfo(infoReqVO);
				}
			}else if("getchargeinfo".equals(srcMethod)){
					
				InfoReqVO infoReqVO = (InfoReqVO) JsonUtil.json2Clazz(reqContentReal.toString(), InfoReqVO.class);
				String reqIp = request.getRemoteAddr();
				infoReqVO.setReqIp(reqIp);
				if(infoReqVO != null){
					info = chargeService.generateChargeinfo(infoReqVO);
				}
			}else if("result".equals(srcMethod)){
				
				ResultReqVO resultReqVO = (ResultReqVO) JsonUtil.json2Clazz(reqContentReal.toString(), ResultReqVO.class);
				if(resultReqVO != null){
					chargeService.result(resultReqVO);
				}
				
			}
		
		} catch (Exception e) {
			code = ErrorCode.SYSTEM_ERROR;
		}
		
		ResBaseVO resVo = new ResBaseVO();
		resVo.setCode(String.valueOf(code));
		resVo.setInfo(info);
		
		
		SerializerFeature[] featureArr = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteNullListAsEmpty };
		String jsonStr = (JSON.toJSONString(resVo,featureArr)).replace("null", "\"\"");
		logStr.append("\t"+jsonStr+"\r\n");
		logger.info(logStr.toString());
		
		byte[] content = EU.getInstance().encode(jsonStr.toString().getBytes("UTF8"));
		
		response.getOutputStream().write(content);
		return null;
	}
	
}
