package com.zhrt.entity;

/**
 * 
 * 数据文件和产品版本中间表实体
 * @author ：
 * @vision : 1.0.0
 * @createDate : 2015年8月14日 下午6:32:40
 * @email  ：zhu_shz@myepay.cn
 */
public class DataFileAppVer {

	private String dataFileId;
	private String appVerId;
	public String getDataFileId() {
		return dataFileId;
	}
	public void setDataFileId(String dataFileId) {
		this.dataFileId = dataFileId;
	}
	public String getAppVerId() {
		return appVerId;
	}
	public void setAppVerId(String appVerId) {
		this.appVerId = appVerId;
	}
}
