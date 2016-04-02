package com.zhrt.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.system.util.stringutil.StringDateConstant;

/**
 * 
 * 简单描述: 通知管理
 *
 * @author 王坤
 * @Create Date 2015年8月27 14点30分30秒
 */
public class NoticeInfo {
	private String id;//
	private String noticeTitle;// 通知标题
	private String noticeType;// 通知类型 1:文本 2:外部连接
	private String noticeTypeName;// 通知类型名称
	
	private String noticeContent;// 通知内容
	private String noticeLinkAddr;// 链接地址
	private String noticeStatus;// 状态 1:上线 2:下线 3删除
	private Date noticeBegin;// 起始时间
	private String noticeBeginStr;// 起始时间
	private Date noticeEnd;// 结束时间
	private String noticeEndStr;// 结束时间
	
	private String cpId;// 所属cp的id
	//private String cpName;// 所属cp的name, 便于页面显示
	
	private String appId;// 所属产品的id
	private String appName;// 所属产品name，便于页面显示
	
	private String appVerId;// 所属产品版本
	private String verNumber;// 所属产品版本，便于页面显示
	
	private String channelId;// 渠道名称id
	private String cnName;// 渠道名称(短名称)，便于页面显示
	
	private String cuser;// 创建人
	private Date ctime;// 创建时间
	private String uuser;// 修改人
	private Date utime;// 修改时间
	private String utimeStr;// 修改时间
	

	public String getNoticeTypeName() {
		return noticeTypeName;
	}
	public void setNoticeTypeName(String noticeTypeName) {
		this.noticeTypeName = noticeTypeName;
	}
	public String getNoticeBeginStr() {
		return noticeBeginStr;
	}
	public String getNoticeEndStr() {
		return noticeEndStr;
	}
	public String getUtimeStr() {
		return utimeStr;
	}
	public void setNoticeBegin(Date noticeBegin) {
		this.noticeBegin = noticeBegin;
	}
	public void setNoticeEnd(Date noticeEnd) {
		this.noticeEnd = noticeEnd;
	}
	
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getVerNumber() {
		return verNumber;
	}
	public void setVerNumber(String verNumber) {
		this.verNumber = verNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getNoticeLinkAddr() {
		return noticeLinkAddr;
	}
	public void setNoticeLinkAddr(String noticeLinkAddr) {
		this.noticeLinkAddr = noticeLinkAddr;
	}
	
	
	public String getCpId() {
		return cpId;
	}
	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppVerId() {
		return appVerId;
	}
	public void setAppVerId(String appVerId) {
		this.appVerId = appVerId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getCuser() {
		return cuser;
	}
	public void setCuser(String cuser) {
		this.cuser = cuser;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getUuser() {
		return uuser;
	}
	public void setUuser(String uuser) {
		this.uuser = uuser;
	}
	
	public Date getNoticeBegin() {
		return noticeBegin;
	}
	
	public void setUtime(Date utime) {
		this.utime = utime;
	}
	public Date getNoticeEnd() {
		return noticeEnd;
	}
	
	public void setNoticeBeginStr(String noticeBeginStr) {
		try {
			if(StringUtils.isNotBlank(noticeBeginStr)){				
				SimpleDateFormat sdf = new SimpleDateFormat(StringDateConstant.FMT_YMDHMS);
				this.noticeBegin = sdf.parse(noticeBeginStr);
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	public void setNoticeEndStr(String noticeEndStr) {
		try {
			if(  StringUtils.isNotBlank(noticeEndStr)){				
				SimpleDateFormat sdf = new SimpleDateFormat(StringDateConstant.FMT_YMDHMS);
				this.noticeEnd = sdf.parse(noticeEndStr);
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}	
	
	public void setUtimeStr(String utimeStr) {
		this.utimeStr = utimeStr;
	}
	public Date getUtime() {
		return utime;
	}
	


}
