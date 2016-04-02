package com.zhrt.entity;

/** 
 * 手机归属地
 */
public class Latn {
	private Integer latnId;
	private String latnName;
	private String latnLevel;
	private String latnAreacode;
	private Integer parentLatnId;
	private String remark;
	
	public Integer getLatnId() {
		return latnId;
	}
	public void setLatnId(Integer latnId) {
		this.latnId = latnId;
	}
	public String getLatnName() {
		return latnName;
	}
	public void setLatnName(String latnName) {
		this.latnName = latnName;
	}
	public String getLatnLevel() {
		return latnLevel;
	}
	public void setLatnLevel(String latnLevel) {
		this.latnLevel = latnLevel;
	}
	public String getLatnAreacode() {
		return latnAreacode;
	}
	public void setLatnAreacode(String latnAreacode) {
		this.latnAreacode = latnAreacode;
	}
	public Integer getParentLatnId() {
		return parentLatnId;
	}
	public void setParentLatnId(Integer parentLatnId) {
		this.parentLatnId = parentLatnId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
