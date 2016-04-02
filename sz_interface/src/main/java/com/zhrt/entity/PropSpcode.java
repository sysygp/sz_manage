package com.zhrt.entity;

import java.util.Date;

/**
 * 
 * @Description:道具信息实体类
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月28日 上午10:54:02
 */
public class PropSpcode {

	private String id;
	private String propId;
	private String spcodeId;
	private Integer spcodeSeq;
	private String createPerson;
	private Date createTime;
	private String lastUpdatePerson;
	private Date lastUpdateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPropId() {
		return propId;
	}
	public void setPropId(String propId) {
		this.propId = propId;
	}
	public String getSpcodeId() {
		return spcodeId;
	}
	public void setSpcodeId(String spcodeId) {
		this.spcodeId = spcodeId;
	}
	public Integer getSpcodeSeq() {
		return spcodeSeq;
	}
	public void setSpcodeSeq(Integer spcodeSeq) {
		this.spcodeSeq = spcodeSeq;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdatePerson() {
		return lastUpdatePerson;
	}
	public void setLastUpdatePerson(String lastUpdatePerson) {
		this.lastUpdatePerson = lastUpdatePerson;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
