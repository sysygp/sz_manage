package com.zhrt.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;

import com.system.util.file.FileUtil;
import com.system.util.http.LocalAndNetUrlUtil;
import com.system.util.property.PropertiesConfigDynamic;
import com.system.util.system.SystemUtil;

/**
 * 
 * @Description:
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月12日 下午4:37:29
 */
public class ApkTool {
	

    public static String[] makeApk(String appVerFilePath,String channelCode,/*String chanAppVerFilePath,*/
    		String defaultBillFileStr,String configStr,
    		String gameConfigStr,String gameSetStr) throws IOException{  
 
    	String javaJdkPath = PropertiesConfigDynamic.getConfig("java_jdk_path");
//    	String javaJdkPath = System.getenv("JAVA_HOME");//JAVA_JDK路径

    	//根目录下的文件路径
    	String keyStoreFilePath = LocalAndNetUrlUtil.BASE_FILE_PATH+PropertiesConfigDynamic.getConfig("keystore_file_path");;//apk签名文件路径
    	String unionpayBinFilePath = LocalAndNetUrlUtil.BASE_FILE_PATH+PropertiesConfigDynamic.getConfig("unionpay_bin_file_path");;//银联计费APK包中需要的资源bin文件地址
    	//上传目录下的文件路径
    	String apkFileTmpPath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("apkfile_tmp_path");;//apk文件临时存放路径	
    	String apkKeyStoreFile = PropertiesConfigDynamic.getConfig("keystore_file");;//apk签名文件名
    	String keyPassword = PropertiesConfigDynamic.getConfig("keystore_file_password");//签名文件密码
    	String plugconfigFilePath = PropertiesConfigDynamic.getConfig("plugconfig_file_path");;//配置文件名及路径
    	String defaultBillFile = PropertiesConfigDynamic.getConfig("defaultbill_file_path");;//计费方案文件名及路径
    	String channelApplFilePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_channel_appl_filepath");//渠道运营产品存放目录
    	String applFilePath = LocalAndNetUrlUtil.UPLOAD_FILE_PATH+PropertiesConfigDynamic.getConfig("upload_appl_filepath");//应用产品版本文件存放路径
    	
    	String gameconfigFilePath = PropertiesConfigDynamic.getConfig("gameconfig_file_path");;//轻游戏服务平台配置文件名及路径
    	String gameSetFilePath = PropertiesConfigDynamic.getConfig("gameSet_file_path");;//轻游戏服务平台配置文件名及路径
    	
    	String[] returnStrs=new String[2];
    	String returnCode = "0";//默认成功
        BufferedReader br =null;  
        OutputStreamWriter osw =null;  
        Process process = null;  
        String cmdStr = "";
        String runStr ="";//需要运行的命令行
		String OS = SystemUtil.OS;
		
		
		File file = null;
		if(StringUtils.isNotEmpty(OS)&&OS.startsWith("Win")){
			cmdStr = "cmd.exe /c";
            file =new File("C:/Windows");
		}else{
			//linux
			cmdStr = "";
			file =new File("");
		}
		
        String tmpFloderName=FileUtil.getNewFileName();//存放临时生成文件的目录
        String fileName = FileUtil.getNewFileName();//生成的最终文件名
        String channelFloderFile = "";//返回的渠道产品文件目录
        try {
            //1----解压    
//            File file =new File(apkTool);
            /*file
             * 使用apktool工具把产品的APK文件解压到指定的目录下
             * 说明：
             * 1、applFilePath+"/"+apkFilePath：为需要解压的APK文件
             * 2、apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName：为解压文件存放路径
             */
            runStr =cmdStr+" apktool d "+applFilePath+"/"+appVerFilePath+" -o "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName;
            Runtime time = Runtime.getRuntime();
            
            if(StringUtils.isNotEmpty(OS)&&OS.startsWith("Win")){
            	String[] env = new String[]{"path="+javaJdkPath+"/bin"};
            	process = time.exec(runStr,env,file);
    		}else{
    			process = time.exec(runStr);  
    		}
            
            String message = loadStream(process.getInputStream());  
            String errorMeg = loadStream(process.getErrorStream());  
            System.out.println(message);  
            System.out.println("-------");  
            System.out.println(errorMeg);   
            
            
            System.out.println(process.waitFor()+"=========");
            if(process.waitFor()!=0){
            	returnCode = "1";//解压失败
            	System.out.println("解压失败。。。");  
            }
            System.out.println("解包后：returnCode:"+returnCode);
            
            
            if(returnCode.equals("0")){

                //2----内容修改  
            	  
           	 //创建临时存放一级主文件目录
               String tmpFloderPath = apkFileTmpPath+"/"+tmpFloderName;
               File tmpFloderFile = new File(tmpFloderPath);
               if(!tmpFloderFile.exists()){
               	tmpFloderFile.mkdir();
               }
               //创建临时存放解压文件的目录
               String tmpApplFloderPath = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName;
               File tmpApplFloderFile = new File(tmpApplFloderPath);
               if(!tmpApplFloderFile.exists()){
               	tmpApplFloderFile.mkdir();
               }
                //创建需要修改文件的目录
                String tmpAssetsPath = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"/assets";
                File tmpAssetsFile = new File(tmpAssetsPath);
                if(!tmpAssetsFile.exists()){
                	tmpAssetsFile.mkdir();
                }
                
                //创建银联资源BIN文件的目录
                String tmpUnionPayBinPath = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"/res/drawable";
                File tmpUnionPayBinFile = new File(tmpUnionPayBinPath);
                if(!tmpUnionPayBinFile.exists()){
                	tmpUnionPayBinFile.mkdir();
                }
                //复制银联
                String[] comands = null;
                if(StringUtils.isNotEmpty(OS)&&OS.startsWith("Win")){
                    File srcFile = new File(unionpayBinFilePath);
                    File destFile = new File(apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"/res/drawable");
                    runStr ="copy "+srcFile.getAbsolutePath()+"\\*"+" "+destFile.getAbsolutePath();
                    
                    comands = new String[] {"cmd.exe","/c",runStr };
        		}else{
        			//linux
        			runStr = "chmod 777 "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"/res/drawable";
                    System.out.println("++++++++++赋于权限语句++++++++++++ "+runStr);
                    process = Runtime.getRuntime().exec(runStr);
                    if(process.waitFor()!=0){
                    	System.out.println("执行权限命令返回代码为： "+process.waitFor());
                    	System.out.println("执行权限命令失败。。。");  
                    }else{
                    	System.out.println("执行权限命令成功~~~~~~~~~~~~~~");  
                    }
                    runStr ="cp -a "+unionpayBinFilePath+"/* "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"/res/drawable";
                    comands = new String[] { "/bin/sh", "-c", runStr };
        		}
                System.out.println("++++++++++复制银联资源BIN文件语句++++++++++++ "+runStr);
                process = Runtime.getRuntime().exec(comands);
                if(process.waitFor()!=0){
                	System.out.println("返回代码为： "+process.waitFor());
                	System.out.println("复制银联资源BIN文件失败。。。");  
                	returnCode = "1";
                }else{
                	System.out.println("复制银联资源BIN文件成功~~~~~~~~~~~~~~");  
                }
                
                
                
                if(returnCode.equals("0")){
                	
                	//需要添加的默认计费文件名和路径
                    String defaultBillFilePath = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+defaultBillFile;
                    File defaultFile = new File(defaultBillFilePath);
            		if(!defaultFile.exists()){
            			defaultFile.createNewFile();
            	        BufferedReader defaultBr =new BufferedReader(new InputStreamReader(new FileInputStream(defaultBillFilePath)));
        	            if(defaultBr.readLine()==null){
        	            	OutputStream defaultOsw =new FileOutputStream(defaultBillFilePath);
        	            	defaultOsw.write(defaultBillFileStr.getBytes("UTF-8"));
        	            	defaultOsw.flush(); 
        	            	defaultOsw.close();     
        	            	defaultBr.close();
        	            }
            		}else{
            	        BufferedReader defaultBr =new BufferedReader(new InputStreamReader(new FileInputStream(defaultBillFilePath)));
    	            	OutputStream defaultOsw =new FileOutputStream(defaultBillFilePath);
    	            	defaultOsw.write(defaultBillFileStr.getBytes("UTF-8"));
    	            	defaultOsw.flush(); 
    	            	defaultOsw.close();     
    	            	defaultBr.close();
            		}
            		
            		
            		
            		//myepay配置文件文件名路径
            		String targetPath = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+plugconfigFilePath;
            		File configFile = new File(targetPath);
            		if(!configFile.exists()){
            			configFile.createNewFile();
            		}
            		//myepay配置文件写入内容
            		osw = new OutputStreamWriter(new FileOutputStream(targetPath));
            		osw.write(configStr,0,configStr.length());    
            		osw.flush();
            		osw.close();
            		
            		
            		
            		
            		//轻游戏服务平台配置文件文件名路径
            		String gameconfigFileName = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+gameconfigFilePath;
            		File gameconfigFile = new File(gameconfigFileName);
            		if(!gameconfigFile.exists()){
            			gameconfigFile.createNewFile();
            		}
            		//轻游戏服务平台配置文件写入内容
            		osw = new OutputStreamWriter(new FileOutputStream(gameconfigFileName));
            		osw.write(gameConfigStr,0,gameConfigStr.length());    
            		osw.flush();
            		osw.close();
            		
            		
                   //轻游戏服务平台动态设置文件文件名路径
                   String gameSetFileName = apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+gameSetFilePath;
                   File gameSetFile = new File(gameSetFileName);
                   if(!gameSetFile.exists()){
                	   gameSetFile.createNewFile();
            		}
                   //轻游戏服务平台配置文件写入内容
                	osw = new OutputStreamWriter(new FileOutputStream(gameSetFileName));
                    osw.write(gameSetStr,0,gameSetStr.length());    
                    osw.flush();
                    osw.close();
                
                    

                    if(returnCode.equals("0")){
    	                //3----打包  
    	                /*
    	                 * 使用apktool工具把指定的文件目录打包成指定的APK文件
    	                 * 说明：1、apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName：需要打包的文件目录
    	                 * 2、apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"_tmp.apk：生成的apk文件名和目录
    	                 */
                    	runStr =cmdStr+" apktool b "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+" -o "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"_tmp.apk";
                    	System.out.println("打包 ++++++++++++++++++++++ "+runStr);

                    	 if(StringUtils.isNotEmpty(OS)&&OS.startsWith("Win")){
                         	String[] env = new String[]{"path="+javaJdkPath+"/bin"};
                         	process = time.exec(runStr,env,file);
                 		}else{
                 			process = time.exec(runStr);  
                 		}
    	                if(process.waitFor()!=0){
    	                	returnCode = "2";//打包失败
    	                	System.out.println("打包失败。。。");  
    	                }
    	                
    	                
    	                if(returnCode.equals("0")){
        	                //4----签名(文件名称中不能包含空格)  
        	                //签名打包的新文件存放路径即渠道运营的产品存放地址
        	                String channelApplPath = channelApplFilePath+"/"+channelCode;
        	                channelFloderFile = channelCode+"/"+fileName+".apk";
        	                File channelAppl = new File(channelApplPath);
        	                if(!channelAppl.exists()){
        	                	channelAppl.mkdirs();
        	                }
        	                File jdkBin =new File(javaJdkPath);
        	                /*
        	                 * 使用JAVA自带的jarsigner命令对生成的APK文件进行签名重新打包并生成最终渠道运营的产品
        	                 * 说明：1、keyStoreFilePath+apkKeyStoreFile：签名文件地址和文件名
        	                 * 2、keyPassword：签名文件密码
        	                 * 3、"+channelApplPath+"/"+fileName+".apk：生成的新APK文件(渠道运营产品)
        	                 * 4、apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"_tmp.apk：需要打包签名的APK文件。
        	                 * 
        	                 * jdk1.7签名需要加上“-digestalg SHA1 -sigalg MD5withRSA ”参数
        	                 */	            
//        	                if(StringUtils.isNotEmpty(signFileUrl)){
//        	                	String tmpSingFile = signFileUrl.split("/")[1];
//        		                runStr ="jarsigner -digestalg SHA1 -sigalg MD5withRSA -keystore "+appSignFilePath+"/"+signFileUrl+" -storepass "+signPwd+" -signedjar /"+channelApplPath+"/"+fileName+".apk "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"_tmp.apk"+" "+tmpSingFile;
//        	                }else{
        	                	runStr ="jarsigner -digestalg SHA1 -sigalg MD5withRSA -keystore "+keyStoreFilePath+apkKeyStoreFile+" -storepass "+keyPassword+" -signedjar /"+channelApplPath+"/"+fileName+".apk "+apkFileTmpPath+"/"+tmpFloderName+"/"+tmpFloderName+"_tmp.apk"+" "+apkKeyStoreFile;
//        	                }  
        	                System.out.println("签名 ++++++++++++++++++++++ "+runStr);
        	                process = Runtime.getRuntime().exec(runStr,null,jdkBin);  
//        	                process = Runtime.getRuntime().exec("jarsigner -keystore E:/Work/Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/myepay_manage_dev_MP-I2.0.0.0/keystore/zhrt_pay.keystore -storepass E:/Work/Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/myepay_manage_dev_MP-I2.0.0.0/upload/zhrt_sdk -signedjar E:/Work/Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/myepay_manage_dev_MP-I2.0.0.0/upload/channel/00000137/14164168561602546.apk E:/Work/Workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp2/wtpwebapps/myepay_manage_dev_MP-I2.0.0.0/upload/temp/14164168561603558/14164168561603558_tmp.apk zhrt_pay.keystore",null,bin);  
        	                if(process.waitFor()!=0){
        	                	returnCode = "3";//签名失败
        	                	System.out.println("签名失败。。。");  
        	                }
                        }
    	                
    	                
                    }
                    
                	
                	
                }
                
                
                
            }
        }catch (Exception e)  
        {  
            e.printStackTrace(); 
            //创建临时存放文件目录
            String tmpFloderPath = apkFileTmpPath+"/"+tmpFloderName;
            File tmpFloderFile = new File(tmpFloderPath);
            if(tmpFloderFile.exists()){
            	//删除临时文件
            	FileUtil.deletefile(tmpFloderPath);
    		} 
        }finally{  
        	if(br!=null){
        		br.close();  
        	}
        	if(osw!=null){
                osw.close(); 
        	} 
        	//创建临时存放文件目录
        	String tmpFloderPath = apkFileTmpPath+"/"+tmpFloderName;
        	File tmpFloderFile = new File(tmpFloderPath);
        	if(tmpFloderFile.exists()){
        		//删除临时文件
        		System.out.println("开始删除临时文件夹");
        		FileUtil.deletefile(tmpFloderPath);
        		System.out.println("删除成功========");
        		//fileT.deletefile(tmpFloderPath);
        	}
        	
        	
           /* //删除原有渠道产品版本
            String chanAppVerOldPath = channelApplFilePath+"/"+chanAppVerFilePath;
            FileUtil.deletefile(chanAppVerOldPath);*/
        }  
        returnStrs[0]=returnCode;
        returnStrs[1]=channelFloderFile;
        return returnStrs;
    }      
    
    private static String loadStream(InputStream in) throws IOException {  
        int ptr = 0;  
        in = new BufferedInputStream(in);  
        StringBuffer buffer = new StringBuffer();  
        while ((ptr = in.read()) != -1) {  
            buffer.append((char) ptr);  
        }  
        return new String(buffer.toString().getBytes("ISO-8859-1"), "GBK");  
    }  
    
    
    
}
