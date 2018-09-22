package com.example.administrator.riskprojects.bean;

public class CollieryTeam {
	private String id;//主键
	private String teamName;// 队组名称
	private String chargeMan;// 负责人
	private String phone;// 队组电话
	private String beizhu;// 备注
	private String chargeManId;// 负责人id
	private String teamGroupCode;//编码
	private String specialtyid;// 专业id
	private String sname;// 专业名称
	private String realName;// 专业名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getChargeMan() {
		return chargeMan;
	}

	public void setChargeMan(String chargeMan) {
		this.chargeMan = chargeMan;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getChargeManId() {
		return chargeManId;
	}

	public void setChargeManId(String chargeManId) {
		this.chargeManId = chargeManId;
	}

	public String getTeamGroupCode() {
		return teamGroupCode;
	}

	public void setTeamGroupCode(String teamGroupCode) {
		this.teamGroupCode = teamGroupCode;
	}

	public String getSpecialtyid() {
		return specialtyid;
	}

	public void setSpecialtyid(String specialtyid) {
		this.specialtyid = specialtyid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
