package com.system.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class FileUtil {
	/*
	 * Java文件操作 获取文件扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	// 取得随时生成的文件名
	public static String getNewFileName() {
		int[] a = new int[4];
		for (int j = 0; j < 4; j++) {
			int b = (int) (Math.random() * 9);
			a[j] = b;
		}
		String rand = "";
		for (int j = 0; j < a.length; j++) {
			rand = rand + java.lang.String.valueOf(a[j]);
		}
		Date now = new Date();
		// 设置文件名称
		String filename = java.lang.String.valueOf(now.getTime()) + rand;
		return filename;
	}
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	/** 
	  * 删除某个文件夹下的所有文件夹和文件 
	  * 
	  * @param delpath 
	  *            String 
	  * @throws FileNotFoundException 
	  * @throws IOException 
	  * @return boolean 
	  */  
	public static boolean deletefile(String delpath){  
		try {  
	  
		   File file = new File(delpath);  
		   if(file.exists()){
			// 当且仅当此抽象路径名表示的文件存在且 是一个目录时，返回 true  
			   if (!file.isDirectory()) {  
				   file.delete();  
			   } else if (file.isDirectory()) {  
				   String[] filelist = file.list();  
				   for (int i = 0; i < filelist.length; i++) {  
					   File delfile = new File(delpath + "\\" + filelist[i]);  
					   if (!delfile.isDirectory()) {  
						   delfile.delete();  
						   //System.out.println(delfile.getAbsolutePath() + "删除文件成功");  
					   } else if (delfile.isDirectory()) {  
						   deletefile(delpath + "\\" + filelist[i]);  
					   }  
				   }  
				   //System.out.println(file.getAbsolutePath()+"删除成功");  
				   file.delete();
			  } 
		   }
		} catch (Exception e) {  
		   System.out.println("deletefile() Exception:" + e.getMessage());  
		}  
		  return true;  
	}  
}
