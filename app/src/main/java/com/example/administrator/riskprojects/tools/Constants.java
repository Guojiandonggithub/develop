package com.example.administrator.riskprojects.tools;

public interface Constants {

	// 聊天
	String LoginState = "LoginState";
	String UserInfo = "UserInfo";
	String NAME = "NAME";
	String PWD = "PWD";
	// JSON status
	String Info = "content";
	String Value = "data";
	String Result = "type";
	String DB_NAME = "WeChat.db";
	String NET_ERROR = "网络错误，请稍后再试！";
	// 主机地址
	String MAIN_ENGINE = "http://59.49.107.27:8187/yhpc/mobile/";
	// 用户登录
	String Login_URL = MAIN_ENGINE + "employeeMobileAction!employeeLogin.action";
	// 首页获取隐患统计数据
	String GET_HIDDENUM = MAIN_ENGINE + "yesterdayHdStatisticsMobileAction!gethiddenDangerByMonth.action";
	// 首页获取隐患记录数据
	String GET_HIDDENRECORD = MAIN_ENGINE + "yesterdayHdStatisticsMobileAction!getHiddenDangerRecordByTeam.action";

}
