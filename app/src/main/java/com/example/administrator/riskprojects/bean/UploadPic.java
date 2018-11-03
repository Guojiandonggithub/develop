package com.example.administrator.riskprojects.bean;

import java.util.List;

public class UploadPic {
	private String record;// 隐患字符串
	private List<String> fileList;// 图片文件列表

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}
}