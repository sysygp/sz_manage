package com.zhrt.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.system.util.encryp.MD5Util;
import com.zhrt.bo.LatnVO;

public class Constant {
	public static final String SDKVER = "101";
	public static final String PLATID = "9";
	public static final String MAIN_CHANNELID = "240227101830021711";
	public static final String ACCOUNT_PWD_KEY = "ke*#2)7";

	//手机归属地Map
	public static Map<Integer, LatnVO> LATN_MAP = null;
	public static Map<String, Object> cpMap = null;//cp信息
	public static Map<String, Object> spMap = null;//cp信息
	public static Map<String, Object> spcodeMap = null;//cp信息
	public static Map<String, Object> channelMap = null;//渠道信息
	public static Map<String, Object> appMap = null;//产品信息
	public static Map<String, Object> channelAppMap = null;//渠道产品信息
	public static Map<String, Object> appVersionMap = null;//产品版本信息
	public static Map<String, Object> propMap = null;//道具信息
	public static Map<String, Object> platMap = null;//平台信息
	public static Map<String, Object> funcMap = null;//菜单信息
	public static Map<String, Object> moduleMap = null;//模块信息
	public static Map<String, Object> payTypeMap = null;//支付类型信息
	public static Map<String, Object> appSortMap = null;//游戏分类信息
	
	public static String genePwdMD5(String password) {
		return MD5Util.string2MD5(ACCOUNT_PWD_KEY + password + ACCOUNT_PWD_KEY);
	}

	// 处理结果
	public static final String DEAL_OK = "1";// 成功
	public static final String DEAL_ERR = "2";// 失败

	// 信息状态
	public static final int STATUS_VALID = 1;// 启用
	public static final int STATUS_INVALID = 2;// 停用
	public static final int STATUS_DEL = 3;//已删除

	// imei状态
	public static final int IMEI_LAST = 1; // 永久的
	public static final int IMEI_TEMP = 2; // 临时的

	public static final int FUNCTION_NOT_CHECKED = 0;	//权限未选中
	public static final int FUNCTION_CHECKED = 1;		//权限选中
	
	public static final String INTERFACE_TYPE_PAYPWD_SET = "31"; // 设置初始支付密码接口,不需要原密码，默认和初始登录密码一致
	public static final String INTERFACE_TYPE_PAYPWD_FORGET = "32"; // 重置支付密码接口,不需要原密码
	public static final String INTERFACE_TYPE_PAYPWD_MODIFY = "33"; // 修改支付密码接口,需要原密码
	
	public static final String TERMTYPE_MOBILE = "1";//手机
	public static final String TERMTYPE_PAD = "2";//平板
	public static final String TERMTYPE_UNKNOWN = "-1";//未知
	
	private static final String SEX_MALE = "1";//性别--男
	private static final String SEX_FEMALE = "2";//性别--女
	
	//操作类型
	public static final String OPERTYPE_LOGIN = "001";//用户登录
	public static final String OPERTYPE_ADD = "002";//新增
	public static final String OPERTYPE_MODIFY = "003";//修改
	public static final String OPERTYPE_DEL = "004";//删除
	public static final String OPERTYPE_RESETPWD = "005";//密码重置
	
	
	public static Map<String, String> STATUS_MAP = null;//状态MAP
	public static Map<String, String> BEHTYPE_MAP = null;//游客操作类型MAP
	public static Map<String, String> TERMTYPE_MAP = null;//终端类型MAP
	public static Map<String, String> SEX_MAP = null;//性别MAP
	public static Map<String, String> OPERTYPE_MAP = null;//操作类型MAP
	
	
	public static final String SYNGET = "1";
	public static final String SYNPOST = "2";
	public static Map<String, String> SYNTYPE_MAP = null;//同步类型MAP
	
	static{
		STATUS_MAP = new HashMap<String, String>();
		STATUS_MAP.put(STATUS_VALID+"", "上线");
		STATUS_MAP.put(STATUS_INVALID+"", "下线");
		STATUS_MAP.put(STATUS_DEL+"", "已删除");
		
		BEHTYPE_MAP = new HashMap<String, String>();
		BEHTYPE_MAP.put(STATUS_VALID+"", "创建");
		BEHTYPE_MAP.put(STATUS_INVALID+"", "退出");
		
		TERMTYPE_MAP = new HashMap<String, String>();
		TERMTYPE_MAP.put(TERMTYPE_MOBILE+"", "手机");
		TERMTYPE_MAP.put(TERMTYPE_PAD+"", "平板");
		TERMTYPE_MAP.put(TERMTYPE_UNKNOWN+"", "未知");
		
		SEX_MAP = new HashMap<String, String>();
		SEX_MAP.put(SEX_MALE, "男");
		SEX_MAP.put(SEX_FEMALE, "女");
		
		OPERTYPE_MAP = new TreeMap<String, String>();
		OPERTYPE_MAP.put(OPERTYPE_LOGIN, "用户登录");
		OPERTYPE_MAP.put(OPERTYPE_ADD, "新增");
		OPERTYPE_MAP.put(OPERTYPE_MODIFY, "修改");
		OPERTYPE_MAP.put(OPERTYPE_DEL, "删除");
		OPERTYPE_MAP.put(OPERTYPE_RESETPWD, "密码重置");
		
		SYNTYPE_MAP = new HashMap<String, String>();
		SYNTYPE_MAP.put(SYNGET, "GET");
		SYNTYPE_MAP.put(SYNPOST, "POST");
	}
	
	public static void main(String[] args) {
//		System.out.println(Constant.genePwdMD5("en28YIw89"));
		System.out.println("a,bc,d".indexOf(","));
	}
}
