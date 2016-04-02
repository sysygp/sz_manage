package com.system.util.bean;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.system.util.property.PropertiesConfigDynamic;

/**
 * 转换工具类
 * @author wangjx
 *
 */
public class DigestUtil {
	private static Log log = LogFactory.getLog(DigestUtil.class);

	/**
	 * map转化为对应的字符串(用于构造字符串)
	 * @param map
	 * @return
	 */
	public static String mapToString(Map<String, String> map){
		if (map == null || map.isEmpty()) {
			return null;
		}

		Object[] key = map.keySet().toArray();
		Arrays.sort(key);

		StringBuilder res = new StringBuilder(128);
        for (Object aKey : key) {
            res.append(aKey).append("=").append(map.get(aKey)).append("&");
        }

		return res.substring(0, res.length() - 1);
	}
	
	/**
	 * 字符转化为对应map
	 * @param digest
	 * @return
	 */
	public static Map<String, String> stringToMap(String digest){
		Map<String, String> resultMap = new HashMap<String, String>();
		if (digest == null) {
			return null;
		}
		String[] split = digest.split("&");
        for (String aSplit : split) {
            String[] temp = aSplit.split("=");
            if (temp.length == 1) {
                resultMap.put(temp[0], "");
            }
            if (temp.length > 1) {
                resultMap.put(temp[0], temp[1]);
                log.info(temp[0] + "=" + temp[1]);
            }
        }
		return resultMap;
	}
	
	/**
	 * 构造请求字符串
	 * @param map
	 * @param appInitKey
	 * @return
	 */
	public static String createParam(Map<String, String> map, String appInitKey) {
		try {
			String rStr = mapToString(map);
			if (appInitKey == null) {
				return rStr + "&verify=" + getKeyedDigest(rStr, "");
			}

			log.info("verify="+getKeyedDigest(rStr, appInitKey));
			return rStr + "&verify=" + getKeyedDigest(rStr, appInitKey);
		} catch (Exception e) {
			log.error("DigestUtil：createParam method arises the error,parameter="+map.toString());
			e.printStackTrace();
		}

		return null;
	}
	
	public static String getKeyedDigest(Map<String, String> map, String appInitKey){
		String rStr = mapToString(map);
		if (appInitKey != null) {
			return getKeyedDigest(rStr, appInitKey);
		}
		return getKeyedDigest(rStr, "");
	}
	
	/**
	 * MD5加密
	 * @param strSrc
	 * @param key
	 * @return
	 */
	public static String getKeyedDigest(String strSrc, String key) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF-8"));

			//String result = "";  .
            StringBuilder stringBuilder = new StringBuilder();
			byte[] temp;
			temp = md5.digest(key.getBytes("UTF-8"));
            for (byte aTemp : temp) {
                //result += Integer.toHexString((0x000000ff & aTemp) | 0xffffff00).substring(6);
                stringBuilder.append(Integer.toHexString((0x000000ff & aTemp) | 0xffffff00).substring(6));
            }

			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("Method getKeyedDigest  arises the error(NoSuchAlgorithmException),parameter="+strSrc);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Method getKeyedDigest arises the error,parameter="+strSrc);
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 单字符串MD5加密
	 * @param strSrc
	 * @return
	 */
	public static String getKeyMD5(String strSrc){
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF-8"));

			//String result = "";
            StringBuilder stringBuilder = new StringBuilder();
			byte[] temp;
			temp = md5.digest();
            for (byte aTemp : temp) {
               // result += Integer.toHexString((0x000000ff & aTemp) | 0xffffff00).substring(6);
                stringBuilder.append(Integer.toHexString((0x000000ff & aTemp) | 0xffffff00).substring(6));
            }

			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("Method getKeyMD5 arises the error(NoSuchAlgorithmException),parameter="+strSrc);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Method getKeyMD5 arises the error,parameter="+strSrc);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * 验证参数的正确性
     * @param parametersMap : 参数的map
     * @param appInitKey : 密匙
     * @param verify ：传过来的加密后的串
     * @return
     */
    public static boolean validateParameters(Map<String, String> parametersMap, String appInitKey,String verify){
       boolean validateResult = false;
       String paramStr = mapToString(parametersMap);
       log.info("接收原始字符串："+parametersMap);
       log.info("接收方转化后字符串："+paramStr);
       
       String newVerifyStr=DigestUtil.getKeyedDigest(paramStr, appInitKey);
       log.info("接收方处理后字符串："+newVerifyStr+" | "+appInitKey);
       
       if(verify.equals(newVerifyStr)){
           validateResult = true;
       }
       return validateResult;
    }
    
    /**
     * 提交Post请求
     * @param paramMap
     * @param url
     * @param appInitKey
     * @return
     */
    public static String submitPost(Map<String, String> paramMap, String url,
			String appInitKey) {
		String result = null;
		try {
			int statusCode;
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(18000);// 设置连接超时时间
			client.getHttpConnectionManager().getParams().setSoTimeout(18000);// 设置读取数据超时时间
			PostMethod method = new PostMethod(url);
			String strRequst = DigestUtil.createParam(paramMap, appInitKey);
			String encodeRequest = URLEncoder.encode(strRequst, "UTF-8");
			log.info("Encode:" + URLEncoder.encode(strRequst, "UTF-8"));
			method.addParameter("data", encodeRequest);

			statusCode = client.executeMethod(method);
			
			System.out.println("------------statusCode:"+statusCode);
			if (statusCode == HttpStatus.SC_OK) {
				result = method.getResponseBodyAsString();
				log.info("提交成功");
				log.info("返回信息显示"+result);
			} else {
				log.info("提交失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return result;
	}
    
    /**
     * 19pay 发送表单处理方法
     * @param url
     * @param map
     * @return
     */
    public static String doPost(String url, Map<String, String> map) {
        String res = null;
        log.info("HTTP POST url:" + url);
        HttpClient http = new HttpClient();
        PostMethod method = new PostMethod(url);
        Set<String> keys = map.keySet();
        for(String k : keys) {
               method.addParameter(k, map.get(k));
        }
        try {
               int httpStatus = http.executeMethod(method);
               if(httpStatus == HttpStatus.SC_OK) {
                      res = method.getResponseBodyAsString();
                      log.info("POST响应：" + res);
               }
        } catch (HttpException e) {
        	log.info("http异常1", e);
        } catch (IOException e) {
        	log.info("http异常2", e);
        } finally {
               method.releaseConnection();
        }
        return res;
 }
    /**
	 * 调用用户中心数据信息同步处理
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	private static Map dealUserData(Map<String, String> paramMap) throws Exception {
		String retFlag ="-1";
		try {
			//调用用户中心之前做数据
			String lon = paramMap.get("lon");
			String lat = paramMap.get("lat");
		    if(StringUtils.isNotEmpty(lon))
		    {
		      double dlon = Double.valueOf(lon);
		      double ilon = dlon*100000;
		      
			   System.out.println(ilon);
			   int mdata =  (int)Math.floor(ilon);
			   String lonStr = String.valueOf(mdata);
			   paramMap.remove("lon");
			   paramMap.put("lon", lonStr);
		    }
		    if(StringUtils.isNotEmpty(lat))
		    {
		      double dlat = Double.valueOf(lat);
		      double ilat = dlat*100000;
		      
			   System.out.println(ilat);
			   int mdata =  (int)Math.floor(ilat);
			   String latStr = String.valueOf(mdata);
			   paramMap.remove("lat");
			   paramMap.put("lat", latStr);
		    }
			// ------------------------------调用用户中心数据处理结束---------------------------------------//
			return paramMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 校验传递过来的字符串
	 * @param requestMap 参数映射
	 * @return  参数是否校验通过
	 */
	public static boolean checkDigest(Map<String,String> requestMap){
		boolean result=false;
		
		if(requestMap!=null){
			String oldVerify=requestMap.get("verify");
			requestMap.remove("verify");
			//String cid=requestMap.get("cid");
			//计费系统标识
			String verifyKey = PropertiesConfigDynamic.getConfig("verifyKey");
			//String verifyKey = ConfigUtil.getValue("verifyKey");
			//String key="JY102";
			//VerifyUtil.getVerifyKey(cid);
			//String key= VerifyUtil.getVerifyKey(cid);
			if(verifyKey==null){
				log.error("系统根据cid无法找到对应的key");
				return false;
			}
			boolean isEqual= DigestUtil.validateParameters(requestMap,verifyKey,oldVerify);
			if (isEqual) {
				result = true;
			} else {
				log.error("Method checkDigest fail,parameters --->"+requestMap);
			}
		}
		return result;
	}  
  public static void main(String[] args) {
    	//调用户中心接口，保存代理商信息
		Map<String, String> mapRequet = new HashMap<String, String>();
		mapRequet.put("cid", "JY102");     //
		mapRequet.put("cmd", "061");     
		mapRequet.put("version", "1.0");	  
		mapRequet.put("rtype", "json");	   
		mapRequet.put("nickname", "15010342109");     
		mapRequet.put("type", "3");	   
		mapRequet.put("name", "新朋友");	 
		mapRequet.put("sex", "1");	
		mapRequet.put("provinceid", "110000");
		mapRequet.put("cityid", "110100");
		mapRequet.put("districtid", "");
		mapRequet.put("certcode", "110105198609126850");
		mapRequet.put("certaddr", "www");
		mapRequet.put("logintype", "0");
		
		mapRequet.put("lon", "116306821");
		mapRequet.put("lat", "40030496");
		
		mapRequet.put("telephone", "15888");
		mapRequet.put("address", "ddddddddd");
		mapRequet.put("postcode", "2155");
		mapRequet.put("loginname", "18001291855");
		String pwd = getKeyMD5("455565");
		mapRequet.put("loginpwd", "e10adc3949ba59abbe56e057f20f883e");
		String pwdpay = getKeyMD5("2255");
		mapRequet.put("paypwd", "e10adc3949ba59abbe56e057f20f883e");
		
		mapRequet.put("phone", "15811232331");
		mapRequet.put("pid", "ss");
		mapRequet.put("bid", "dd");
		mapRequet.put("tid", "ss");
		mapRequet.put("ip", "192.168.1.222");
		
	
		//mapRequet.put("type", "1");	   	
		mapRequet.put("expand", "1");
		mapRequet.put("companyname", "测试公司");
		mapRequet.put("comprovinceid", "110000");
		mapRequet.put("comcityid", "110100");//110100
		mapRequet.put("comdistrictid", "");
		mapRequet.put("comtype", "1");
		mapRequet.put("rank", "1");
		mapRequet.put("status", "0");
		mapRequet.put("comaddress", "北京市");
		//String re = DigestUtil.submitPost(mapRequet,userCenterUrl, "JY102");
		//mapRequet.put("aid", "15811232314");
		//mapRequet.put("status", "15811232314");
		//http://192.168.63.196:9090/ucgw/service
		//http://192.168.63.137:9000/service
		//http://192.168.63.111:8080/ucgw/service
		String re = DigestUtil.submitPost(mapRequet, "http://localhost:8080/service", "JY102");
		System.out.println(re);
		log.info("返回信息为：" + re);
	}
	 /* public static void main(String[] args) {
    	//调用户中心接口，保存代理商信息
		Map<String, String> mapRequet = new HashMap<String, String>();
		mapRequet.put("cid", "JY102");     //
		mapRequet.put("cmd", "004");     
		mapRequet.put("version", "1.0");	  
		mapRequet.put("rtype", "json");	   
		mapRequet.put("uid", "UA0b06d2013070511362852140");     
		//mapRequet.put("type", "1");	   
		mapRequet.put("name", "测试用户14");	 
		mapRequet.put("sex", "1");	
		mapRequet.put("qq", "15911232388");
		mapRequet.put("email", "test@163.com");
		//mapRequet.put("provinceid", "110000");
		//mapRequet.put("cityid", "110100");
		//mapRequet.put("districtid", "");
		mapRequet.put("certcode", "140227198611100819");
		mapRequet.put("certaddr", "www");
		//mapRequet.put("logintype", "0");
		
		mapRequet.put("telephone", "158887777");
		mapRequet.put("address", "dddddddddyyyyyyyyy");
		mapRequet.put("postcode", "215555");
		mapRequet.put("loginname", "15800000001");
		String pwd = getKeyMD5("455565");
		//mapRequet.put("loginpwd", pwd);
		String pwdpay = getKeyMD5("2255");
		//mapRequet.put("paypwd", pwdpay);
		
		//mapRequet.put("phone", "15811232331");
		//mapRequet.put("pid", "ss");
		//mapRequet.put("bid", "dd");
		//mapRequet.put("tid", "ss");
		mapRequet.put("ip", "192.168.1.222");
		
	
		//mapRequet.put("type", "1");	   	
		
		//String re = DigestUtil.submitPost(mapRequet,userCenterUrl, "JY102");
		//mapRequet.put("aid", "15811232314");
		//mapRequet.put("status", "15811232314");
		//http://192.168.63.196:9090/ucgw/service
		//http://192.168.63.137:9000/service
		//http://192.168.63.111:8080/ucgw/service
		String re = DigestUtil.submitPost(mapRequet, "http://localhost:8080/service", "JY102");
		System.out.println(re);
		log.info("返回信息为：" + re);
	}*/
}
