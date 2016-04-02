package com.zhrt.util;

public class AccountinfoUtil {
	
	/**
	 * 根据accountId(platid_phone)获取platid
	 * 
	 * @param accountId
	 * @return
	 */
	public static String genePlatidByAccountId(String accountId){
		String[] elements = accountId.split("_");
		return elements[0];
	}
	
	
}
