package com.system.util.system;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * @Description:随机数工具类
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年6月30日 下午4:32:32
 */
public class RandomUtil {

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
			"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	
	public static String[] en_chars = new String[]{ "a", "b", "c", "d", "e", "f","g", "h", "i", "j", "k", "l", "m", "n", 
					"o", "p", "q", "r", "s","t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", 
					"I","J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
	public static String[] num_chars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

	/**
	 * 32位不重复随机数
	 * @return
	 */
	public static String genUuid32() {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid;
	}
	
	/**
	 * 8位不重复随机数
	 * @return
	 */
	public static String genUuid8() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, (i+1) * 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}
	
	/**
	 * 6位随机数(由5位数字+1位字母)
	 * @return
	 */
	public static String geneRandomStr6(){
		StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < 5; i++) 
        { 
            int rand = (int) (Math.random() * num_chars.length); 
            buffer.append(num_chars[rand]);
        } 
      
        for(int j=0; j < 1; j++){
        	int rand = (int) (Math.random() * en_chars.length); 
        	buffer.append(en_chars[rand]);
        }
        
		return buffer.toString();
	}
	
	
	/**
	 * n位随机数，可能重复
	 * @param n
	 * @return
	 */
	public static String random(int n) {
        if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }
	
	
	
	
	public static void main(String[] args) {
		
//		String key32 = RandomUtil.genUuid32();
//		String key8 = RandomUtil.genUuid8();
//		String key4 = RandomUtil.genUuid4();
//		System.out.println(key4);
		
		System.out.println(geneRandomStr6());
		
		
	}
	
	public static void test(){
		//测试重复概率
		Map oldMap = new HashMap();
		int testNum = 20000000;
		for(int i = 0 ; i < testNum ; i++){
			String key = genUuid8();
			oldMap.put(key,"");
		}
		System.out.println(oldMap.size()==testNum?"没有重复":"有重复");
	}

}
