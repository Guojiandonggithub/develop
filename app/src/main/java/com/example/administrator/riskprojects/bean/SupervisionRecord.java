package com.example.administrator.riskprojects.bean;

public class SupervisionRecord {
	private String hiddenDangerId;//隐患记录Id
	private String supervisionRecordDeptName;// 督办人
	private String supervisionRecordPersonName;// 督办人部门
	private String supervisionRecord;// 督办内容记录
	private String supervisionTime;// 督办记录时间

	public String getHiddenDangerId() {
		return hiddenDangerId;
	}

	public void setHiddenDangerId(String hiddenDangerId) {
		this.hiddenDangerId = hiddenDangerId;
	}

	public String getSupervisionRecordDeptName() {
		return supervisionRecordDeptName;
	}

	public void setSupervisionRecordDeptName(String supervisionRecordDeptName) {
		this.supervisionRecordDeptName = supervisionRecordDeptName;
	}

	public String getSupervisionRecordPersonName() {
		return supervisionRecordPersonName;
	}

	public void setSupervisionRecordPersonName(String supervisionRecordPersonName) {
		this.supervisionRecordPersonName = supervisionRecordPersonName;
	}

	public String getSupervisionRecord() {
		return supervisionRecord;
	}

	public void setSupervisionRecord(String supervisionRecord) {
		this.supervisionRecord = supervisionRecord;
	}

	public String getSupervisionTime() {
		return supervisionTime;
	}

	public void setSupervisionTime(String supervisionTime) {
		this.supervisionTime = supervisionTime;
	}
}
