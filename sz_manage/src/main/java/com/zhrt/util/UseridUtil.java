package com.zhrt.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.system.util.system.RandomUtil;

/**
 * 
 * @Description:获取随机userid工具类
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年6月27日 上午10:38:10
 */
public class UseridUtil {
	private static int num = 0;
	
	/**
	 * 循环获取0-9的数字
	 * @return
	 */
	synchronized public static int getNumber(){
		if(num==9){
			num = 0;
			return 9;
		}
		return num++;
	}

	/**
	 * 获取9位userid,前8位是随机数,最后一位是0-9的循环
	 * @return
	 */
	public static String genUserid9() {
		String userId = "";
		
		String uuid = RandomUtil.genUuid8();
		int endNum = UseridUtil.getNumber();
		
		userId = uuid+endNum;
		return userId;
	}
	
	
	/**
	 * 生成注册用户userid
	 * platid_z_  +   8位数字  +  手机号最后一位
	 * @param phone
	 * @param platid
	 * @return
	 */
	public static String geneUidZ(String phone,String platid){
		StringBuffer userId = new StringBuffer(platid+"_");
		
		userId.append("z");
		String uuid = RandomUtil.genUuid8();
		userId.append(uuid).append(phone.charAt(phone.length()-1));
		
		
		return userId.toString();
	}
	
	/**
	 * 生成非注册用户userid
	 * @param imei
	 * @param platid
	 * @return
	 */
	public static String geneUidN(String imei,String platid){
		StringBuffer userId = new StringBuffer(platid+"_");
		
		//非注册永久用户
		if(StringUtils.isNotBlank(imei)){
			userId.append("y");
		}
		//非注册临时用户
		else{
			userId.append("l");
		}
		String uuid = UseridUtil.genUserid9();
		userId.append(uuid);
		
		
		return userId.toString();
	}
	
	
	/**
	 * 根据userid生成map对象,包含 LOrY和endTag 属性，以便定位到具体表
	 * @param userId
	 * @return
	 */
	public static Map locateUinfoTMap(String userId){
		Map map = new HashMap();
		
		if(StringUtils.isNotBlank(userId)){
			String platId = userId.substring(0,userId.indexOf("_"));
			map.put("platid", platId);
			
			String LOrYFlag = userId.charAt(userId.indexOf("_")+1)+"";
			if("y".equals(LOrYFlag)){
				map.put("LOrY", "y");
			}if("l".equals(LOrYFlag)){
				map.put("LOrY", "l");
			}
			
			map.put("appSeq", userId.charAt(userId.length()-1)+"");
			return map;
		}else{
			return map;
		}
	}
	
	/**
	 * 根据platid和phone定位到哪一张注册用户表，该方法只适用于注册用户表
	 * @param platId
	 * @param phone
	 * @return
	 */
	public static Map locateUinfoZTMap(String platId,String phone){
		Map map = new HashMap();
		
		map.put("platid", platId);
		map.put("appSeq", phone.charAt(phone.length()-1)+"");
		
		return map;
	}
}
