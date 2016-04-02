package com.zhrt.util;

import java.util.regex.Pattern;

import com.system.util.stringutil.StringUtils;


public class ValiUtil {
	
	/**
	 * 验证2个String参数去空格后是否相同
	 * @param field1
	 * @param field2
	 * @return
	 */
	public static boolean valiFieldEqual(String field1,String field2){
		boolean flag = false;
		if(field1 != null && field2 != null && field1.trim().equals(field2.trim())){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 验证imei是否合法
	 * @param imei
	 * @return
	 */
	public static boolean valiImei(String imei){
		boolean flag = false;
		if(StringUtils.isNotBlank(imei) && !imei.equals("000000000000000") && imei.length() == 15){
			flag = true; 
		}
		return flag;
	}
	
	/**
	 * 验证手机号是否合法
	 * @param phone
	 * @return
	 */
	public static boolean valiPhone(String phone){
		boolean flag = true;
		if(StringUtils.isBlank(phone) || phone.length() != 11 || !phone.startsWith("1")){
			flag = false; 
		}
		return flag;
	}
	
	/**
	 * 验证两次密码输入是否一致
	 * @param passwd
	 * @param passwd2
	 * @return
	 */
	public static boolean confirmPwd(String passwd,String passwd2){
		boolean flag = true;
		if(StringUtils.isBlank(passwd) || StringUtils.isBlank(passwd2) || passwd.trim().length() <6 || passwd.trim().length() > 12){
			flag = false; return flag;
		}
		if(!valiFieldEqual(passwd, passwd2)){
			flag = false; return flag;
		}
		return flag;
	}
	
	/**
	 * 验证密码
	 * @param password
	 * @return
	 */
	public static boolean valiPwd(String password){
		boolean flag = true;
		if(StringUtils.isBlank(password) || password.trim().length() <6 || password.trim().length() > 12){
			flag = false; 
		}
		return flag;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean valiStr(String str){
		boolean flag = false;
		if(StringUtils.isNotBlank(str)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断字符串是否为数字（包含整数 小数）
	 * @param str
	 * @return
	 */
	public static boolean valiDigital(String number){
		boolean flag = false;
		Pattern pattern = Pattern.compile("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$"); 
		if(StringUtils.isNotBlank(number) && pattern.matcher(number).matches()){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断字符串是否为整数
	 * @param str
	 * @return
	 */
	public static boolean valiNumeric(String str){
		boolean flag = false;
		Pattern pattern = Pattern.compile("[0-9]*"); 
		if(StringUtils.isNotBlank(str) && pattern.matcher(str).matches()){
			flag = true;
		}
		return flag;    
	}
	
	
	/**
	 * 校验获取验证码参数
	 * @param platId
	 * @param platidParam
	 * @param phone
	 * @param function
	 * @return
	 */
	public static String valiValicode(String platId,String platidParam,String phone,String function,String chanAppVerSeq){
		String status = Constant.DEAL_OK+"";; 
		
		if(!ValiUtil.valiFieldEqual(platId, platidParam)){
			status = ErrorCode.PARAM_PLATID_ERR+"";return status;
		}
		if(!ValiUtil.valiPhone(phone)){
			status = ErrorCode.PARAM_PHONE_ERR+"";return status;
		}
		if(StringUtils.isBlank(function)){
			status = ErrorCode.PARAM_FUNCTION_ERR+"";return status;
		}
		if(!ValiUtil.valiStr(chanAppVerSeq)){
			status = ErrorCode.PARAM_CHANAPPVERSEQ_NOTEXIST+"";return status;
		}
		
		return status;
	}
	
	/**
	 * 校验修改账户密码参数
	 * @param platId
	 * @param platidParam
	 * @param phone
	 * @param function
	 * @param accountPwdNew
	 * @param confirmPwdNew
	 * @return
	 */
	public static String valiAccountPwd(String platId,String platidParam,String phone,String function,
			String accountPwdNew,String confirmPwdNew,String chanAppVerSeq){
		String status = Constant.DEAL_OK+"";; 
		
		if(!ValiUtil.valiFieldEqual(platId, platidParam)){
			status = ErrorCode.PARAM_PLATID_ERR+"";
		}
		if(!ValiUtil.confirmPwd(accountPwdNew, confirmPwdNew)){
			status = ErrorCode.PARAM_CONFIRM_PASSWD_ERR+"";
		}
		if(!ValiUtil.valiPhone(phone)){
			status = ErrorCode.PARAM_PHONE_ERR+"";
		}
		if(!ValiUtil.valiNumeric(function)){
			status = ErrorCode.PARAM_FUNCTION_ERR+"";
		}
		if(!ValiUtil.valiStr(chanAppVerSeq)){
			status = ErrorCode.PARAM_CHANAPPVERSEQ_NOTEXIST+"";
		}
		
		return status;
	}
	
	/**
	 * 判断accountId是否合法
	 * @param accountId
	 * @return
	 */
	public static boolean valiAccountId(String accountId){
		boolean flag = false;
		
		if(StringUtils.isNotBlank(accountId) && accountId.contains("_")){
			String[] elements = accountId.split("_");
			String platid = elements[0];
			String phone = elements[1];
			
			if(valiNumeric(platid) && valiNumeric(phone)){
				flag = true;
			}
		}
		return flag;
	}
	

	/**
	 * 判断多个参数是否为空
	 * @param objs
	 * @return
	 */
	public static boolean valiParams(Object...objs){
		boolean flag = false;
		if(objs!=null && objs.length > 0){
			int i = 0;
			
			for(Object obj : objs){
				if(obj!=null && ((CharSequence) obj).length()>0){
					i++;
				}else{
					break;
				}
			}
			
			if(i == objs.length){
				flag = true;
			}
		}
		return flag;    
	}
}
