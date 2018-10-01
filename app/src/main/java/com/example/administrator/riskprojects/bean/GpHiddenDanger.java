package com.example.administrator.riskprojects.bean;

/**
 * 挂牌实体
 */
public class GpHiddenDanger {
	private String id;//隐患id
	private String supervisionPersonId;// 挂牌人员Id
	private String supervisionPersonName;// 挂牌人员姓名
	private String supervisionDeptId;// 挂牌部门Id
	private String supervisionDeptName;// 挂牌部门名称
	private String supervisionDetail;// 挂牌整改信息
	private String supervisionTime;// 挂牌时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupervisionPersonId() {
		return supervisionPersonId;
	}

	public void setSupervisionPersonId(String supervisionPersonId) {
		this.supervisionPersonId = supervisionPersonId;
	}

	public String getSupervisionPersonName() {
		return supervisionPersonName;
	}

	public void setSupervisionPersonName(String supervisionPersonName) {
		this.supervisionPersonName = supervisionPersonName;
	}

	public String getSupervisionDeptId() {
		return supervisionDeptId;
	}

	public void setSupervisionDeptId(String supervisionDeptId) {
		this.supervisionDeptId = supervisionDeptId;
	}

	public String getSupervisionDeptName() {
		return supervisionDeptName;
	}

	public void setSupervisionDeptName(String supervisionDeptName) {
		this.supervisionDeptName = supervisionDeptName;
	}

	public String getSupervisionDetail() {
		return supervisionDetail;
	}

	public void setSupervisionDetail(String supervisionDetail) {
		this.supervisionDetail = supervisionDetail;
	}

	public String getSupervisionTime() {
		return supervisionTime;
	}

	public void setSupervisionTime(String supervisionTime) {
		this.supervisionTime = supervisionTime;
	}
}
