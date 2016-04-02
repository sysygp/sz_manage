package com.zhrt.util;

import com.system.util.stringutil.StringUtils;
import com.zhrt.entity.Spcode;
import com.zhrt.entity.UserLimit;

import redis.clients.jedis.Jedis;

public class CheckSpcodeAndUserLimit {

	/**
	 * 检查是否允许下发本条spcode
	 * @param spcode
	 * @param userLimit
	 * @param userUuid
	 * @return
	 */
	public static boolean check(Spcode spcode,UserLimit userLimit,String userUuid,String province){
		boolean flag = false;
		
		try {
			
			if(checkProvinceLimit(spcode, province)){
				Jedis jedis = RedisClient.getPool().getResource();
				jedis.select(0);
				if(checkDayLimit(spcode, userLimit, userUuid,jedis)){
					if(checkMonthLimit(spcode, userLimit, userUuid,jedis)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 检查省份是否允许
	 * @param spcode
	 * @param province
	 * @return
	 */
	public static boolean checkProvinceLimit(Spcode spcode,String province){
		boolean flag = true;
		
		if(province == null){
			return false;
		}
		
		String okProvince = spcode.getChargeProvince();
		if(StringUtils.isBlank(okProvince) || !okProvince.contains(province)){
			flag = false;
		}
		
		return flag;
	}
	/**
	 * 检查日限制是否允许
	 * @param spcode
	 * @param userLimit
	 * @param userUuid
	 * @return
	 */
	public static boolean checkDayLimit(Spcode spcode,UserLimit userLimit,String userUuid,Jedis jedis){
		boolean flag = true;
		
		if(!checkSpcodeDayLimit(spcode, userUuid, jedis)){
			flag = false;return flag;
		}
		if(!checkUserDayLimit(userLimit, spcode, userUuid, jedis)){
			flag = false;return flag;
		}
		
		return flag;
	}
	
	/**
	 * 检查spcode日限制是否允许
	 * @param spcode
	 * @param userUuid
	 * @param jedis
	 * @return
	 */
	public static boolean checkSpcodeDayLimit(Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		

		String key = RedisKeyUtils.getDayLimitKey();
		String spcodeLimitField = RedisKeyUtils.getSpcodeLimitField(spcode.getId(),userUuid);
		String spCodeDayUse = jedis.hget(key, spcodeLimitField);
		if(StringUtils.isBlank(spCodeDayUse)){
			spCodeDayUse = "0";
		}
		if(Integer.parseInt(spCodeDayUse)+Integer.parseInt(spcode.getChargeTimes()) > spcode.getLimitDayNum()){
			return false;
		}
	
		
		return flag;
	}
	
	/**
	 * 检查用户日限制是否允许
	 * @param userLimit
	 * @param spcode
	 * @param userUuid
	 * @param jedis
	 * @return
	 */
	public static boolean checkUserDayLimit(UserLimit userLimit,Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		
		String key = RedisKeyUtils.getDayLimitKey();
		String userLimitField = RedisKeyUtils.getUserLimitField(userUuid);
		String userDayUse = jedis.hget(key, userLimitField);
		if(StringUtils.isBlank(userDayUse)){
			userDayUse = "0";
		}
		if(Integer.parseInt(userDayUse)+Integer.parseInt(spcode.getChargeTimes()) > userLimit.getLimitDayNum()){
			return false;
		}
		
		return flag;
	}
	
	/**
	 * 检查月限制是否允许
	 * @param spcode
	 * @param userLimit
	 * @param userUuid
	 * @return
	 */
	public static boolean checkMonthLimit(Spcode spcode,UserLimit userLimit,String userUuid,Jedis jedis){
		boolean flag = true;
		if(!checkSpcodeMonthLimit(spcode, userUuid, jedis)){
			flag = false;return flag;
		}
		if(!checkUserMonthLimit(userLimit, spcode, userUuid, jedis)){
			flag = false;return flag;
		}
		return flag;
	}
	
	/**
	 * 检查spcode月限制是否允许
	 * @param spcode
	 * @param userUuid
	 * @param jedis
	 * @return
	 */
	public static boolean checkSpcodeMonthLimit(Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		

		String key = RedisKeyUtils.getMonthLimitKey();
		String spcodeLimitField = RedisKeyUtils.getSpcodeLimitField(spcode.getId(),userUuid);
		String spCodeMonthUse = jedis.hget(key, spcodeLimitField);
		if(StringUtils.isBlank(spCodeMonthUse)){
			spCodeMonthUse = "0";
		}
		if(Integer.parseInt(spCodeMonthUse)+Integer.parseInt(spcode.getChargeTimes()) > spcode.getLimitMonthNum()){
			return false;
		}
	
		
		return flag;
	}
	
	/**
	 * 检查用户月限制是否允许
	 * @param userLimit
	 * @param spcode
	 * @param userUuid
	 * @param jedis
	 * @return
	 */
	public static boolean checkUserMonthLimit(UserLimit userLimit,Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		
		String key = RedisKeyUtils.getMonthLimitKey();
		String userLimitField = RedisKeyUtils.getUserLimitField(userUuid);
		String userMonthUse = jedis.hget(key, userLimitField);
		if(StringUtils.isBlank(userMonthUse)){
			userMonthUse = "0";
		}
		if(Integer.parseInt(userMonthUse)+Integer.parseInt(spcode.getChargeTimes()) > userLimit.getLimitMonthNum()){
			return false;
		}
		
		return flag;
	}
}
