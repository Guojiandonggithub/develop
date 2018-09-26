package com.example.administrator.riskprojects.net;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HiddenFollingRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.AsyncHttpClient;
import com.juns.health.net.loopj.android.http.JsonHttpResponseHandler;
import com.juns.health.net.loopj.android.http.RequestParams;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NetClient {
	private static final String TAG = "NetClient";
	private static Context context;
	// http 请求
	private AsyncHttpClient client;
	// 超时时间
	private int TIMEOUT = 20000;


	public NetClient(Context context) {
		NetClient.context = context;
		client = new AsyncHttpClient();
		client.setTimeout(TIMEOUT);
	}

	/**
	 * get方式请求调用方法 返回格式均为json对象 返回为json
	 * 
	 * @param url
	 *            请求URL
	 * @param params
	 *            请求参数 可以为空
	 * @param res
	 *            必须实现此类 处理成功失败等 回调
	 */
	public void get(String url, RequestParams params,
                    final JsonHttpResponseHandler res) {
		if (!NetUtil.checkNetWork(context)) {
			Utils.showLongToast(context, Constants.NET_ERROR);
			return;
		}
		try {
			if (params != null)
				// 带请求参数 获取json对象
				client.get(url, params, res);
			else
				// 不请求参数 获取json对象
				client.get(url, res);
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	/**
	 * json post方式请求调用方法 返回为json
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数 可以为空
	 * @param res
	 *            必须实现此类 处理成功失败等 回调
	 */
	public void post(String url, RequestParams params,
                     final JsonHttpResponseHandler res) {
		System.out.println("请求URL：" + url);
		System.out.println("params：" + params);
		if (!NetUtil.checkNetWork(context)) {
			saveReportData(url,params.toString());
			return;
		}
		try {
			if (params != null) {
				client.post(url, params, res);
			} else {
				client.post(url, res);
			}
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}

	private void saveReportData(String url,String param){
		if(url.contains(Constants.ADD_HIDDENDANGERRECORD)){
			List<HiddenDangerRecord> recordList = new ArrayList<>();
			String hiddenRecord = Utils.getValue(context,Constants.ADDHIDDENRECORD);
			if(!TextUtils.isEmpty(hiddenRecord)){
				recordList = JSONArray.parseArray(hiddenRecord, HiddenDangerRecord.class);
			}
			HiddenDangerRecord record = JSONArray.parseObject(param.split("=")[1], HiddenDangerRecord.class);
			recordList.add(record);
			String listStr = JSONArray.toJSONString(recordList);
			Log.e(TAG, "隐患上报没网时: listStr============"+listStr);
			Utils.putValue(context,Constants.ADDHIDDENRECORD,listStr);
			Utils.showLongToast(context, Constants.SAVE_DATA);
		}else if(url.contains(Constants.ADD_RECHECK)){
			List<ThreeFix> threeFixList = new ArrayList<>();
			String hiddenRecord = Utils.getValue(context,Constants.ADDRECHECK);
			if(!TextUtils.isEmpty(hiddenRecord)){
				threeFixList = JSONArray.parseArray(hiddenRecord, ThreeFix.class);
			}
			ThreeFix threeFix = new ThreeFix();
			Map<String,String> map = getParameters(param);
			threeFix.setId(map.get("id"));
			threeFix.setRecheckPersonId(map.get("recheckPersonId"));
			threeFix.setRecheckPersonName(map.get("recheckPersonName"));
			threeFix.setRecheckResult(map.get("recheckResult"));
			threeFix.setDescription(map.get("description"));
			threeFixList.add(threeFix);
			String listStr = JSONArray.toJSONString(threeFixList);
			Log.e(TAG, "隐患复查没网时: listStr============"+listStr);
			Utils.putValue(context,Constants.ADDRECHECK,listStr);
			Utils.showLongToast(context, Constants.SAVE_DATA);
		}else{
			Utils.showLongToast(context, Constants.NET_ERROR);
		}
	}


	public static final String AND = "&";

	public static final String EQUAL = "=";

	private Map<String, String> getParameters(String content) {

		Map<String, String> params =new TreeMap<String, String>();

		if(content != null) {

			String[] nameValuePairs = content.split(AND);

			if(nameValuePairs != null && nameValuePairs.length > 0) {

				for(String nameValuePair : nameValuePairs) {

					String[] parameter = nameValuePair.split(EQUAL);

					if(parameter != null && parameter.length == 2) {

						params.put(parameter[0], parameter[1]);

					}

				}

			}

		}

		return params;

	}
}
