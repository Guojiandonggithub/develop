package com.example.administrator.riskprojects.bean;

public class Specialty {
	private String id;//主键
	private String sname;// 专业名称
	private String scode;// 专业编码
	private String coefficient;// 权重系数
	private String normalscore;// 标准分数
	private String dutyPersonId;// 负责人
	private String dutyPerson;//负责人id
	private String deptId;// 部门id
	private String deptName;// 部门名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}

	public String getNormalscore() {
		return normalscore;
	}

	public void setNormalscore(String normalscore) {
		this.normalscore = normalscore;
	}

	public String getDutyPersonId() {
		return dutyPersonId;
	}

	public void setDutyPersonId(String dutyPersonId) {
		this.dutyPersonId = dutyPersonId;
	}

	public String getDutyPerson() {
		return dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
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
}
