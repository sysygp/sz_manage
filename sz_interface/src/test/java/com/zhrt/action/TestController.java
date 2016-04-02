package com.zhrt.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.system.controller.BaseController;
import com.system.util.encode.EncodeUtil;
import com.system.util.http.HttpClientConnectSZ;
import com.zhrt.util.EU;



@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
	
	public static void main(String[] args) {
		try {

			String[] urlAndContent = null;
//			urlAndContent = testGetuinfo();
			urlAndContent = testChargeInfo();
			
			//手动测试数据
//			urlAndContent = new String[2];
//			urlAndContent[0]="http://localhost:8061/uinfo/regist/9/";
//			urlAndContent[1]="[{\"valicodeseq\":\"b4e92135eca44f3bb5fbc848230c385c\",\"phone\":\"15011209614\",\"termOs\":\"4.4.2\",\"imei\":\"864387021893306\",\"valicode\":\"5261\",\"userid\":\"9_yrMHbwJZO6\",\"mac\":\"e0:19:1d:f3:9d:90\",\"chanAppVerSeq\":\"23\",\"termModel\":\"H60-L11\",\"platid\":\"9\",\"function\":\"110\",\"termType\":\"1\",\"token\":\"\",\"longitude\":\"0.0\",\"latitude\":\"0.0\",\"imsi\":\"460023107760808\"}]";
			
			
			
			
			String url=urlAndContent[0];
			String send = urlAndContent[1];
			
			
			StringBuffer logStr = new StringBuffer();
			logStr.append("发送地址："+url+"\n");
			logStr.append("发送包体原文："+urlAndContent[1]+"\n");
			
			String result= HttpClientConnectSZ.getResWithHClientPost(url, EncodeUtil.ENCODE, send);
			logStr.append("返回包体："+result+"\n");
			
			System.out.println(logStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String[] testChargeInfo(){
		String url = "http://localhost:8081/charge/getchargeinfo";
//		String url = "http://49.213.8.34:8081/charge/getchargeinfo";
		
		//获取注册报文
//		String data = "{\"channel\":\"buyu0001\",\"imei\":\"46780807667788\",\"imsi\":\"4600211782873897\",\"uuid\" :\"ddq8987d7duai2\",\"version\":\"4.3.3\",\"factory\":\"sumsung\",\"safeSoft\":\"是\"}";
		
		//获取计费报文
		String data = "{\"channelapp\":\"30\",\"factory\":\"\",\"imei\":\"356517052002725\",\"imsi\":\"460019807224876\",\"mac\":\"a\",\"safeSoft\":\"\",\"uuid\":\"356517052002725\",\"version\":\"\"}";
//		String data = "{\"safeSoft\":\"是\",\"imei\":\"862134028302697\",\"factory\":\"Coolpad8085Q\",\"mac\":\"00:16:6d:c9:14:09\",\"uuid\":\"347bff76-56dc-448e-b397-ed52887fbe35\",\"imsi\":\"460028144742605\",\"channelapp\":\"28\",\"version\":\"4.2.1\"}";
		
		
		return new String[]{url,data};
	}
}
