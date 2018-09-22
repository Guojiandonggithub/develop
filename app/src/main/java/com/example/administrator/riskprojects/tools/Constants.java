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
	String PAGE = "1";
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
	// 隐患汇总表查询接口
	String SUMARYMOBILE = MAIN_ENGINE + "hiddenDangerSumaryMobileAction!findHiddenDangerSumReport.action";
	// 已删除隐患记录查询
	String DELETEMOBILE = MAIN_ENGINE + "hiddenDangerSumaryMobileAction!findHiddenDangerSumReport.action";
	// 重复隐患记录查询
	String REPEATMOBILE = MAIN_ENGINE + "hiddenDangerRepeatMobileAction!findHiddenDangerRepeat.action";
	// 隐患处理单位图表分析
	String DEPARTMENTSTATISTICSMOBILE = MAIN_ENGINE + "hiddenDangerRepeatMobileAction!findHiddenDangerRepeat.action";
	// 隐患查询统计
	String QUERYSTATICMOBILE = MAIN_ENGINE + "hiddenDangerQueryStaticMobileAction!findHiddenDangerQueryStatic.action";
	// 根据id获取隐患信息
	String HIDDENDANGERRECORD = MAIN_ENGINE + "hiddenDangerRecordMobileAction!findHiddenDangerRecordById.action";
	// 获取部门/队组接口
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
	// 隐患逾期重启下达
	String HANDLEOUT_OVERDUELIST = MAIN_ENGINE + "hiddenDangerOutOfTimeMobileAction!handleOutTime.action";
	// 隐患验收查询
	String GET_REVIEWLIST = MAIN_ENGINE + "hiddenDangerRecheckMobileAction!findhiddenDangerRecheck.action";
	// 隐患跟踪记录查询列表
	String GET_FOLLINGRECORD = MAIN_ENGINE + "hiddenFollingRecordMobileAction!findFollingRecordByThreeFixId.action";
	// 隐患跟踪记录添加
	String ADD_FOLLINGRECORD = MAIN_ENGINE + "hiddenFollingRecordMobileAction!addHiddenFollingRecord.action";
	// 隐患跟踪记录修改
	String UPDATE_FOLLINGRECORD = MAIN_ENGINE + "hiddenFollingRecordMobileAction!updateFollingRecord.action";
	// 隐患跟踪记录删除
	String DELETE_FOLLINGRECORD = MAIN_ENGINE + "hiddenFollingRecordMobileAction!deleteFollingRecordById.action";
	// 隐患添加记录
	String ADD_HIDDENDANGERRECORD = MAIN_ENGINE + "hiddenDangerRecordMobileAction!addHiddenDangerRecord.action";
	// 隐患修改记录
	String UPDATE_HIDDENDANGERRECORD = MAIN_ENGINE + "hiddenDangerRecordMobileAction!updateHiddenDangerRecord.action";
	// 隐患验收
	String ADD_RECHECK = MAIN_ENGINE + "hiddenDangerRecheckMobileAction!addRecheck.action";
	// 隐患整改
	String COMPLETERECTIFY = MAIN_ENGINE + "hiddenDangerRectifyMobileAction!completeRectify.action";
	// 隐患下达
	String ADD_THREEFIXANDCONFIRM = MAIN_ENGINE + "hiddenDangerThreeFixMobileAction!addThreeFixAndConfirm.action";
	// 首页下边统计详情接口
	String GET_HIDDENDANGERDETAILLIST = MAIN_ENGINE + "yesterdayHdStatisticsMobileAction!getHiddenDangerDetailList.action";
	// 获取整改人、跟踪人
	String GET_EMPLOYEELISTBYTEAMID = MAIN_ENGINE + "collieryTeamMobileAction!getEmployeeListByTeamId.action";

}
