package com.example.administrator.riskprojects.bean;

import java.io.Serializable;

/**
 * 辨识评估
 */
public class IdentificationEvaluation implements Serializable{
	private String id;//主键ID
	private String kuangQuId;// 矿区Id
	private String kuangQuName;//矿区名称
	private String evaluationType;// 辨识评估类型 0 年度辨识评估 1专项辨识评估
	private String riskPlace;// 风险地点
	private String riskContent;// 风险描述
	private String dsId ;// 灾害ID
	private String dsName;// 灾害类型
	private String possibility;// 可能性分值
	private String exposureRate;// 暴露率
	private String result;// 后果
	private String riskScore;// 风险值
	private String riskGnameId;// 风险等级Id
	private String riskGname;// 风险等级名称
	private String measures;// 管理措施
	private String dutyDeptId;// 责任单位Id
	private String dutyDeptName;// 责任单位名称
	private String dutyPersonId;// 责任人Id
	private String dutyPersonName;// 责任人姓名
	private String controlTeamId;// 管控队组Id
	private String controlTeamName;// 管控队组名称
	private String starTime;// 开始时间
	private String openType;// 开启状态 （0 关闭 1开启）
	private String endTime;// 结束时间
	private String personsControl;// 人数控制
	private String listOrder;// 排序
	private String controlYear;// 管控年
	private String controlMonths;// 管控月
	private String recordTime;// 管控月

	public boolean isExpands() {
		return expands;
	}

	public void setExpands(boolean expands) {
		this.expands = expands;
	}

	private boolean expands; // 是否挂牌督办（0：未挂牌督办；1：已挂牌督办）


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKuangQuId() {
		return kuangQuId;
	}

	public void setKuangQuId(String kuangQuId) {
		this.kuangQuId = kuangQuId;
	}

	public String getKuangQuName() {
		return kuangQuName;
	}

	public void setKuangQuName(String kuangQuName) {
		this.kuangQuName = kuangQuName;
	}

	public String getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getRiskPlace() {
		return riskPlace;
	}

	public void setRiskPlace(String riskPlace) {
		this.riskPlace = riskPlace;
	}

	public String getRiskContent() {
		return riskContent;
	}

	public void setRiskContent(String riskContent) {
		this.riskContent = riskContent;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getPossibility() {
		return possibility;
	}

	public void setPossibility(String possibility) {
		this.possibility = possibility;
	}

	public String getExposureRate() {
		return exposureRate;
	}

	public void setExposureRate(String exposureRate) {
		this.exposureRate = exposureRate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}

	public String getRiskGnameId() {
		return riskGnameId;
	}

	public void setRiskGnameId(String riskGnameId) {
		this.riskGnameId = riskGnameId;
	}

	public String getRiskGname() {
		return riskGname;
	}

	public void setRiskGname(String riskGname) {
		this.riskGname = riskGname;
	}

	public String getMeasures() {
		return measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}

	public String getDutyDeptId() {
		return dutyDeptId;
	}

	public void setDutyDeptId(String dutyDeptId) {
		this.dutyDeptId = dutyDeptId;
	}

	public String getDutyDeptName() {
		return dutyDeptName;
	}

	public void setDutyDeptName(String dutyDeptName) {
		this.dutyDeptName = dutyDeptName;
	}

	public String getDutyPersonId() {
		return dutyPersonId;
	}

	public void setDutyPersonId(String dutyPersonId) {
		this.dutyPersonId = dutyPersonId;
	}

	public String getDutyPersonName() {
		return dutyPersonName;
	}

	public void setDutyPersonName(String dutyPersonName) {
		this.dutyPersonName = dutyPersonName;
	}

	public String getControlTeamId() {
		return controlTeamId;
	}

	public void setControlTeamId(String controlTeamId) {
		this.controlTeamId = controlTeamId;
	}

	public String getControlTeamName() {
		return controlTeamName;
	}

	public void setControlTeamName(String controlTeamName) {
		this.controlTeamName = controlTeamName;
	}

	public String getStarTime() {
		return starTime;
	}

	public void setStarTime(String starTime) {
		this.starTime = starTime;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPersonsControl() {
		return personsControl;
	}

	public void setPersonsControl(String personsControl) {
		this.personsControl = personsControl;
	}

	public String getListOrder() {
		return listOrder;
	}

	public void setListOrder(String listOrder) {
		this.listOrder = listOrder;
	}

	public String getControlYear() {
		return controlYear;
	}

	public void setControlYear(String controlYear) {
		this.controlYear = controlYear;
	}

	public String getControlMonths() {
		return controlMonths;
	}

	public void setControlMonths(String controlMonths) {
		this.controlMonths = controlMonths;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
}
