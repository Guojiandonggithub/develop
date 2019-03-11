package com.example.administrator.riskprojects.bean;

import java.io.Serializable;

/**
 * 跟踪记录
 */
public class IdentificationEvaluationRecord implements Serializable{
	private String addTime;//跟踪时间
	private String conCollieryId;
	private String consider;//跟踪内容
	private String controlId;
	private String deptId;
	private String deptName;//责任单位
	private String dutyPerId;
	private String dutyPerName;//责任人
	private String management;//管理流程
	private String safeCheckId;

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getConCollieryId() {
		return conCollieryId;
	}

	public void setConCollieryId(String conCollieryId) {
		this.conCollieryId = conCollieryId;
	}

	public String getConsider() {
		return consider;
	}

	public void setConsider(String consider) {
		this.consider = consider;
	}

	public String getControlId() {
		return controlId;
	}

	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDutyPerId() {
		return dutyPerId;
	}

	public void setDutyPerId(String dutyPerId) {
		this.dutyPerId = dutyPerId;
	}

	public String getDutyPerName() {
		return dutyPerName;
	}

	public void setDutyPerName(String dutyPerName) {
		this.dutyPerName = dutyPerName;
	}

	public String getManagement() {
		return management;
	}

	public void setManagement(String management) {
		this.management = management;
	}

	public String getSafeCheckId() {
		return safeCheckId;
	}

	public void setSafeCheckId(String safeCheckId) {
		this.safeCheckId = safeCheckId;
	}
}
