package com.zhrt.util.exception;

public class ParamException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String msg;

	public ParamException(String msg){
		super(msg);
		this.msg = msg;
	}
	
	public ParamException(int msg){
		super(msg+"");
		this.msg = msg+"";
	}
	
	public String getMsg(){
		return msg;
	}
}
