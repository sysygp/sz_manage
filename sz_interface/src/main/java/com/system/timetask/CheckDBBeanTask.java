package com.system.timetask;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckDBBeanTask extends TimerTask {

	static final Logger log = LoggerFactory.getLogger(CheckDBBeanTask.class);

	public void run() {
		try {
			//checkDB();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkDB() {
		try{
			
		}
		catch(Exception e){
			log.error("DBBean 连接数据库异常！");
			e.printStackTrace();
			try{
				
			}
			catch(Exception e1){
				log.error("销毁DBBean数据库连接失败！");
				e.printStackTrace();
			}
			try{
				log.info("重新初始化DBBean数据库连接！");
//				Server_Console.initDB();
			}
			catch(Exception e2){
				log.error("重新初始化DBBean数据库连接失败！");
				e.printStackTrace();
			}
		}
	}
	
}
