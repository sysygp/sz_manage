package com.zhrt.util.exception;

public class DealException extends Exception{

	private static final long serialVersionUID = 1L;

	private String msg;

	public DealException(String msg){
		super(msg);
		this.msg = msg;
	}
	
	public DealException(int msg){
		super(msg+"");
		this.msg = msg+"";
	}
	
	public String getMsg(){
		return msg;
	}
	
}
