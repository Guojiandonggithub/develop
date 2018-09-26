package com.example.administrator.riskprojects.activity;

import android.app.Application;

import com.example.administrator.riskprojects.tools.Constants;

public class Data extends Application{
	private String ip;
	private boolean Wifi;
	private boolean Mobile;
	private boolean Connected;

	private static Data instance;
	public static Data getInstance() {
		return instance;
	}
	@Override
	public void onCreate(){
		ip = Constants.MAIN_ENGINE;
		super.onCreate();
		instance = this;
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
}

