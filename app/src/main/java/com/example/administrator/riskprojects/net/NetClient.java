package com.example.administrator.riskprojects.net;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.bean.UploadPic;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.dialog.FlippingLoadingDialog;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.AsyncHttpClient;
import com.juns.health.net.loopj.android.http.JsonHttpResponseHandler;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NetClient {
	private static final String TAG = "NetClient";
	private static Context context;
	protected FlippingLoadingDialog mLoadingDialog;
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
			Utils.showShortToast(context, Constants.NET_ERROR);
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
		System.out.println("!NetUtil.checkNetWork(context)：" + !NetUtil.checkNetWork(context));
		if (!NetUtil.checkNetWork(context)) {
			Log.e(TAG, "saveReportData===========: "+url);
			getLoadingDialog("正在连接服务器...  ").dismiss();
			saveReportData(url,params.toString());
			return;
		}else{
			try {
				if (params != null) {
					client.post(url, params, res);
				} else {
					client.post(url, res);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void saveReportData(String url,String param){
		System.out.print("url=================="+url);
		System.out.print("Constants.ADD_HIDDENDANGERRECORD=================="+Constants.ADD_HIDDENDANGERRECORD);
		switch (url) {
			case Constants.MAIN_ENGINE+Constants.ADD_HIDDENDANGERRECORD://添加隐患
				hiddenReport(param);
				break;
			case Constants.MAIN_ENGINE+Constants.ADD_RECHECK://添加验收
				recheckReport(param);
				break;
			case Constants.MAIN_ENGINE+Constants.UPLOAD_PIC://添加图片
				uploadPid(param);
				break;
			case Constants.MAIN_ENGINE+Constants.ADD_CARDRECORDLIST://添加打卡记录
				cardRecordReport(param);
				break;
			default:
				Utils.showShortToast(context, Constants.NET_ERROR);
				break;
		}
	}

	private void hiddenReport(String param){
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
		Utils.showShortToast(context, Constants.SAVE_DATA);
		Activity activity = findActivity(context);
		activity.finish();
	}

	private void recheckReport(String param){
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
		Utils.showShortToast(context, Constants.SAVE_DATA);
		Activity activity = findActivity(context);
		activity.finish();
	}

	private void uploadPid(String param){
		List<UploadPic> uploadPics = new ArrayList();
		String picurllist = Utils.getValue(context,Constants.ADDPIC);
		if(!TextUtils.isEmpty(picurllist)){
			uploadPics = JSONArray.parseArray(picurllist, UploadPic.class);
		}
		Map<String,String> map = getParameters(param);
		UploadPic uploadPic = new UploadPic();
		uploadPic.setRecord(map.get("record"));
		String fileurl = map.get("fileurl");
		List<String> fileList = JSONArray.parseArray(fileurl,String.class);
		uploadPic.setFileList(fileList);
		uploadPics.add(uploadPic);
		String listStr = JSONArray.toJSONString(uploadPics);
		Log.e(TAG, "隐患上传图片没网时: listStr============"+listStr);
		Utils.putValue(context,Constants.ADDPIC,listStr);
		Utils.showShortToast(context, Constants.SAVE_DATA);
		Activity activity = findActivity(context);
		activity.finish();
	}
	private void cardRecordReport(String param){
		List<String> cardrecordList = new ArrayList();
		String cardrecordListStr = Utils.getValue(context,Constants.CARDRECORD);
		if(!TextUtils.isEmpty(cardrecordListStr)){
			cardrecordList = JSONArray.parseArray(cardrecordListStr, String.class);
		}
		Map<String,String> map = getParameters(param);
		String str = map.get("carRecordJson");
		cardrecordList.add(str);
		String listStr = JSONArray.toJSONString(cardrecordList);
		Log.e(TAG, "打卡记录没网时: listStr============"+listStr);
		Utils.putValue(context,Constants.CARDRECORD,listStr);
		Utils.showShortToast(context, Constants.SAVE_DATA);
		//Activity activity = findActivity(context);
		//activity.finish();
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

	public FlippingLoadingDialog getLoadingDialog(String msg) {
		if (mLoadingDialog == null)
			mLoadingDialog = new FlippingLoadingDialog(context, msg);
		return mLoadingDialog;
	}

	@Nullable
	public static Activity findActivity(Context context) {
		if (context instanceof Activity) {
			return (Activity) context;
		}
		if (context instanceof ContextWrapper) {
			ContextWrapper wrapper = (ContextWrapper) context;
			return findActivity(wrapper.getBaseContext());
		} else {
			return null;
		}
	}
}
