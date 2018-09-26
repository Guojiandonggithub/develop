package com.example.administrator.riskprojects.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.FiveDecisionsActivity;
import com.example.administrator.riskprojects.activity.HiddenDangerAcceptanceActivity;
import com.example.administrator.riskprojects.activity.HiddenRiskRecordAddEditActivity;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.SelectItem;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.List;

public class ReportRecord implements Constants{
	private static final String TAG = "ReportRecord";
	protected NetClient netClient;
	//验收
	public void addRecheck(final Context context) {
		netClient = new NetClient(context);
		String recheck = Utils.getValue(context,Constants.ADDRECHECK);
		if(!TextUtils.isEmpty(recheck)||recheck.length()>2){
			Log.e(TAG, "隐患验收上报数据" + recheck);
			final List<ThreeFix> threeFixList = JSONArray.parseArray(recheck, ThreeFix.class);
			for (final ThreeFix threeFix: threeFixList){
				RequestParams params = new RequestParams();
				params.put("id",threeFix.getId());
				params.put("recheckResult",threeFix.getRecheckResult());
				params.put("description",threeFix.getDescription());
				params.put("recheckPersonId",threeFix.getRecheckPersonId());
				params.put("recheckPersonName",threeFix.getRecheckPersonName());
				Log.e(TAG, "addRecheck: 隐患验收参数==="+params);
				netClient.post(Data.getInstance().getIp()+ Constants.ADD_RECHECK, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "隐患验收返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							threeFixList.remove(threeFix);
							String listStr = JSONArray.toJSONString(threeFixList);
							Log.e(TAG, "隐患复查有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADDRECHECK,listStr);
						}
					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "隐患验收返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "隐患验收没有数据");
		}
	}

	public void addAddhiddenrecord(final Context context){
		netClient = new NetClient(context);
		String hiddenRecord = Utils.getValue(context,Constants.ADDHIDDENRECORD);
		Log.e(TAG, "隐患添加上报数据: "+hiddenRecord);
		if(!TextUtils.isEmpty(hiddenRecord)||hiddenRecord.length()>2){
			final List<HiddenDangerRecord> hiddenDangerRecordList = JSONArray.parseArray(hiddenRecord, HiddenDangerRecord.class);
			for (final HiddenDangerRecord hiddenDangerRecord:hiddenDangerRecordList){
				RequestParams params = new RequestParams();
				String hiddenDangerRecordStr = JSON.toJSONString(hiddenDangerRecord);
				Log.i(TAG, "addHiddenDanger: 隐患添加修改="+hiddenDangerRecordStr);
				params.put("hiddenDangerRecordJsonData",hiddenDangerRecordStr);
				netClient.post(Data.getInstance().getIp()+Constants.ADD_HIDDENDANGERRECORD, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "添加修改记录上报返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							hiddenDangerRecordList.remove(hiddenDangerRecord);
							String listStr = JSONArray.toJSONString(hiddenDangerRecordList);
							Log.e(TAG, "隐患复查有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADDHIDDENRECORD,listStr);
						}

					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
					}
				});
			}
		}
	}
}

/*
 * 使用方法： 在getView里这样 ImageView bananaView = ViewHolder.get(convertView,
 * R.id.banana);
 */
