package com.example.administrator.riskprojects.tools;

import android.content.Context;

public interface Constants {

	// 聊天
	String LoginState = "LoginState";
	String UserInfo = "UserInfo";
	String NAME = "NAME";
	String PWD = "PWD";
	String ADDHIDDENRECORD = "addhiddenRecord";
	String ADDRECHECK = "addrecheck";
	// JSON status
	String Info = "content";
	String Value = "data";
	String Result = "type";
	String DB_NAME = "WeChat.db";
	String NET_ERROR = "网络错误，请稍后再试！";
	String SAVE_DATA = "网络错误，数据已保存，有网时自动上报！";
	String PAGE = "1";
	String ROWS = "10";
	// 外网地址
	String MAIN_ENGINE = "http://59.49.107.27:8187/yhpc/mobile/";
	String ISUPERVISION = "1";
	// 内网地址
	String INNER_MAIN_ENGINE = "123123";
	// 用户登录
	String Login_URL = "employeeMobileAction!employeeLogin.action";
	// 首页获取隐患统计数据
	String HOME_GET_HIDDENUM = "yesterdayHdStatisticsMobileAction!gethiddenDangerByMonth.action";
	// 首页获取隐患记录数据
	String HOME_GET_HIDDENRECORD = "yesterdayHdStatisticsMobileAction!getHiddenDangerRecordByTeam.action";
	// 获取隐患记录数据(挂牌督办)
	String GET_HIDDENRECORD = "hiddenDangerRecordMobileAction!findHiddenDangerRecord.action";
	// 各单位隐患统计查询接口
	String TEAMHDSTAISTICSDATAGRID = "yesterdayHdStatisticsMobileAction!teamHdStatisticsDataGrid.action";
	// 隐患汇总表查询接口
	String SUMARYMOBILE = "hiddenDangerSumaryMobileAction!findHiddenDangerSumReport.action";
	// 已删除隐患记录查询
	String DELETEMOBILE = "hiddenDangerSumaryMobileAction!findHiddenDangerSumReport.action";
	// 重复隐患记录查询
	String REPEATMOBILE = "hiddenDangerRepeatMobileAction!findHiddenDangerRepeat.action";
	// 隐患处理单位图表分析
	String DEPARTMENTSTATISTICSMOBILE = "hiddenDangerHandleDepartmentStatisticsMobileAction!findHiddenDangerDepartmentStatistics.action";
	// 隐患处理图表分析接口
	String HIDDENDANGERSPECIALSTATISTICS = "hiddenDangerHandleSpecialStatisticsMobileAction!findHiddenDangerSpecialStatistics.action";
	// 隐患年度图表分析接口
	String FINDHIDDENDANGERYEARCHARTSTATISTICS = "hiddenDangerYearChartStatisticsMobileAction!findHiddenDangerYearChartStatistics.action";
	// 隐患查询统计
	String QUERYSTATICMOBILE = "hiddenDangerQueryStaticMobileAction!findHiddenDangerQueryStatic.action";
	// 根据id获取隐患信息
	String HIDDENDANGERRECORD = "hiddenDangerRecordMobileAction!findHiddenDangerRecordById.action";
	// 获取部门/队组接口
	String GET_COLLIERYTEAM = "collieryTeamMobileAction!getCollieryTeamList.action";
	// 获取所属专业
	String GET_SPECIALTY = "specialtyMobileAction!getSpecialtyList.action";
	// 获取隐患类别
	String GET_RISKGRADE = "riskGradeMobileAction!getRiskGradeList.action";
	// 获取数据字典
	String GET_DATADICT = "dataDictMobileAction!getDataDictionaryList.action";
	// 获取班次
	String GET_CLASSNUMBER = "classNumberMobileAction!getClassNumberList.action";
	// 获取区域
	String GET_AREA = "areaMobileAction!getAreaList.action";
	// 删除隐患
	String DELETE_HIDDEN = "hiddenDangerRecordMobileAction!deleteHiddenDangerRecord.action";
	// 隐患下达查询
	String GET_HIDDENRELEASELIST = "hiddenDangerThreeFixMobileAction!findHiddenDangerThreeFix.action";
	// 隐患整改查询
	String GET_RECTIFICATIONLIST = "hiddenDangerRectifyMobileAction!findhiddenDangerRectify.action";
	// 隐患跟踪查询
	String GET_TRACKINGLIST = "hiddenDangerCarryOutMobileAction!findhiddenDangerCarryOut.action";
	// 隐患逾期查询
	String GET_OVERDUELIST = "hiddenDangerOutOfTimeMobileAction!findhiddenDangerOutTime.action";
	// 隐患逾期重启下达
	String HANDLEOUT_OVERDUELIST = "hiddenDangerOutOfTimeMobileAction!handleOutTime.action";
	// 隐患验收查询
	String GET_REVIEWLIST = "hiddenDangerRecheckMobileAction!findhiddenDangerRecheck.action";
	// 隐患跟踪记录查询列表
	String GET_FOLLINGRECORD = "hiddenFollingRecordMobileAction!findFollingRecordByThreeFixId.action";
	// 隐患跟踪记录添加
	String ADD_FOLLINGRECORD = "hiddenFollingRecordMobileAction!addHiddenFollingRecord.action";
	// 隐患跟踪记录修改
	String UPDATE_FOLLINGRECORD = "hiddenFollingRecordMobileAction!updateFollingRecord.action";
	// 隐患跟踪记录删除
	String DELETE_FOLLINGRECORD = "hiddenFollingRecordMobileAction!deleteFollingRecordById.action";
	// 隐患添加记录
	String ADD_HIDDENDANGERRECORD = "hiddenDangerRecordMobileAction!addHiddenDangerRecord.action";
	// 隐患修改记录
	String UPDATE_HIDDENDANGERRECORD = "hiddenDangerRecordMobileAction!updateHiddenDangerRecord.action";
	// 隐患验收
	String ADD_RECHECK = "hiddenDangerRecheckMobileAction!addRecheck.action";
	// 隐患整改
	String COMPLETERECTIFY = "hiddenDangerRectifyMobileAction!completeRectify.action";
	// 隐患下达
	String ADD_THREEFIXANDCONFIRM = "hiddenDangerThreeFixMobileAction!addThreeFixAndConfirm.action";
	// 首页下边统计详情接口
	String GET_HIDDENDANGERDETAILLIST = "yesterdayHdStatisticsMobileAction!getHiddenDangerDetailList.action";
	// 查询未消耗的数据
	String GET_NOTHANDLELIST = "hiddenDangerSumaryMobileAction!findHiddenDangerNotHandleList.action";
	// 获取整改人、跟踪人
	String GET_EMPLOYEELISTBYTEAMID = "collieryTeamMobileAction!getEmployeeListByTeamId.action";
	// 修改密码
	String UPDATE_PWD = "employeeMobileAction!editPwd.action";
	// 获取版本号和下载地址
	String UPDATE_VERSION = "employeeMobileAction!changeVersion.action";
	// 添加督办
	String ADD_SUPERVISIONRECORD = "hiddenSupervisionRecordMobileAction!addHiddenSupervisionRecord.action";
	// 督办列表查询
	String GET_SUPERVISIONRECORDLIST = "hiddenSupervisionRecordMobileAction!findSupervisionRecordByHiddenDangerId.action";


	public void addRecheck(Context context);
	public void addAddhiddenrecord(Context context);
}
