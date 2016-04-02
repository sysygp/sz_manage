package com.system.util.stringutil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.system.util.math.ArithUtil;
public class StrUtils {

	/**
	 * 判断字符串是否包含某数组内
	 * @param substring:要判断的字符串
	 * @param source：数组
	 * @return
	 */
	public static boolean isIn(String substring, String[] source) {
		boolean flag=false;
		if (source == null || source.length == 0) {
			flag = false;
		}
		for (int i = 0; i < source.length; i++) {
			String aSource = source[i];
			if (aSource.equals(substring)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * 去除List中重复的元素
	 */
	public static List<String> removeDuplicate(List<String> list) {
	    System.out.println("去重之前"+list);
	    for ( int i = 0 ; i < list.size() - 1 ; i ++ ) {
	        for ( int j = list.size() - 1 ; j > i; j -- ) {
	          if (list.get(j).equals(list.get(i))) {
	            list.remove(j);
	          }
	         }
	       }
	    System.out.println("去重之后"+list);
	    return list;
	}
	/**
	 * 是否有中文字符
	 * 
	 * @param s
	 * @return
	 */
	public static boolean hasCn(String s) {
		if (s == null) {
			return false;
		}
		return countCn(s) > s.length();
	}

	/**
	 * 获得字符。符合中文习惯。
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String getCn(String s, int len) {
		if (s == null) {
			return s;
		}
		int sl = s.length();
		if (sl <= len) {
			return s;
		}
		// 留出一个位置用于…
		len -= 1;
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		while (count < maxCount && i < sl) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
			i++;
		}
		if (count > maxCount) {
			i--;
		}
		return s.substring(0, i) + "…";
	}

	/**
	 * 计算GBK编码的字符串的字节数
	 * 
	 * @param s
	 * @return
	 */
	public static int countCn(String s) {
		if (s == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		return count;
	}

	/**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder bld = new StringBuilder();
		char c;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			switch (c) {
			case '&':
				bld.append("&amp;");
				break;
			case '<':
				bld.append("&lt;");
				break;
			case '>':
				bld.append("&gt;");
				break;
			case '"':
				bld.append("&quot;");
				break;
			case ' ':
				bld.append("&nbsp;");
				break;
			case '\n':
				bld.append("<br/>");
				break;
			default:
				bld.append(c);
				break;
			}
		}
		return bld.toString();
	}

	/**
	 * html转文本
	 * 
	 * @param htm
	 * @return
	 */
	public static String htm2txt(String htm) {
		if (htm == null) {
			return htm;
		}
		htm = htm.replace("&amp;", "&");
		htm = htm.replace("&lt;", "<");
		htm = htm.replace("&gt;", ">");
		htm = htm.replace("&quot;", "\"");
		htm = htm.replace("&nbsp;", " ");
		htm = htm.replace("<br/>", "\n");
		return htm;
	}

	public static final String escapeXmlTags(String content) {
		if (content != null) {
			content = content.replace("&", "&amp;");
			content = content.replace("\"", "&quot;");
			content = content.replace("<", "&lt;");
			content = content.replace(">", "&gt;");
			return content;
		} else
			return content;
	}

	public static final String unescapeXmlTags(String content) {
		if (content != null) {
			content = content.replace("&quot;", "\"");
			content = content.replace("&lt;", "<");
			content = content.replace("&gt;", ">");
			content = content.replace("&amp;", "&");
			return content;
		} else
			return content;
	}

	/**
	 * 全角-->半角
	 * 
	 * @param qjStr
	 * @return
	 */
	public String Q2B(String qjStr) {
		String outStr = "";
		String Tstr = "";
		byte[] b = null;
		for (int i = 0; i < qjStr.length(); i++) {
			try {
				Tstr = qjStr.substring(i, i + 1);
				b = Tstr.getBytes("unicode");
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if (b[3] == -1) {
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				try {
					outStr = outStr + new String(b, "unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else
				outStr = outStr + Tstr;
		}
		return outStr;
	}

	public static final char[] N62_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	public static final char[] N36_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private static StringBuilder longToNBuf(long l, char[] chars) {
		int upgrade = chars.length;
		StringBuilder result = new StringBuilder();
		int last;
		while (l > 0) {
			last = (int) (l % upgrade);
			result.append(chars[last]);
			l /= upgrade;
		}
		return result;
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @return
	 */
	public static String longToN62(long l) {
		return longToNBuf(l, N62_CHARS).reverse().toString();
	}

	public static String longToN36(long l) {
		return longToNBuf(l, N36_CHARS).reverse().toString();
	}

	/**
	 * 长整数转换成N62
	 * 
	 * @param l
	 * @param length
	 *            如N62不足length长度，则补足0。
	 * @return
	 */
	public static String longToN62(long l, int length) {
		StringBuilder sb = longToNBuf(l, N62_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	public static String longToN36(long l, int length) {
		StringBuilder sb = longToNBuf(l, N36_CHARS);
		for (int i = sb.length(); i < length; i++) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	/**
	 * N62转换成整数
	 * 
	 * @param n62
	 * @return
	 */
	public static long n62ToLong(String n62) {
		return nToLong(n62, N62_CHARS);
	}

	public static long n36ToLong(String n36) {
		return nToLong(n36, N36_CHARS);
	}

	private static long nToLong(String s, char[] chars) {
		char[] nc = s.toCharArray();
		long result = 0;
		long pow = 1;
		for (int i = nc.length - 1; i >= 0; i--, pow *= chars.length) {
			int n = findNIndex(nc[i], chars);
			result += n * pow;
		}
		return result;
	}

	private static int findNIndex(char c, char[] chars) {
		for (int i = 0; i < chars.length; i++) {
			if (c == chars[i]) {
				return i;
			}
		}
		throw new RuntimeException("N62(N36)非法字符：" + c);
	}

	public static String urlDecoderUtf8(String value) {
		try {
			return java.net.URLDecoder.decode(value, "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个二维数组转换成MAP
	 */
	public static Map toMap(String[][] StrArray) {
		HashMap map = new HashMap();
		if (StrArray != null && StrArray.length > 0) {
			for (int i = 0; i < StrArray.length; i++) {
				map.put(StrArray[i][0], StrArray[i][1]);
			}
		}
		return map;
	}

	/**
	 * 将一个二维数组转换成List
	 */
	public static List toList(String[][] StrArray) {
		ArrayList list = new ArrayList();
		if (StrArray != null && StrArray.length > 0) {
			for (int i = 0; i < StrArray.length; i++) {
				LabelValueBean bean = new LabelValueBean(StrArray[i][0], StrArray[i][1]);
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * 调用发送接口时统一对数据进行处理 如果参数为null或"null"，则返回""，否则返回原串
	 * 
	 * @param value
	 * @return
	 */
	public static final String getValue(String value) {
		if (value == null || value.equalsIgnoreCase("null")) {
			return "";
		}
		return value.trim();
	}

	/**
	 * 格式化金额分－>元
	 */
	public static final String fenToYuan(String fenStr) {
		String yuanStr = "0.00";
		DecimalFormat fmt = new DecimalFormat("#0.00");

		double d;
		try {
			double numd = ArithUtil.round(Double.parseDouble(fenStr) / 100, 2);// 四舍五入，保留两位小数
			yuanStr = fmt.format(numd);
		} catch (Exception e) {
		}
		return yuanStr;
	}

	/**
	 * 取得字串中方括号里的内容
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String getStringByBracket(String oriStr) {
		String resultStr = "";
		try {
			Pattern pattern = Pattern.compile("\\[(.+?)\\]"); // 取得方括号中的代码
			Matcher matcher;
			if (StringUtils.isNotEmpty(oriStr)) {
				matcher = pattern.matcher(oriStr);
				while (matcher.find()) {
					resultStr = matcher.group(1);
				}
			}
		} catch (Exception e) {
		}
		return resultStr;
	}

	/**
	 * 判断字串是否为数字
	 * 
	 * @param oriStr
	 * @return
	 */
	public static boolean isNum(String oriStr) {
		boolean resultBoolean = false;
		try {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(oriStr);
			if (isNum.matches()) {
				resultBoolean = true;
			}
		} catch (Exception e) {
		}
		return resultBoolean;
	}

	/**
	 * 转换bool字符串到数值字符串
	 */
	public static String convertBoolToInt(String checkboxStr) {
		String intValueStr = "";
		if (StringUtils.isNotEmpty(checkboxStr) && (checkboxStr.equals("true") || checkboxStr.equals("0")))
			intValueStr = "0";
		else
			intValueStr = "1";

		return intValueStr;
	}

	/**
	 * 转换数值字符串到bool字符串
	 */
	public static String convertIntToBool(String intValueStr) {
		String checkboxStr = "";
		if (StringUtils.isNotEmpty(intValueStr) && (intValueStr.equals("0") || intValueStr.equals("true")))
			checkboxStr = "true";
		else
			checkboxStr = "false";

		return checkboxStr;
	}

	/**
	 * 转换数值字符串到bool
	 */
	public static boolean convertStringToBool(String intValueStr) {
		boolean checkboxStr = false;
		if (intValueStr != null)
			intValueStr = intValueStr.trim();
		if (StringUtils.isNotEmpty(intValueStr) && (intValueStr.equals("0") || intValueStr.equals("true")))
			checkboxStr = true;
		else
			checkboxStr = false;

		return checkboxStr;
	}

	/**
	 * 获取文件扩展名
	 */
	public static String trimExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length()))) {
				return filename.substring(i + 1, filename.length());
			}
		}
		return filename;
	}

	/**
	 * 是否包含
	 */
	public static boolean isContain(String str, String a) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile(".*" + a + ".*");
		Matcher matcher = pattern.matcher(str);
		// 当条件满足时，将返回true，否则返回false
		return matcher.matches();
	}

	/**
	 * 替换字符
	 */
	public static String replaceAll(String str, String regex, String replacement) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll(replacement);
	}

	public static String trim(String str) {
		if (str != null) {
			str = str.trim();
		}
		return str;
	}

	/**
	 * 字串不足digit位时前面补0
	 * 
	 * @param oriStr
	 *            源字串 例如35
	 * @param digit
	 *            多少位 例如 6
	 * @return 补0后的字串
	 */
	public static final String addZero(String oriStr, int digit) {
		try {
			if (StringUtils.isEmpty(oriStr) || oriStr.length() >= digit)
				return oriStr;
			String okStr = "";
			for (int i = 0; i < digit - oriStr.length(); i++) {
				okStr += "0";
			}
			okStr += oriStr;
			return okStr;
		} catch (Exception ex) {
			return oriStr;
		}
	}

	/**
	 * 去除字符串前的"0"
	 * 
	 * @param oriStr
	 * @return
	 */
	public static final String dropZero(String oriStr) {
		try {
			oriStr = getResp(oriStr);
			String tempStr = "";
			int i = 0;
			for (i = 0; i < oriStr.length(); i++) {
				tempStr = oriStr.substring(i, i + 1);
				if (!tempStr.equals("0")) {
					break;
				}
			}
			if (i == oriStr.length())
				oriStr = "0";
			if (i > 1 && i < oriStr.length()) {
				oriStr = oriStr.substring(i);
			}

			return oriStr;
		} catch (Exception ex) {
			return oriStr;
		}
	}

	/**
	 * 调用返回接口时统一对数据进行处理 去除返回数据中带有的空格
	 * 
	 * @param resp
	 * @return
	 */
	public static final String getResp(String resp) {
		if (resp != null) {
			return resp.trim();
		}
		return resp;
	}

	private static Random randGen = new Random();
	private static char numbers[] = "0123456789".toCharArray();

	public static final String randomNumber(int length) {
		if (length < 1)
			return null;
		char randBuffer[] = new char[length];
		for (int i = 0; i < randBuffer.length; i++)
			randBuffer[i] = numbers[randGen.nextInt(10)];

		return new String(randBuffer);
	}

	public static String convertArrayToString(String[] oriArray, String s) {
		StringBuffer rs = new StringBuffer();
		if (oriArray != null && oriArray.length > 0 && s != null && !s.equals("")) {
			for (int i = 0; i < oriArray.length; i++) {
				rs.append(oriArray[i] + ",");
			}
		}
		if (rs != null && !rs.equals("")) {
			rs = rs.delete(rs.length() - 1, rs.length());
		}
		return rs.toString();
	}

	   private static String byte2hex(byte b[]) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			hs = hs + stmp;
		}

		return hs;
	}

	public static String getSHA1(String opwd) {
		byte[] bt;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(opwd.getBytes());
			MessageDigest md1 = (MessageDigest) md.clone();
			bt = md1.digest();
			return byte2hex(bt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public static byte[] getFromBASE64(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);

				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}	
	
	public static String getSecretIdStr(String oriStr) {
		return "";
	}		
	
	public static String getBaseUrlStr(String oriStr) {
		String returnStr = "";
		try {
			if (StringUtils.isNotEmpty(oriStr)) {
				byte[] b = oriStr.getBytes();
				BASE64Encoder encoder = new BASE64Encoder();
				String basedStr = encoder.encode(b);
				returnStr = java.net.URLEncoder.encode(basedStr, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		return returnStr;
	}
	/**
	 * 根据指定格式返回指定时间
	 * @param dateTime:指定的时间
	 * @param dateFormat:指定的格式 "yyyy-MM-dd";"yyyyMMdd"等
	 * @return
	 */
	public static String getDateTimeByFormat(Date dateTime,String dateFormat){
		try{
	        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			Calendar cal = Calendar.getInstance();
	        cal.setTime(dateTime);
	        return format.format(cal.getTime());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @return 前一天的日期，格式为 "yyyy-MM-dd"
	 */
	public static String getYesterday(){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE,-1);
			return format.format(cal.getTime());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @return 前一个月的日期，格式为 "yyyy-MM"
	 */
	public static String getLastMonth(){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MONTH,-1);
			return format.format(cal.getTime());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		// double value = 4.015d;
		// System.out.println(Math.round(value * 100) / 100.0);
		
		// double
		// billingChargeNew=billingCharge*(1d-(hisunAdjustFeeRatio/100d))*((1d-(topChannelIncomeRatioAvg/100d)-(serviceRatioAvg/100d))*(1d-(channelAdjustFeeRatio/100d))/(contractIncomeRatioMc/100d));
		
		double billingChargeNew=768000*(1d-(0/100d))*((1d-(34/100d)-(26/100d))*(1d-(0/100d))/(40/100d));
		System.out.println(billingChargeNew);
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("2");
		list.add("3");
		list.add("4");
		removeDuplicate(list);
	}
}
