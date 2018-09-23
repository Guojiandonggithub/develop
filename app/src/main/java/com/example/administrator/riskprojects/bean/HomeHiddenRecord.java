package com.example.administrator.riskprojects.bean;

public class HomeHiddenRecord {
	//各单位隐患统计
	private String month;//已处理数量
	private String page;// 当前页
	private String rows;//
	private String teamGroupCode;// 队组编码
	private String teamGroupName;// 队组名称
	private String total;// 未处理数量
	private String money;//
	private String totalNum;// 总数量
	//汇总隐患统计
	private String hiddenBelong;// 隐患归属
	private String hiddenBelongId;// 隐患归属id
	private String checkNum;//检查数量
	private String handleNum;//处理数量
	private String notHandleNum;//未处理数量
	private String questionNum;//有问题数量
	private String totalMoney;//总条数

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

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getHiddenBelong() {
		return hiddenBelong;
	}

	public void setHiddenBelong(String hiddenBelong) {
		this.hiddenBelong = hiddenBelong;
	}

	public String getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	public String getHandleNum() {
		return handleNum;
	}

	public void setHandleNum(String handleNum) {
		this.handleNum = handleNum;
	}

	public String getNotHandleNum() {
		return notHandleNum;
	}

	public void setNotHandleNum(String notHandleNum) {
		this.notHandleNum = notHandleNum;
	}

	public String getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(String questionNum) {
		this.questionNum = questionNum;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getHiddenBelongId() {
		return hiddenBelongId;
	}

	public void setHiddenBelongId(String hiddenBelongId) {
		this.hiddenBelongId = hiddenBelongId;
	}
}
