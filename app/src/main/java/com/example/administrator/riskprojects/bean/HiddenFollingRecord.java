package com.example.administrator.riskprojects.bean;

import java.io.Serializable;

public class HiddenFollingRecord implements Serializable{

	private String follingRecordId;//主键
	private String threeFixId;// threeFix的id
	private String follingPersonId;// 隐患跟踪人ID
	private String follingPersonName;//隐患跟踪人姓名
	private String follingRecord;// 跟踪记录
	private String follingRecordTime;// 记录时间

	public HiddenFollingRecord() {
	}

	public HiddenFollingRecord(String threeFixId, String follingPersonId, String follingPersonName, String follingRecord, String follingRecordTime) {
		this.threeFixId = threeFixId;
		this.follingPersonId = follingPersonId;
		this.follingPersonName = follingPersonName;
		this.follingRecord = follingRecord;
		this.follingRecordTime = follingRecordTime;
	}

	public String getFollingRecordId() {
		return follingRecordId;
	}

	public void setFollingRecordId(String follingRecordId) {
		this.follingRecordId = follingRecordId;
	}

	public String getThreeFixId() {
		return threeFixId;
	}

	public void setThreeFixId(String threeFixId) {
		this.threeFixId = threeFixId;
	}

	public String getFollingPersonId() {
		return follingPersonId;
	}

	public void setFollingPersonId(String follingPersonId) {
		this.follingPersonId = follingPersonId;
	}

	public String getFollingPersonName() {
		return follingPersonName;
	}

	public void setFollingPersonName(String follingPersonName) {
		this.follingPersonName = follingPersonName;
	}

	public String getFollingRecord() {
		return follingRecord;
	}

	public void setFollingRecord(String follingRecord) {
		this.follingRecord = follingRecord;
	}

	public String getFollingRecordTime() {
		return follingRecordTime;
	}

	public void setFollingRecordTime(String follingRecordTime) {
		this.follingRecordTime = follingRecordTime;
	}
}
