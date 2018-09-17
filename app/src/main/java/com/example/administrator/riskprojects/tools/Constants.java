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
	String PAGE = "0";
	String ROWS = "10";
	// 主机地址
	String MAIN_ENGINE = "http://59.49.107.27:8187/yhpc/mobile/";
	// 用户登录
	String Login_URL = MAIN_ENGINE + "employeeMobileAction!employeeLogin.action";
	// 首页获取隐患统计数据
	String HOME_GET_HIDDENUM = MAIN_ENGINE + "yesterdayHdStatisticsMobileAction!gethiddenDangerByMonth.action";
	// 首页获取隐患记录数据
	String HOME_GET_HIDDENRECORD = MAIN_ENGINE + "yesterdayHdStatisticsMobileAction!getHiddenDangerRecordByTeam.action";
	// 获取隐患记录数据(挂牌督办)
	String GET_HIDDENRECORD = MAIN_ENGINE + "hiddenDangerRecordMobileAction!findHiddenDangerRecord.action";
	// 各单位隐患统计查询接口
	String TEAMHDSTAISTICSDATAGRID = MAIN_ENGINE + "yesterdayHdStatisticsMobileAction!teamHdStatisticsDataGrid.action";
	// 根据id获取隐患信息
	String HIDDENDANGERRECORD = MAIN_ENGINE + "hiddenDangerRecordMobileAction!findHiddenDangerRecordById.action";
	// 获取部门/队组成员接口
	String GET_COLLIERYTEAM = MAIN_ENGINE + "collieryTeamMobileAction!getCollieryTeamList.action";
	// 获取所属专业
	String GET_SPECIALTY = MAIN_ENGINE + "specialtyMobileAction!getSpecialtyList.action";
	// 获取隐患类别
	String GET_RISKGRADE = MAIN_ENGINE + "riskGradeMobileAction!getRiskGradeList.action";
	// 获取数据字典
	String GET_DATADICT = MAIN_ENGINE + "dataDictMobileAction!getDataDictionaryList.action";
	// 获取班次
	String GET_CLASSNUMBER = MAIN_ENGINE + "classNumberMobileAction!getClassNumberList.action";
	// 获取区域
	String GET_AREA = MAIN_ENGINE + "areaMobileAction!getAreaList.action";
	// 删除隐患
	String DELETE_HIDDEN = MAIN_ENGINE + "hiddenDangerRecordMobileAction!deleteHiddenDangerRecord.action";
	// 隐患下达查询
	String GET_HIDDENRELEASELIST = MAIN_ENGINE + "hiddenDangerThreeFixMobileAction!findHiddenDangerThreeFix.action";
	// 隐患整改查询
	String GET_RECTIFICATIONLIST = MAIN_ENGINE + "hiddenDangerRectifyMobileAction!findhiddenDangerRectify.action";
	// 隐患跟踪查询
	String GET_TRACKINGLIST = MAIN_ENGINE + "hiddenDangerCarryOutMobileAction!findhiddenDangerCarryOut.action";
	// 隐患逾期查询
	String GET_OVERDUELIST = MAIN_ENGINE + "hiddenDangerOutOfTimeMobileAction!findhiddenDangerOutTime.action";
	// 隐患验收查询
	String GET_REVIEWLIST = MAIN_ENGINE + "hiddenDangerRecheckMobileAction!findhiddenDangerRecheck.action";

}
