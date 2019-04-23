package com.example.administrator.riskprojects.activity;

import android.app.Application;

import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;

import cn.jpush.android.api.JPushInterface;

import static com.example.administrator.riskprojects.activity.TagAliasOperatorHelper.ACTION_DELETE;
import static com.example.administrator.riskprojects.activity.TagAliasOperatorHelper.sequence;

public class Data extends Application{
	private String ip;
	private boolean Wifi;
	private boolean Mobile;
	private boolean Connected;
	private boolean isConnect = true;

	private static Data instance;
	public static Data getInstance() {
		return instance;
	}
	@Override
	public void onCreate(){
		ip = Constants.MAIN_ENGINE;
		super.onCreate();
		instance = this;
		setUpJpush();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public boolean isWifi() {
		return Wifi;
	}

	public void setWifi(boolean wifi) {
		Wifi = wifi;
	}

	public boolean isMobile() {
		return Mobile;
	}

	public void setMobile(boolean mobile) {
		Mobile = mobile;
	}

	public boolean isConnected() {
		return Connected;
	}

	public void setConnected(boolean connected) {
		Connected = connected;
	}

	private void setUpJpush() {
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

	public void deleteAlias() {
		TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
		tagAliasBean.action = ACTION_DELETE;
		sequence++;
		tagAliasBean.alias = UserUtils.getUserID(this);
		tagAliasBean.isAliasAction = true;
		TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
	}

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean connect) {
		isConnect = connect;
	}
}

