package com.system.util.system;

public class SystemUtil {
	public final static String OS = java.lang.System.getProperty("os.name");

	// 换行符
	public static String LINE_SEPARATOR = java.lang.System.getProperty("line.separator");

	// 文件路径分割符
	public static String FILE_SEPARATOR = java.lang.System.getProperty("file.separator");
}
