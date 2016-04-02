package com.system.util.export; 
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 导出excel工具类
* @ClassName: ExportExcelUtil 
* @Description: TODO 
* @author 杨功平 852704764@qq.com 
* @date 2013年9月3日 上午12:54:08 
*
 */
public class ExportExcelUtil{

	public static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 导出excel,如果调用该方法，则需要在titleList中怎加一个"序号"字段，并放在titleList的第一位置。
	 * @param response  	HttpServletResponse响应
	 * @param titleList	 	列标题的集合
	 * @param contentList	要导出的内容的集合
	 * @param reportName	要生成的报表的名称
	 * @param isNeedAutoGenerateIndex 是否自动生成序号
	 */
	public static void exportExcel(HttpServletResponse response,List<String> titleList,List<List> contentList,String reportName,boolean isNeedAutoGenerateIndex){
		
		try{
			
//			Properties pps=System.getProperties();
//			pps.put("file.encoding","ISO-8859-1");
//			pps.list(System.out);
			
			/*
			 * 一、处理异常
			 */
			if(response == null){
	        	throw new Exception("导出失败，无法获取输出流.....");
	        }
	        if(titleList == null){	        	
	        	titleList = new ArrayList<String>();
	        }
	        if(contentList == null){	        	
	        	contentList = new ArrayList();
	        }
	        reportName = reportName==null?"sheet1":reportName;
//	        reportName += new Date().getTime();
			
			
			/*
			 * 二、设置响应报头
			 */
			response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=gbk");
	        response.setHeader("Content-Disposition" ,"attachment;filename="+new String(reportName.getBytes("UTF-8"),"ISO-8859-1")+".xls");
	        //获取输出流
	        OutputStream os = response.getOutputStream();
	        
	        
	        /*
	         * 三、写入内容
	         */
	        
	        /*
	         * 设置一些常量
	         */
	        //设置起始位置，将来可以维护
	        int initCol = 0,initRow = 0;
	        
	        //设置标题标题 字体格式(字体名称，字体大小，粗体显示，是否斜体),并作为参数传递给cellformat
			WritableFont wFont_title = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD, false);
			WritableCellFormat wCellFormat_title = new WritableCellFormat(wFont_title);
			wCellFormat_title.setAlignment(Alignment.CENTRE);
			wCellFormat_title.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			//设置列标题格式
			WritableFont wFont_colTitle = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.WHITE);
			WritableCellFormat wCellFormat_colTitle = new WritableCellFormat(wFont_colTitle);
			wCellFormat_colTitle.setBackground(jxl.format.Colour.LIGHT_BLUE);
			wCellFormat_colTitle.setAlignment(Alignment.CENTRE);
			wCellFormat_colTitle.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			//设置正文格式
			WritableFont wFont_content = new WritableFont(WritableFont.TIMES, 10, WritableFont.NO_BOLD, false);
			WritableCellFormat wCellFormat_content = new WritableCellFormat(wFont_content);
			wCellFormat_content.setBackground(jxl.format.Colour.LIGHT_GREEN);
			wCellFormat_content.setAlignment(Alignment.CENTRE);
			wCellFormat_content.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
			
			
	        //新建或者打开文件
	        WritableWorkbook wbook = Workbook.createWorkbook(os);
	        // 生成名为“第一页”的工作薄，下表从0开始
			WritableSheet sheet = wbook.createSheet(reportName, 0);
			
			/*
			 * 写入工作表标题,如果isNeedAutoGenerateIndex为true，则需要自动生成序列，加上“序号”字段。
			 */
			if(isNeedAutoGenerateIndex){
				titleList.add(0, "序号");
			}
			int colNum = titleList.size();
			if(colNum>1){
				sheet.mergeCells(initCol, initRow, colNum-1, initRow);
				Label label = new Label(initCol,initRow++,reportName,wCellFormat_title);
				sheet.addCell(label);
			}
			
			//定义一个int类型的数组，用来存放每列数据的最长值。
			int[] maxLengthsVarCol = new int[colNum];
			
			/*
			 * 写入列标题
			 */
			int current_col = initCol;
			for(int i=0;i<colNum;i++,current_col++){
				String title = titleList.get(i);
				//生成一个Lable(列下标，行下表，内容，单元格样式)
				Label label = new Label(current_col,initRow,title,wCellFormat_colTitle);
				//将该label添加到工作薄中。
				sheet.addCell(label);
			}
			initRow++;

			
			/*
			 * 写入内容
			 */
			
			//循环正文内容
			int current_row = initRow;
			for(int rowCount=0,contentSize=contentList.size();rowCount<contentSize;rowCount++,current_row++){
				if(isNeedAutoGenerateIndex){
					jxl.write.Number rowIndexNum = new jxl.write.Number(0,current_row,rowCount+1,wCellFormat_content);  
					sheet.addCell(rowIndexNum);
//					if(rowCount<contentSize-1){
//						jxl.write.Number rowIndexNum = new jxl.write.Number(0,current_row,rowCount+1,wCellFormat_content);  
//						sheet.addCell(rowIndexNum);
//					}else{
//						Label lable = new Label(initCol,current_row,"合计",wCellFormat_content);
//						sheet.addCell(lable);
//					}
					current_col = initCol+1;
				}else{
					current_col = initCol;
				}
				
				//循环获取正文每行数据。
				List content = contentList.get(rowCount);
				for(int i=0;i<content.size();i++,current_col++){
					//获取原有内容
					Object oriContent = content.get(i);
					//对原有内容进行格式判断
					if(null != oriContent){
						if(java.math.BigDecimal.class.equals(oriContent.getClass())){
							//注意number为jxl包下的，非java.lang包下的。
							jxl.write.Number numValue = new jxl.write.Number(current_col,current_row,((java.math.BigDecimal)oriContent).doubleValue(),wCellFormat_content);  
							sheet.addCell(numValue);
						}else if(Double.class.equals(oriContent.getClass())){
							//注意number为jxl包下的，非java.lang包下的。
//							Number aa =  new Number();
							jxl.write.Number numValue = new jxl.write.Number(current_col,current_row,(Double)oriContent,wCellFormat_content);  
							sheet.addCell(numValue);
						}else if(Integer.class.equals(oriContent.getClass())){
							//注意number为jxl包下的，非java.lang包下的。
							jxl.write.Number numValue = new jxl.write.Number(current_col,current_row,(Integer)oriContent,wCellFormat_content);  
							sheet.addCell(numValue);
						}else if(Boolean.class.equals(oriContent.getClass())){
							jxl.write.Boolean booleanValue =new jxl.write.Boolean(current_col,current_row,(Boolean)oriContent,wCellFormat_content);
							sheet.addCell(booleanValue);
						}else if(java.sql.Timestamp.class.equals(oriContent.getClass())){
//							jxl.write.DateTime dateValue = new jxl.write.DateTime(current_col,current_row,(Date)oriContent,wCellFormat_content);
//							sheet.addCell(dateValue);
							Label commonValue = new Label(current_col,current_row,sdf.format(oriContent),wCellFormat_content);
							sheet.addCell(commonValue);
						}else if(java.util.Date.class.equals(oriContent.getClass())){
							Label commonValue = new Label(current_col,current_row,sdf.format(oriContent),wCellFormat_content);
							sheet.addCell(commonValue);
						}else{
							Label commonValue = new Label(current_col,current_row,String.valueOf(oriContent),wCellFormat_content);
							sheet.addCell(commonValue);
						}
						
						/**
						 * 让正文内容自动适应列宽
						 */
						if(rowCount==0){ //比较第一行本列数据与对应的title
							maxLengthsVarCol[i] = oriContent.toString().getBytes().length>titleList.get(current_row).getBytes().length?oriContent.toString().getBytes().length:titleList.get(current_row).getBytes().length;
						}else{ //比较之前的最大值 与该行该列数据
							maxLengthsVarCol[i] = oriContent.toString().getBytes().length>maxLengthsVarCol[i]?oriContent.toString().getBytes().length:maxLengthsVarCol[i];
						}
						sheet.setColumnView(current_col,maxLengthsVarCol[i]+8);
					}else{
						//当改行改列数据为空时，赋上空值""，否则生成的表格在该单元格上没有边框。
						Label commonValue = new Label(current_col,current_row,"",wCellFormat_content);
						sheet.addCell(commonValue);
						
						if(rowCount==0) //如果第一行该列为空，取对应的title长度。否则默默人以上一行该列比较后的结果。
						maxLengthsVarCol[i] = titleList.get(current_row).getBytes().length;
						sheet.setColumnView(current_col,maxLengthsVarCol[i]+8);
					}
				}
			}

			
			//将流写入工作薄并关闭工作薄流
			wbook.write();
			wbook.close();
		
			//清空并关闭输出流
			os.flush();
			os.close();
		
		}catch(Exception e){
			System.err.println("导出出错...");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出报表，该方法默认不自动生成所引序号
	 * @param response  	HttpServletResponse响应
	 * @param titleList	 	列标题的集合
	 * @param contentList	要导出的内容的集合
	 * @param reportName	要生成的报表的名称
	 */
	public static void exportExcel(HttpServletResponse response,List<String> titleList,List<List> contentList,String reportName){
		ExportExcelUtil.exportExcel(response, titleList, contentList, reportName,false);
	}
	
}
