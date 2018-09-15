package com.example.administrator.riskprojects.bean;

public class HomeHiddenRecord {
	private String month;//已处理数量
	private String page;// 当前页
	private String rows;//
	private String teamGroupCode;// 队组编码
	private String teamGroupName;// 队组名称
	private String total;// 未处理数量

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getTeamGroupCode() {
		return teamGroupCode;
	}

	public void setTeamGroupCode(String teamGroupCode) {
		this.teamGroupCode = teamGroupCode;
	}

	public String getTeamGroupName() {
		return teamGroupName;
	}

	public void setTeamGroupName(String teamGroupName) {
		this.teamGroupName = teamGroupName;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
