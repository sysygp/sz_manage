/**   
* @Title: UrlUtil.java 
* @Package com.zhrt.util 
* @Description: TODO
* @author 杨功平 852704764@qq.com   
* @date 2014年11月19日 下午5:54:51 
* @version V1.0   
*/
package com.system.util.http;


/**
 * @ClassName: UrlUtil 
 * @Description: TODO 
 * @author 杨功平 852704764@qq.com 
 * @date 2014年11月19日 下午5:54:51 
 * */
public class LocalAndNetUrlUtil {
	
	/**
	 * 本地路径
	 */
	//基本路径
	public static final String BASE_FILE_PATH = LocalAndNetUrlUtil.class.getClassLoader().getResource("/").getPath().replace("/WEB-INF/classes/", "/");
	//图片
	public static final String UPLOAD_FILE_PATH = BASE_FILE_PATH+"upload/";
	
	
	/**
	 * 网络路径
	 */
	public static String NET_BASE_FILE_PATH;
	public static String NET_IMAGE_FILE_PATH;

}
