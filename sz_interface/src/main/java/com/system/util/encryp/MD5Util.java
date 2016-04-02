package com.system.util.encryp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {  
	
    /*** 
     * MD5加码 生成32位md5码 
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    }  
    
    private static String Md5(String plainText) {
    	  String result = null;
    	  try {
    	   MessageDigest md = MessageDigest.getInstance("MD5");
    	   md.update(plainText.getBytes());
    	   byte b[] = md.digest();
    	   int i;
    	   StringBuffer buf = new StringBuffer("");
    	   for (int offset = 0; offset < b.length; offset++) {
    	    i = b[offset];
    	    if (i < 0)
    	     i += 256;
    	    if (i < 16)
    	     buf.append("0");
    	    buf.append(Integer.toHexString(i));
    	   }
    	   // result = buf.toString();  //md5 32bit
    	   // result = buf.toString().substring(8, 24))); //md5 16bit
    	   result = buf.toString();
//    	   System.out.println("mdt 16bit: " + buf.toString().substring(8, 24));
//    	   System.out.println("md5 32bit: " + buf.toString() );
    	  } catch (NoSuchAlgorithmException e) {
    	   e.printStackTrace();
    	  }
    	  return result;
    	}

} 