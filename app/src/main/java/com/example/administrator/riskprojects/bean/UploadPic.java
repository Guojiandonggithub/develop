package com.example.administrator.riskprojects.bean;

import java.io.File;
import java.util.List;

public class UploadPic {
	private String record;// 隐患字符串
	private String fileList;// 图片文件列表

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
}