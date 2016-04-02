package com.zhrt.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ObjectSendUtil {
	private static Log log = LogFactory.getLog(ObjectSendUtil.class);
	/**
	 * 向买壹贝网关发送信息
	 * 以字符串形式接收数据
	 */
	public static JSONObject SendUtil(String obj,String url, String syscmd) {
		String result = "";
		try {
			// 客户端管理文件扩展名
			int statusCode;
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(80000);// 设置连接超时时间
			client.getHttpConnectionManager().getParams().setSoTimeout(180000);// 设置读取数据超时时间
			// myepay网关url
			PostMethod method = new PostMethod(url);
			StringBuffer bff = new StringBuffer();
			bff.append(obj);
			bff.append("&cmd=" + syscmd);
			bff.append("&sourceType=" + "userSys100");
			// 添加专递的字符串
			String encodeRequest = URLEncoder.encode(bff.toString(), "UTF-8");
			method.addParameter("data", encodeRequest);
			statusCode = client.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				result = method.getResponseBodyAsString();
				log.info("提交成功");
				log.info("返回信息显示" + result);
			} else {
				log.info("提交失败!");
			}
		} catch (Exception ex) {
			log.error(ex.toString());
			// logger.error("执行 向用户发送信息的异常 :  ex=" + ex.getMessage());
			// return Integer.parseInt(Constant.CODE_Process_WRONG);
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	/**
	 * 向买壹贝网关发送信息
	 * 以流的形式接收返回数据
	 */
	public static JSONObject receiveByStream(String obj, String url,
			String syscmd) {
		String result = "";
		try {
			// 客户端管理文件扩展名
			int statusCode;
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(80000);// 设置连接超时时间
			client.getHttpConnectionManager().getParams().setSoTimeout(180000);// 设置读取数据超时时间
			// myepay网关url
			PostMethod method = new PostMethod(url);
			StringBuffer bff = new StringBuffer();
			bff.append(obj);
			bff.append("&cmd=" + syscmd);
			bff.append("&sourceType=" + "userSys100");
			// 添加专递的字符串
			String encodeRequest = URLEncoder.encode(bff.toString(), "UTF-8");
			method.addParameter("data", encodeRequest);
			statusCode = client.executeMethod(method);
			InputStream resStream = method.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					resStream));
			StringBuffer resBuffer = new StringBuffer();
			String resTemp = "";
			while ((resTemp = br.readLine()) != null) {
				resBuffer.append(resTemp);
			}
			result = resBuffer.toString();
			System.out.println(result);
		} catch (Exception ex) {
			log.error(ex.toString());
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
}
