package com.zhrt.action;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.system.util.http.HttpClientConnect;
import com.system.util.http.HttpClientConnectSZ;

public class Test {

	public static void main(String[] args) {
//		testGetChargeFile();
//		testChargeResult();
//		testDxm();
		
		List<String> list = new LinkedList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		
		for(String s : list){
			if(s.equals("b")){
				list.add("b1");
			}
			System.out.println(s);
		}
	}
	
	
	public static void testGetChargeFile(){
		String s = "{\"safeSoft\":\"否\",\"imei\":\"990004358901324\",\"factory\":\"SPH-L720\",\"mac\":\"40:0E:85:21:EA:74\",\"uuid\":\"cb197476-24fd-45d5-9941-471a37de9b89\",\"imsi\":\"\",\"channelapp\":\"28\",\"version\":\"4.2.2\"}";
//		String url = "http://49.213.8.34:8081/charge/getchargeinfo";
		String url = "http://localhost:8081/charge/getchargeinfo";
		System.out.println(url);
		System.out.println(s);
		String res = HttpClientConnectSZ.getResWithHClientPost(url, "UTF-8", s);
		System.out.println(res);
	}
	
	public static void testChargeResult(){
		String s = "{\"id\":\"718f5b28-2a4b-4363-a6a4-b2d829e8c388#24335251077595562#28\",\"times\":1}";
//		String url = "http://49.213.8.34:8081/charge/result";
		String url = "http://localhost:8081/charge/result";
		System.out.println(url);
		System.out.println(s);
		String res = HttpClientConnectSZ.getResWithHClientPost(url, "UTF-8", s);
		System.out.println(res);
	}
	
	public static void testDxm(){
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><messages><message mobile=\"15011209614\" content=\"460026003129788#6be5f83b-78e2-427d-99f9-35a4ad0a4788#28\" dateTime=\"2015-12-23 21:04:44\"/></messages>";
		String url = "http://localhost:8081/regist/sync";
//		String url = "http://49.213.8.34:8081/regist/sync";
		System.out.println(url);
		System.out.println(s);
		String res = HttpClientConnect.getResContentWithHClientPost(url, "UTF-8", s, null);
		System.out.println(res);
	}
	
}
