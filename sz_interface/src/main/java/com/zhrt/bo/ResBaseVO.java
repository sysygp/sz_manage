package com.zhrt.bo;

/**
 * 
 * @Description:返回报文的整体格式模版
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年6月26日 下午6:34:32
 */
public class ResBaseVO {

	private String code;//状态码
	private Object info;//报文内容
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}
	
}
