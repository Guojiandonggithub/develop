package com.example.administrator.riskprojects.common;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.ThreeFix;
import com.example.administrator.riskprojects.bean.UploadPic;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class ReportRecord implements Constants{
	private static final String TAG = "ReportRecord";
	protected NetClient netClient;
	//验收
	public void addRecheck(final Context context) {
		netClient = new NetClient(context);
		String recheck = Utils.getValue(context,Constants.ADDRECHECK);
		if(!TextUtils.isEmpty(recheck)&&recheck.length()>2){
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
		if(!TextUtils.isEmpty(hiddenRecord)&&hiddenRecord.length()>2){
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
							Log.e(TAG, "隐患添加有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADDHIDDENRECORD,listStr);
						}

					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "隐患添加没有数据");
		}
	}

	//上传隐患图片
	public void addHiddenPic(final Context context){
		netClient = new NetClient(context);
		String hiddenpicStr = Utils.getValue(context,Constants.ADDPIC);
		Log.e(TAG, "上传隐患图片上报数据: "+hiddenpicStr);
		if(!TextUtils.isEmpty(hiddenpicStr)||hiddenpicStr.length()>2){
			final List<UploadPic> uploadPicList = JSONArray.parseArray(hiddenpicStr, UploadPic.class);
			RequestParams params = new RequestParams();
			for(final UploadPic uploadPic:uploadPicList){
				List<String> fileList = uploadPic.getFileList();
				for (int i=0;i<fileList.size();i++){
					if(!fileList.get(i).contains("http://")){
						File file = new File(fileList.get(i));
						try {
							params.put(i+"", file);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}
				netClient.post(Data.getInstance().getIp()+Constants.UPLOAD_PIC, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "上传隐患图片返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							HiddenDangerRecord hiddenDangerRecord = JSON.parseObject(uploadPic.getRecord(),HiddenDangerRecord.class);
							hiddenDangerRecord.setImageGroup(data);
							String hiddenDangerRecordStr = JSON.toJSONString(hiddenDangerRecord);
							addhiddenrecord(context,hiddenDangerRecordStr,uploadPicList,uploadPic);
						}

					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "图片上传没有数据");
		}
	}

	public void addhiddenrecord(final Context context,String hiddenRecord,final List<UploadPic> uploadPicList,final UploadPic uploadPic){
		netClient = new NetClient(context);
		Log.e(TAG, "隐患添加上报数据: "+hiddenRecord);
		if(!TextUtils.isEmpty(hiddenRecord)){
				RequestParams params = new RequestParams();
				Log.i(TAG, "addHiddenDanger: 隐患添加修改="+hiddenRecord);
				params.put("hiddenDangerRecordJsonData",hiddenRecord);
				netClient.post(Data.getInstance().getIp()+Constants.ADD_HIDDENDANGERRECORD, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "添加修改记录上报返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							uploadPicList.remove(uploadPic);
							String listStr = JSONArray.toJSONString(uploadPicList);
							Log.e(TAG, "上传图片有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADDPIC,listStr);
						}

					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "添加修改隐患记录返回错误信息：" + content);
					}
				});
			}
		}

	//提交巡检记录
	public void addCardRecord(final Context context) {
		netClient = new NetClient(context);
		String cardrecordStr = Utils.getValue(context,Constants.CARDRECORD);
		Log.e(TAG, "提交巡检记录上报数据: "+cardrecordStr);
		if(!TextUtils.isEmpty(cardrecordStr)||cardrecordStr.length()>2){
			final List<String> carRecordList = JSONArray.parseArray(cardrecordStr, String.class);
			Log.e(TAG, "carRecordList============: "+carRecordList);
			for (final String carRecord:carRecordList){
				RequestParams params = new RequestParams();
				Log.e(TAG, "carRecord============: "+carRecord);
				Log.e(TAG, "netClient============: "+netClient);
				params.put("carRecordJson", carRecord);
				netClient.post(Data.getInstance().getIp()+ Constants.ADD_CARDRECORDLIST, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "提交巡检记录返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							carRecordList.remove(carRecord);
							String listStr = JSONArray.toJSONString(carRecordList);
							Log.e(TAG, "提交巡检记录有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.CARDRECORD,listStr);
						}
					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "提交巡检记录返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "打卡记录没有数据");
		}
	}

	//添加督办记录
	public void addDubanRecord(final Context context) {
		netClient = new NetClient(context);
		String dubanrecordStr = Utils.getValue(context,Constants.ADD_SUPERVISIONRECORD);
		Log.e(TAG, "提交督办记录上报数据: "+dubanrecordStr);
		if(!TextUtils.isEmpty(dubanrecordStr)||dubanrecordStr.length()>2){
			final List<String> dubanRecordList = JSONArray.parseArray(dubanrecordStr, String.class);
			Log.e(TAG, "carRecordList============: "+dubanRecordList);
			for (final String dubanRecord:dubanRecordList){
				RequestParams params = new RequestParams();
				params.put("supervisionRecordJsonData",dubanRecord);
				netClient.post(Data.getInstance().getIp()+ Constants.ADD_SUPERVISIONRECORD, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "督办记录返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							dubanRecordList.remove(dubanRecord);
							String listStr = JSONArray.toJSONString(dubanRecordList);
							Log.e(TAG, "督办记录有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADD_SUPERVISIONRECORD,listStr);
						}
					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "提交督办记录返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "督办记录没有数据");
		}
	}

	//隐患下达
	public void addThreeFixAndConfirm(final Context context) {
		netClient = new NetClient(context);
		String confirmStr = Utils.getValue(context,Constants.ADD_THREEFIXANDCONFIRM);
		Log.e(TAG, "提交隐患下达数据: "+confirmStr);
		if(!TextUtils.isEmpty(confirmStr)||confirmStr.length()>2){
			final List<String> confirmList = JSONArray.parseArray(confirmStr, String.class);
			Log.e(TAG, "confirmList============: "+confirmList);
			for (final String confirm:confirmList){
				RequestParams params = new RequestParams();
				params.put("threeFixJsonData",confirm);
				netClient.post(Data.getInstance().getIp()+ Constants.ADD_THREEFIXANDCONFIRM, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "隐患下达返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							confirmList.remove(confirm);
							String listStr = JSONArray.toJSONString(confirmList);
							Log.e(TAG, "隐患下达有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADD_THREEFIXANDCONFIRM,listStr);
						}
					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "提交隐患下达返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "隐患下达没有数据");
		}
	}

	//隐患整改
	public void addCompleterectify(final Context context) {
		netClient = new NetClient(context);
		String confirmStr = Utils.getValue(context,Constants.COMPLETERECTIFY);
		Log.e(TAG, "隐患整改数据: "+confirmStr);
		if(!TextUtils.isEmpty(confirmStr)||confirmStr.length()>2){
			final List<String> confirmList = JSONArray.parseArray(confirmStr, String.class);
			Log.e(TAG, "confirmList============: "+confirmList);
			for (final String confirm:confirmList){
                RequestParams params = new RequestParams();
                params.put("ids", confirm);
                netClient.post(Data.getInstance().getIp() + Constants.COMPLETERECTIFY, params, new BaseJsonRes() {

                    @Override
                    public void onMySuccess(String data) {
                        Log.i(TAG, "完成整改返回数据：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            Utils.showShortToast(context, "隐患整改成功！");
                            confirmList.remove(confirm);
                            String listStr = JSONArray.toJSONString(confirmList);
                            Log.e(TAG, "隐患整改有网时: listStr============"+listStr);
                            Utils.putValue(context,Constants.COMPLETERECTIFY,listStr);
                        }
                    }

                    @Override
                    public void onMyFailure(String content) {
                        Log.e(TAG, "完成整改返回错误信息：" + content);
                        Utils.showShortToast(context, content);
                    }
                });
			}
		}else{
			Log.e(TAG, "隐患整改没有数据");
		}
	}

	//隐患重新下达
	public void handleoutOverduelist(final Context context) {
		netClient = new NetClient(context);
		String confirmStr = Utils.getValue(context,Constants.HANDLEOUT_OVERDUELIST);
		Log.e(TAG, "隐患重新下达数据: "+confirmStr);
		if(!TextUtils.isEmpty(confirmStr)||confirmStr.length()>2){
			final List<String> confirmList = JSONArray.parseArray(confirmStr, String.class);
			Log.e(TAG, "confirmList============: "+confirmList);
			for (final String confirm:confirmList){
				RequestParams params = new RequestParams();
				params.put("ids", confirm);
				netClient.post(Data.getInstance().getIp() + Constants.HANDLEOUT_OVERDUELIST, params, new BaseJsonRes() {
					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "隐逾期患重新下达返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							Utils.showShortToast(context, "重新下达成功");
							confirmList.remove(confirm);
							String listStr = JSONArray.toJSONString(confirmList);
							Log.e(TAG, "隐患下达有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.HANDLEOUT_OVERDUELIST,listStr);
						}
					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "隐逾期患重新下达返回错误信息：" + content);
						Utils.showShortToast(context, content);
						return;
					}
				});
			}
		}else{
			Log.e(TAG, "隐患重新下达没有数据");
		}
	}

	//隐患查看记录添加
	public void recordWatch(final Context context) {
		netClient = new NetClient(context);
		String recordWatchStr = Utils.getValue(context,Constants.ADD_RECORDWATCH);
		Log.e(TAG, "隐患查看记录添加数据: "+recordWatchStr);
		if(!TextUtils.isEmpty(recordWatchStr)||recordWatchStr.length()>2){
			final List<String> recordWatchList = JSONArray.parseArray(recordWatchStr, String.class);
			Log.e(TAG, "recordWatchList============: "+recordWatchList);
			for (final String recordWatch:recordWatchList){
				RequestParams params = new RequestParams();
				params.put("recordWatchJsonData",recordWatch);
				netClient.post(Data.getInstance().getIp()+ Constants.ADD_RECORDWATCH, params, new BaseJsonRes() {

					@Override
					public void onMySuccess(String data) {
						Log.i(TAG, "隐患查看记录添加返回数据：" + data);
						if (!TextUtils.isEmpty(data)) {
							recordWatchList.remove(recordWatch);
							String listStr = JSONArray.toJSONString(recordWatchList);
							Log.e(TAG, "隐患查看记录添加有网时: listStr============"+listStr);
							Utils.putValue(context,Constants.ADD_SUPERVISIONRECORD,listStr);
						}
					}

					@Override
					public void onMyFailure(String content) {
						Log.e(TAG, "隐患查看记录添加返回错误信息：" + content);
					}
				});
			}
		}else{
			Log.e(TAG, "隐患查看记录添加没有数据");
		}
	}

}
