package com.zhrt.util;

public class ErrorCode {
	
	/*
	 * 处理流程错误码
	 */
	public static final int SYSTEM_RIGHT = 10000;//处理结束

	// 10100-10199 请求校验错误码
	public static final int PARAM_ERR = 10100;// 参数校验失败
	public static final int PARAM_PLATID_ERR = 10101;// paltid校验失败
	public static final int PARAM_IMEI_ERR = 10102;// imei校验失败
	public static final int PARAM_PHONE_ERR = 10103;// phone校验失败
	public static final int PARAM_CONFIRM_PASSWD_ERR = 10104;//两次密码输入不一致
	public static final int PARAM_VALICODE_INVALID = 10105;// 验证码无效
	public static final int PARAM_ACCOUNT_EXIST = 10106;// 帐号已经存在，已经注册过
	public static final int PARAM_OLDPWD_NEWPWD_EQUAL = 10107;  //新旧密码相同
	public static final int PARAM_VALICODE_NOTEXIST = 10108;// 验证码不存在
	public static final int PARAM_FUNCTION_ERR = 10109;         //function校验失败
	public static final int OLDPASSWD_ERR = 10110;       	    //旧密码输入不正确
	public static final int PARAM_ACCOUNT_NOTEXIST = 10111;// 帐号不存在
	public static final int PARAM_LOGININFO_ERR = 10112;		//账户或密码错误
	
	public static final int ACCOUNTMONEY_NOT_BALANCE = 10113;		//账户余额不足
	public static final int PARAM_NUMBER_ERR = 10114;		//数字格式不对
	public static final int PARAM_PAYPWD_ERR = 10115;		//支付密码不对
	public static final int PARAM_CHANAPPVERSEQ_NOTEXIST = 10116;// chanAppVerSeq不存在
	public static final int PARAM_ACCOUNTEXIST_GETERROR = 10117;// 帐号已经存在，已经注册过,但获取帐号信息失败
	
	public static final int PARAM_OPENID_ERROR = 10120;//第三方登录账户校验失败
	public static final int PARAM_BINDTYPE_ERROR = 10121;//绑定第三方类型校验失败
	public static final int OPENID_ALREADY_REG = 10121;//第三方账户已注册，不能绑定
	public static final int OPENID_ALREADY_BIND = 10123;//第三方账户已绑定
	public static final int PARAM_SIGNDATE_ERROR = 10124;//查询签到记录日期格式错误
	
	public static final int PARAM_SDKVER_ERROR = 10125;//SDK版本校验错误
	public static final int PARAM_APPDATAVER_ERROR = 10126;//游戏数据文件版本校验错误
	
	// 10200-10299 处理流程错误码
	public static final int DEAL_SENDSMS_ERR = 10201;// 下发验证码处理异常
	
	public static final int DEAL_ALREADY_SIGNIN_ERR = 10202;//用户已签到，不能重复签到


	// 99999 系统错误
	public static final int SYSTEM_ERROR = 99999;//系统错误，未知错误

}
