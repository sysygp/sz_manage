/**   
* @Title: Base64.java 
* @Package com.ccpsrj.zhrt.util.encode 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年5月20日 下午4:32:55 
* @version V1.0   
*/
package com.system.util.encode;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * @ClassName: Base64 
 * @Description: TODO 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年5月20日 下午4:32:55 
 *  
 */
public class BASE64     
{     
    
    /**    
     * BASE64解密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static byte[] decryptBASE64(String key) throws Exception {               
        return (new BASE64Decoder()).decodeBuffer(key);               
    }               
                  
    /**         
     * BASE64加密   
   * @param key          
     * @return          
     * @throws Exception          
     */              
    public static String encryptBASE64(byte[] key) throws Exception {               
        return (new BASE64Encoder()).encodeBuffer(key);               
    }       
    
    public static void main(String[] args) throws Exception     
    {     
        String data = BASE64.encryptBASE64("2".getBytes());     
        System.out.println("加密前："+data);     
             
        byte[] byteArray = BASE64.decryptBASE64(data);     
        System.out.println("解密后："+new String(byteArray));     
    }     
}    

