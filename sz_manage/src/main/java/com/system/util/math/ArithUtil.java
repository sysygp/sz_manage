package com.system.util.math;

import java.math.BigDecimal;

public class ArithUtil {

	private static final int DEF_DIV_SCALE = 2;
	public static final String CNUMBER_PATTERN = "^[0-9]*$";// 判断数字的正则表达式

	private ArithUtil() {

	}

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������ĺ�
	 */

	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������Ĳ�
	 */

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();

	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * @param v1 ������
	 * @param v2 ����
	 * @return ���������Ļ�
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();

	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
	 * С�����Ժ�10λ���Ժ�������������롣
	 * @param v1 ������
	 * @param v2 ����
	 * @return ������������
	 */

	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
	 * �����ȣ��Ժ�������������롣
	 * @param v1 ������
	 * @param v2 ����
	 * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return ������������
	 */

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * �ṩ��ȷ��С��λ�������봦��
	 * @param v ��Ҫ�������������
	 * @param scale С���������λ
	 * @return ���������Ľ��
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将字符串转换为BigDecimal类型
	 * @param number
	 * @return 
	 */
	public static BigDecimal convertDecimal(String number) {
		return new BigDecimal(number);
	}
	
	/**
	 * 将两数相减结果四舍五入
	 * @param v1
	 * @param v2
	 * @return 
	 */
	public static BigDecimal subtract(BigDecimal v1,BigDecimal v2) {
		BigDecimal result = v1.subtract(v2);
		return result.setScale(DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
	}
}
