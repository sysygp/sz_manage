/**
 * 
 */
package com.system.util.encode;

import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Decoder;

/**
 * @author veedy
 * 
 */
public class BaseEncoderUtil {

	/**
	 * ���ܣ���BASE64������ַ�src���н���
	 * 
	 * @param src
	 * @return
	 */
	public String getFromBASE64(String src) {
		if (src == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(src);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public String decodeStrP(String src) {

		String sss1 ="";
		String tt1=src.substring(24,src.length());
		try {
			sss1 = java.net.URLDecoder.decode(tt1, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String ss1 = this.getFromBASE64(sss1);
//		String result="";
//		if(ss1!=null && !ss1.equals("")){
//				result=ss1.substring(24,ss1.length());
//		}
		return ss1;
	}
}
