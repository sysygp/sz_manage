/**   
* @Title: UnicodeUtil.java 
* @Package com.ccpsrj.zhrt.util.encode 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年5月20日 下午4:28:46 
* @version V1.0   
*/
package com.system.util.encode;

/** 
 * @ClassName: UnicodeUtil 
 * @Description: TODO 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年5月20日 下午4:28:46 
 *  
 */
public class UnicodeUtil {

	// 转化字符串为十六进制编码
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	// 转化十六进制编码为字符串
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

}
