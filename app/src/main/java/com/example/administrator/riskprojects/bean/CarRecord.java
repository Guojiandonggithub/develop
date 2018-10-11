package com.example.administrator.riskprojects.bean;

public class CarRecord {
	private String id;//主键
	private String cardAdd;// 打卡区域
	private String cardCode;// 打卡编码
	private String cardTime;// 打卡时间
	private String classNumber;// 打卡班次
	private String userId;// 用户id

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCardAdd() {
		return cardAdd;
	}

	public void setCardAdd(String cardAdd) {
		this.cardAdd = cardAdd;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardTime() {
		return cardTime;
	}

	public void setCardTime(String cardTime) {
		this.cardTime = cardTime;
	}

	public String getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
