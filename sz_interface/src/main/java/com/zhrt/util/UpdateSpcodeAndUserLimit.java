package com.zhrt.util;

import com.zhrt.entity.Spcode;
import com.zhrt.entity.SpcodeLimit;
import com.zhrt.entity.UserLimit;

import redis.clients.jedis.Jedis;

public class UpdateSpcodeAndUserLimit {

	/**
	 * 检查是否允许下发本条spcode
	 * @param spcode
	 * @param userLimit
	 * @param userUuid
	 * @return
	 */
	public static boolean update(Spcode spcode,UserLimit userLimit,String userUuid){
		boolean flag = false;
		
		try {
			Jedis jedis = RedisClient.getPool().getResource();
			jedis.select(0);
			if(updateDayLimit(spcode, userLimit, userUuid,jedis)){
				if(updateMonthLimit(spcode, userLimit, userUuid,jedis)){
					return true;
				}
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
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
	public static boolean updateDayLimit(Spcode spcode,UserLimit userLimit,String userUuid,Jedis jedis){
		boolean flag = true;
		
		if(!updateSpcodeDayLimit(spcode, userUuid, jedis)){
			flag = false;return flag;
		}
		if(!updateUserDayLimit(userLimit, spcode, userUuid, jedis)){
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
	public static boolean updateSpcodeDayLimit(Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		
		String key = RedisKeyUtils.getDayLimitKey();
		String spcodeLimitField = RedisKeyUtils.getSpcodeLimitField(spcode.getId(),userUuid);
		jedis.hincrBy(key, spcodeLimitField, Long.parseLong(spcode.getChargeTimes().toString()));
		jedis.pexpireAt(key, DateUtil.getNextDayBegin());
		
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
	public static boolean updateUserDayLimit(UserLimit userLimit,Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		
		String key = RedisKeyUtils.getDayLimitKey();
		String userLimitField = RedisKeyUtils.getUserLimitField(userUuid);
		jedis.hincrBy(key, userLimitField, Long.parseLong(spcode.getChargeTimes().toString()));
		jedis.pexpireAt(key, DateUtil.getNextDayBegin());
		
		return flag;
	}
	
	/**
	 * 检查月限制是否允许
	 * @param spcode
	 * @param userLimit
	 * @param userUuid
	 * @return
	 */
	public static boolean updateMonthLimit(Spcode spcode,UserLimit userLimit,String userUuid,Jedis jedis){
		boolean flag = true;
		if(!updateSpcodeMonthLimit(spcode, userUuid, jedis)){
			flag = false;return flag;
		}
		if(!updateUserMonthLimit(userLimit, spcode, userUuid, jedis)){
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
	public static boolean updateSpcodeMonthLimit(Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		
		String key = RedisKeyUtils.getMonthLimitKey();
		String spcodeLimitField = RedisKeyUtils.getSpcodeLimitField(spcode.getId(),userUuid);
		jedis.hincrBy(key, spcodeLimitField, Long.parseLong(spcode.getChargeTimes().toString()));
		jedis.pexpireAt(key, DateUtil.getNextMonthBegin());
		
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
	public static boolean updateUserMonthLimit(UserLimit userLimit,Spcode spcode,String userUuid,Jedis jedis){
		boolean flag = true;
		
		String key = RedisKeyUtils.getMonthLimitKey();
		String userLimitField = RedisKeyUtils.getUserLimitField(userUuid);
		jedis.hincrBy(key, userLimitField, Long.parseLong(spcode.getChargeTimes().toString()));
		jedis.pexpireAt(key, DateUtil.getNextMonthBegin());
		
		return flag;
	}
}
