package com.zhrt.util;

import org.apache.commons.lang3.StringUtils;

import com.zhrt.bo.LatnVO;
import com.zhrt.entity.Subcode;

public class PhoneUtil {
	/**
	 * 从缓存中根据手机号查询号段信息
	 * @param phone
	 * @return
	 */
	public static Subcode getSubcodeByPhone(String phone){
		Subcode subcode = null;
		try {
			if(ValiUtil.valiPhone(phone)){				
				subcode = Constant.subcodeMap.get(phone.substring(0, 7));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return subcode;
	}
	
	/**
	 * 根据手机号获取所属省份信息
	 * @param phone
	 * @return
	 */
	public static LatnVO getLatnByPhone(String phone){
		Subcode subcode = getSubcodeByPhone(phone);
		LatnVO latn = Constant.LATN_MAP.get(subcode.getLatinId());
		return latn;
	}
	
	/**
	 * 获取手机号所属省份id
	 * @param phone
	 * @return
	 */
	public static int getProvinceIdByPhone(String phone){
		return getLatnByPhone(phone).getParentLatnId();
	}
	
	/**
	 * 根据手机号查询运营商类型 1：移动  2：联通  3：电信
	 * @param phone
	 * @return
	 */
	public static String getOperatorTypeByPhone(String phone){
		if(StringUtils.isNotBlank(phone)){
			String phoneOpre = phone.substring(0,3);
			if(Constant.oper_cmcc.contains(phoneOpre)){
				return "1";
			}else if(Constant.oper_cu.contains(phoneOpre)){
				return "2";
			}else if(Constant.oper_ct.contains(phoneOpre)){
				return "3";
			}
		}
		return "1";
	}
}
