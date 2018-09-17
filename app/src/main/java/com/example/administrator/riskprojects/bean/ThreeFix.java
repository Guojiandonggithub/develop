package com.example.administrator.riskprojects.bean;

public class ThreeFix {
	private String id;//主键ID
	private String hiddenDangerId;// 隐患id
	private String fixTime;//定时
	private String deptId;// 科室id
	private String deptName;// 科室名称
	private String lsdeptId;// 落实人室科id
	private String lsdeptName;// 落实人科室名字
	private String teamId;// 队组id
	private String teamName;// 队组名称
	private String lsteamId;// 落实人队组Id
	private String lsteamName;// 落实人队组名字
	private String employeeId;// 责任人id
	private String realName;// 责任人姓名
	private String measure;// 措施
	private String question;// 问题
	private String money;// 资金
	private String personNum;// 处理人数
	private String rectifyResult;// 整改结果
	private String completeTime;// 整改结束时间
	private String carryOutDetail;// 落实详情
	private String carryOutTime;// 落实时间
	private String tracer;// 隐患跟踪人员
	private String recheckPersonId;// 复查人id
	private String recheckPersonName;// 复查人姓名
	private String recheckResult;// 复查结果0:通过 1：不通过
	private String description;// 备注
	private String isEnd;// 是否终结 0 ：终结 1：未终结
	private String sname;// 专业名称
	private String gname;// 隐患类别（字典
	private String areaName;// 区域
	private String tname;// 隐患类型名称
	private String content;// 隐患内容
	private String findTime;// 隐患发现人
	private String className;// 隐患发现的班次
	private String ishandle;// 是否处理：0：未处理， 1：已处理
	private String jbName;// 级别名字
	private String hdId;// 隐患id
	private String flag;// 隐患状态 0:筛选 1：五定中 2：整改中 3 复查中 4 销项 5 跟踪
	private String reportState;// 上报状态
	private String scode;// 分数
	private String hiddenBelong;// 隐患归属类型
	private String hiddenCheckContent;// 检查内容
	private String ispublic;// 是否公有
	private String teamGroupName;// 隐患单位
	private String teamGroupCode;// 隐患单位编码
	private String findPerson;// 隐患发现人


	private String zerendanwei; // 责任单位
	private String luoshidanwei; // 落实单位
	private String practicablePerson; // 落实人
	private String practicablePersonId; // 落实人Id
	private String recheckDeptId; // 指定复查人部门id
	private String recheckDeptName; // 指定复查人部门名称
	private String recheckDeptType; // 指定复查人部门类型
	private String deptType; // 部门类型公司或是矿区
	private String lsdeptType; // 部门类型公司或是矿区
	private String hiddenPlace; // 隐患附加地点
	private String snames; // 专业
	private String follingPersonId; // 跟踪人id
	private String follingPersonName; // 跟踪人姓名
	private String follingTeamId; // 跟踪人单位id
	private String follingTeamName; // 跟踪人单位名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHiddenDangerId() {
		return hiddenDangerId;
	}

	public void setHiddenDangerId(String hiddenDangerId) {
		this.hiddenDangerId = hiddenDangerId;
	}

	public String getFixTime() {
		return fixTime;
	}

	public void setFixTime(String fixTime) {
		this.fixTime = fixTime;
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

	public String getLsdeptId() {
		return lsdeptId;
	}

	public void setLsdeptId(String lsdeptId) {
		this.lsdeptId = lsdeptId;
	}

	public String getLsdeptName() {
		return lsdeptName;
	}

	public void setLsdeptName(String lsdeptName) {
		this.lsdeptName = lsdeptName;
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

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public String getRectifyResult() {
		return rectifyResult;
	}

	public void setRectifyResult(String rectifyResult) {
		this.rectifyResult = rectifyResult;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
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

	public String getTracer() {
		return tracer;
	}

	public void setTracer(String tracer) {
		this.tracer = tracer;
	}

	public String getRecheckPersonId() {
		return recheckPersonId;
	}

	public void setRecheckPersonId(String recheckPersonId) {
		this.recheckPersonId = recheckPersonId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIshandle() {
		return ishandle;
	}

	public void setIshandle(String ishandle) {
		this.ishandle = ishandle;
	}

	public String getJbName() {
		return jbName;
	}

	public void setJbName(String jbName) {
		this.jbName = jbName;
	}

	public String getHdId() {
		return hdId;
	}

	public void setHdId(String hdId) {
		this.hdId = hdId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getReportState() {
		return reportState;
	}

	public void setReportState(String reportState) {
		this.reportState = reportState;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getHiddenBelong() {
		return hiddenBelong;
	}

	public void setHiddenBelong(String hiddenBelong) {
		this.hiddenBelong = hiddenBelong;
	}

	public String getHiddenCheckContent() {
		return hiddenCheckContent;
	}

	public void setHiddenCheckContent(String hiddenCheckContent) {
		this.hiddenCheckContent = hiddenCheckContent;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
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

	public String getFindPerson() {
		return findPerson;
	}

	public void setFindPerson(String findPerson) {
		this.findPerson = findPerson;
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

	public String getRecheckDeptType() {
		return recheckDeptType;
	}

	public void setRecheckDeptType(String recheckDeptType) {
		this.recheckDeptType = recheckDeptType;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getLsdeptType() {
		return lsdeptType;
	}

	public void setLsdeptType(String lsdeptType) {
		this.lsdeptType = lsdeptType;
	}

	public String getHiddenPlace() {
		return hiddenPlace;
	}

	public void setHiddenPlace(String hiddenPlace) {
		this.hiddenPlace = hiddenPlace;
	}

	public String getSnames() {
		return snames;
	}

	public void setSnames(String snames) {
		this.snames = snames;
	}

	public String getFollingPersonId() {
		return follingPersonId;
	}

	public void setFollingPersonId(String follingPersonId) {
		this.follingPersonId = follingPersonId;
	}

	public String getFollingPersonName() {
		return follingPersonName;
	}

	public void setFollingPersonName(String follingPersonName) {
		this.follingPersonName = follingPersonName;
	}

	public String getFollingTeamId() {
		return follingTeamId;
	}

	public void setFollingTeamId(String follingTeamId) {
		this.follingTeamId = follingTeamId;
	}

	public String getFollingTeamName() {
		return follingTeamName;
	}

	public void setFollingTeamName(String follingTeamName) {
		this.follingTeamName = follingTeamName;
	}
}
