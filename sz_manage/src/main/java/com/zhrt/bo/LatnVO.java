package com.zhrt.bo;

/**
 * 
 * @Description:地区vo类，存放地区信息和父地区信息
 *
 * @author 杨功平  852704764@qq.com
 * @version 1.0
 * @since 1.0
 * @date 2015年7月8日 下午5:49:45
 */
public class LatnVO {
	
	private Integer latnId;
	private String latnName;
	private String latnLevel;
	private String latnAreacode;
	private Integer parentLatnId;
	private String parentLatnName;
	private String parentLatnLevel;
	
	
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
	public String getParentLatnName() {
		return parentLatnName;
	}
	public void setParentLatnName(String parentLatnName) {
		this.parentLatnName = parentLatnName;
	}
	public String getParentLatnLevel() {
		return parentLatnLevel;
	}
	public void setParentLatnLevel(String parentLatnLevel) {
		this.parentLatnLevel = parentLatnLevel;
	}

	
}
