package com.example.administrator.riskprojects.bean;

import java.io.Serializable;

public class HiddenDangerRecord implements Serializable{
	private String id;//主键ID
	private String teamId;// 队组id
	private String teamName;//队组名称
	private String sid;// 所属专业id
	private String sname;// 专业名称
	private String gid;// 隐患类别id
	private String gname;// 隐患类别名称
	private String jbName;// 级别名字
	private String tid;// 隐患类型id
	private String tname;// 隐患类型名称
	private String areaId;// 所属区域id
	private String areaName;// 区域名称
	private String content;// 隐患内容
	private String findTime;// 发现隐患时间
	private String classId;// 班次id
	private String className;// 隐患发现的班次
	private String employeeId;// 录入员工id
	private String realName;// 隐患发现的人
	private String ishandle;// 是否处理：0：未处理， 1：已处理
	private String flag;// 标识 0:筛选 1：五定中 2：整改中 3 复查中 4 销项 5 落实
	private String canReport;// 是否可上报（0已上报，1未上报）
	private String reportState;// 上报状态（0已上报，1未上报）
	private String hiddenBelongId;// 隐患归属类型id
	private String hiddenBelong;// 隐患归属类型
	private String findPerson;// 隐患发现人
	private String teamGroupName;// 隐患单位
	private String teamGroupCode;// 隐患单位编码
	private String teamGroupId;// 隐患单位编码
	private String tipState;// 是否有建议
	private String hiddenCheckContent;// 检查内容
	private String hiddenCheckContentId;// 检查内容Id
	private String ispublic;// 是否公有
	private String carryOutDetail;// 隐患详情
	private String carryOutTime;// 落实时间
	private String lsdeptId;// 落实人室科id
	private String lsteamId;// 落实人队组Id
	private String lsteamName;// 落实人队组名字
	private String lsdeptName;// 落实人科室名字
	private String lsdeptType;// 落实人部门类型
	private String telephone;// 上报人手机号码
	private String hiddenPlace;// 隐患地点
	private String hiddenstockId;// 隐患库ID
	private String deptId;// 部门id
	private String departmentName;// 部门名称
	private String total;// 总数
	private String month;// 月份
	private String dangerSourceId;// 检查记录的危险源id


	private String code; // 隐患等级的code
	private String fixTime; // 定时
	private String deptName; // 科室名称
	private String threeFixEmployeeId; // 责任人id
	private String threeFixTeamName; // 责任队组名称
	private String threeFixRealName; // 责任人姓名
	private String measure; // 管理措施
	private String question; // 问题
	private String money; // 资金
	private String rectifyResult; // 整改结果0：整改 1：未整改
	private String recheckPersonName; // 复查人姓名
	private String recheckResult; // 复查结果，验收结果
	private String completeTime; // 整改结束时间
	private String practicablePerson; // 落实人姓名
	private String practicablePersonId; // 落实人ID
	private String personNum; // 处理人数
	private String zerendanwei; // 责任单位
	private String luoshidanwei; // 落实单位
	private String recheckDeptId; // 验收部门ID
	private String recheckDeptName; // 验收部门名称
	private String recheckPersonId; // 验收人
	private String recheckDeptType; // 验收单位
	private String threeFixId; // 整改ID
	private String deptType; // 部门类型：GS公司；KQ矿区
	private String dangerId; // 管理对象
	private String manageObject; // 管理对象
	private String standard; // 管理标准
	private String dutyPerson; // 主要责任人
	private String manager; // 直接管理人员和组织
	private String superviseDept; // 主要监管部门
	private String supervisePerson; // 主要监管人员
	private String measure2; // 标准的管理措施
	private String totalNum; // 总数
	private String hiddenDangerId; // 隐患id
	private String collieryName; // 矿区名称
	private String snames; // 专业
	private String isOrder; // 是否倒序
	private String ischecked; // 是否审核:(0:未审核;1:已审核)
	private String isupervision; // 是否挂牌督办（0：未挂牌督办；1：已挂牌督办）
	private String outTimeFlag; // 是否逾期（0：未逾期，1 : 逾期）

	public boolean isExpands() {
		return expands;
	}

	public void setExpands(boolean expands) {
		this.expands = expands;
	}

	private boolean expands; // 是否挂牌督办（0：未挂牌督办；1：已挂牌督办）




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getJbName() {
		return jbName;
	}

	public void setJbName(String jbName) {
		this.jbName = jbName;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFindTime() {
		return findTime;
	}

	public void setFindTime(String findTime) {
		this.findTime = findTime;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIshandle() {
		return ishandle;
	}

	public void setIshandle(String ishandle) {
		this.ishandle = ishandle;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCanReport() {
		return canReport;
	}

	public void setCanReport(String canReport) {
		this.canReport = canReport;
	}

	public String getReportState() {
		return reportState;
	}

	public void setReportState(String reportState) {
		this.reportState = reportState;
	}

	public String getHiddenBelongId() {
		return hiddenBelongId;
	}

	public void setHiddenBelongId(String hiddenBelongId) {
		this.hiddenBelongId = hiddenBelongId;
	}

	public String getHiddenBelong() {
		return hiddenBelong;
	}

	public void setHiddenBelong(String hiddenBelong) {
		this.hiddenBelong = hiddenBelong;
	}

	public String getFindPerson() {
		return findPerson;
	}

	public void setFindPerson(String findPerson) {
		this.findPerson = findPerson;
	}

	public String getTeamGroupName() {
		return teamGroupName;
	}

	public void setTeamGroupName(String teamGroupName) {
		this.teamGroupName = teamGroupName;
	}

	public String getTeamGroupCode() {
		return teamGroupCode;
	}

	public void setTeamGroupCode(String teamGroupCode) {
		this.teamGroupCode = teamGroupCode;
	}

	public String getTipState() {
		return tipState;
	}

	public void setTipState(String tipState) {
		this.tipState = tipState;
	}

	public String getHiddenCheckContent() {
		return hiddenCheckContent;
	}

	public void setHiddenCheckContent(String hiddenCheckContent) {
		this.hiddenCheckContent = hiddenCheckContent;
	}

	public String getHiddenCheckContentId() {
		return hiddenCheckContentId;
	}

	public void setHiddenCheckContentId(String hiddenCheckContentId) {
		this.hiddenCheckContentId = hiddenCheckContentId;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getCarryOutDetail() {
		return carryOutDetail;
	}

	public void setCarryOutDetail(String carryOutDetail) {
		this.carryOutDetail = carryOutDetail;
	}

	public String getCarryOutTime() {
		return carryOutTime;
	}

	public void setCarryOutTime(String carryOutTime) {
		this.carryOutTime = carryOutTime;
	}

	public String getLsdeptId() {
		return lsdeptId;
	}

	public void setLsdeptId(String lsdeptId) {
		this.lsdeptId = lsdeptId;
	}

	public String getLsteamId() {
		return lsteamId;
	}

	public void setLsteamId(String lsteamId) {
		this.lsteamId = lsteamId;
	}

	public String getLsteamName() {
		return lsteamName;
	}

	public void setLsteamName(String lsteamName) {
		this.lsteamName = lsteamName;
	}

	public String getLsdeptName() {
		return lsdeptName;
	}

	public void setLsdeptName(String lsdeptName) {
		this.lsdeptName = lsdeptName;
	}

	public String getLsdeptType() {
		return lsdeptType;
	}

	public void setLsdeptType(String lsdeptType) {
		this.lsdeptType = lsdeptType;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getHiddenPlace() {
		return hiddenPlace;
	}

	public void setHiddenPlace(String hiddenPlace) {
		this.hiddenPlace = hiddenPlace;
	}

	public String getHiddenstockId() {
		return hiddenstockId;
	}

	public void setHiddenstockId(String hiddenstockId) {
		this.hiddenstockId = hiddenstockId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFixTime() {
		return fixTime;
	}

	public void setFixTime(String fixTime) {
		this.fixTime = fixTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getThreeFixEmployeeId() {
		return threeFixEmployeeId;
	}

	public void setThreeFixEmployeeId(String threeFixEmployeeId) {
		this.threeFixEmployeeId = threeFixEmployeeId;
	}

	public String getThreeFixTeamName() {
		return threeFixTeamName;
	}

	public void setThreeFixTeamName(String threeFixTeamName) {
		this.threeFixTeamName = threeFixTeamName;
	}

	public String getThreeFixRealName() {
		return threeFixRealName;
	}

	public void setThreeFixRealName(String threeFixRealName) {
		this.threeFixRealName = threeFixRealName;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRectifyResult() {
		return rectifyResult;
	}

	public void setRectifyResult(String rectifyResult) {
		this.rectifyResult = rectifyResult;
	}

	public String getRecheckPersonName() {
		return recheckPersonName;
	}

	public void setRecheckPersonName(String recheckPersonName) {
		this.recheckPersonName = recheckPersonName;
	}

	public String getRecheckResult() {
		return recheckResult;
	}

	public void setRecheckResult(String recheckResult) {
		this.recheckResult = recheckResult;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getPracticablePerson() {
		return practicablePerson;
	}

	public void setPracticablePerson(String practicablePerson) {
		this.practicablePerson = practicablePerson;
	}

	public String getPracticablePersonId() {
		return practicablePersonId;
	}

	public void setPracticablePersonId(String practicablePersonId) {
		this.practicablePersonId = practicablePersonId;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public String getZerendanwei() {
		return zerendanwei;
	}

	public void setZerendanwei(String zerendanwei) {
		this.zerendanwei = zerendanwei;
	}

	public String getLuoshidanwei() {
		return luoshidanwei;
	}

	public void setLuoshidanwei(String luoshidanwei) {
		this.luoshidanwei = luoshidanwei;
	}

	public String getRecheckDeptId() {
		return recheckDeptId;
	}

	public void setRecheckDeptId(String recheckDeptId) {
		this.recheckDeptId = recheckDeptId;
	}

	public String getRecheckDeptName() {
		return recheckDeptName;
	}

	public void setRecheckDeptName(String recheckDeptName) {
		this.recheckDeptName = recheckDeptName;
	}

	public String getRecheckPersonId() {
		return recheckPersonId;
	}

	public void setRecheckPersonId(String recheckPersonId) {
		this.recheckPersonId = recheckPersonId;
	}

	public String getRecheckDeptType() {
		return recheckDeptType;
	}

	public void setRecheckDeptType(String recheckDeptType) {
		this.recheckDeptType = recheckDeptType;
	}

	public String getThreeFixId() {
		return threeFixId;
	}

	public void setThreeFixId(String threeFixId) {
		this.threeFixId = threeFixId;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDangerId() {
		return dangerId;
	}

	public void setDangerId(String dangerId) {
		this.dangerId = dangerId;
	}

	public String getManageObject() {
		return manageObject;
	}

	public void setManageObject(String manageObject) {
		this.manageObject = manageObject;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getDutyPerson() {
		return dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSuperviseDept() {
		return superviseDept;
	}

	public void setSuperviseDept(String superviseDept) {
		this.superviseDept = superviseDept;
	}

	public String getSupervisePerson() {
		return supervisePerson;
	}

	public void setSupervisePerson(String supervisePerson) {
		this.supervisePerson = supervisePerson;
	}

	public String getMeasure2() {
		return measure2;
	}

	public void setMeasure2(String measure2) {
		this.measure2 = measure2;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getHiddenDangerId() {
		return hiddenDangerId;
	}

	public void setHiddenDangerId(String hiddenDangerId) {
		this.hiddenDangerId = hiddenDangerId;
	}

	public String getCollieryName() {
		return collieryName;
	}

	public void setCollieryName(String collieryName) {
		this.collieryName = collieryName;
	}

	public String getSnames() {
		return snames;
	}

	public void setSnames(String snames) {
		this.snames = snames;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getIschecked() {
		return ischecked;
	}

	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}

	public String getIsupervision() {
		return isupervision;
	}

	public void setIsupervision(String isupervision) {
		this.isupervision = isupervision;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDangerSourceId() {
		return dangerSourceId;
	}

	public void setDangerSourceId(String dangerSourceId) {
		this.dangerSourceId = dangerSourceId;
	}

	public String getOutTimeFlag() {
		return outTimeFlag;
	}

	public void setOutTimeFlag(String outTimeFlag) {
		this.outTimeFlag = outTimeFlag;
	}

	public String getTeamGroupId() {
		return teamGroupId;
	}

	public void setTeamGroupId(String teamGroupId) {
		this.teamGroupId = teamGroupId;
	}
}
