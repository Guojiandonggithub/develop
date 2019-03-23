package com.example.administrator.riskprojects.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HomeHiddenDangerAdapter;
import com.example.administrator.riskprojects.OnItemClickListener;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.activity.Data;
import com.example.administrator.riskprojects.activity.HiddenDangerStatisticsEachUnitDetailActivity;
import com.example.administrator.riskprojects.activity.HomePageTotalDetailActivity;
import com.example.administrator.riskprojects.activity.MainActivity;
import com.example.administrator.riskprojects.activity.MainWindowActivity;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.bean.IdentificCount;
import com.example.administrator.riskprojects.bean.UserInfo;
import com.example.administrator.riskprojects.common.NetUtil;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.example.administrator.riskprojects.util.DensityUtil;
import com.example.administrator.riskprojects.view.MyDecoration;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//消息
public class Fragment_Home extends Fragment {
    private static final String TAG = "Fragment_Home";
    private Activity ctx;
    private View layout, layout_head;
    private MainActivity parentActivity;
    private TextView mTvDeleteNum;
    private TextView mTvNianduNum;
    private TextView mTvZhuanxiangNum;
    private TextView mTvDangyueNum;
    private TextView mTvRiskStatisticsNum;
    private TextView mTvWithinTheTimeLimitNum;
    private TextView mTvUnchangeNum;
    private TextView mTvForAcceptanceNum;
    private TextView mTvopenNum_num;
    private TextView mTvcloseNum_num;
    private TextView mTvonNum_num;
    private RecyclerView mRecyclerView;
    protected NetClient netClient;
    private LinearLayoutCompat mLlNianduNum;
    private LinearLayoutCompat mLlZhuanxiangNum;
    private LinearLayoutCompat mLlDangyueNum;
    private LinearLayoutCompat mLlDeleteNum;
    private LinearLayoutCompat mLlWithinTheTimeLimitNum;
    private LinearLayoutCompat mLlUnchangeNum;
    private LinearLayoutCompat mLlForAcceptanceNum;
    private LinearLayoutCompat mLlRiskStatisticsNum;
    private LinearLayoutCompat mLlRiskAnalysisNum;
    private LinearLayoutCompat mLlopenNum_num;
    private LinearLayoutCompat mLlcloseNum_num;
    private LinearLayoutCompat mLlonNum_num;
    private HomeHiddenDangerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        netClient = new NetClient(getActivity());
        if (layout == null) {
            ctx = this.getActivity();
            parentActivity = (MainActivity) getActivity();
            layout = ctx.getLayoutInflater().inflate(R.layout.fragment_home,
                    null);
        } else {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        initView(layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        initViews();
    }

    private void initViews() {
        getHiddenStatisticsNum(UserUtils.getUserID(getActivity()));
        getRiskStatisticsNum();
        getEvaluationCount();
        getHiddenRecord();
    }

    private void initView(View layout) {
        //年度评估数量
        mTvNianduNum = layout.findViewById(R.id.tv_niandu_num);
        mLlNianduNum = layout.findViewById(R.id.ll_niandu_num);
        mLlNianduNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvNianduNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlNianduNum");
                    intent.putExtra("topname","年度评估");
                    startActivity(intent);
                }
            }
        });
        //专项评估数量
        mTvZhuanxiangNum = layout.findViewById(R.id.tv_zhuanxiang_num);
        mLlZhuanxiangNum = layout.findViewById(R.id.ll_zhuanxiang_num);
        mLlZhuanxiangNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvZhuanxiangNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlZhuanxiangNum");
                    intent.putExtra("topname","专项评估");
                    startActivity(intent);
                }
            }
        });
        //当月评估数量
        mTvDangyueNum = layout.findViewById(R.id.tv_dangyue_num);
        mLlDangyueNum = layout.findViewById(R.id.ll_dangyue_num);
        mLlDangyueNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvDangyueNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlDangyueNum");
                    intent.putExtra("topname","当月管控");
                    startActivity(intent);
                }
            }
        });
        //风险统计
        mTvRiskStatisticsNum = layout.findViewById(R.id.tv_risk_statistics);
        mLlRiskStatisticsNum = layout.findViewById(R.id.ll_risk_statistics);
        mLlRiskStatisticsNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvDangyueNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlRiskStatisticsNum");
                    intent.putExtra("topname","风险统计");
                    startActivity(intent);
                }
            }
        });
        //风险分析
        mLlRiskAnalysisNum = layout.findViewById(R.id.ll_risk_analysis);
        mLlRiskAnalysisNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MainWindowActivity.class);
                intent.putExtra("datatype","mLlRiskAnalysisNum");
                intent.putExtra("topname","风险分析");
                startActivity(intent);
            }
        });
        //已消号数量
        mTvDeleteNum = layout.findViewById(R.id.tv_delete_num);
        mLlDeleteNum = layout.findViewById(R.id.ll_delete_num);
        mLlDeleteNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvDeleteNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlDeleteNum");
                    intent.putExtra("topname","已销号");
                    startActivity(intent);
                }
            }
        });
        //逾期数量
        mTvWithinTheTimeLimitNum = layout.findViewById(R.id.tv_within_the_time_limit_num);
        mLlWithinTheTimeLimitNum = layout.findViewById(R.id.ll_within_the_time_limit_num);
        mLlWithinTheTimeLimitNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvWithinTheTimeLimitNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlWithinTheTimeLimitNum");
                    intent.putExtra("topname","逾期的");
                    startActivity(intent);
                }
            }
        });
        //未整改
        mTvUnchangeNum = layout.findViewById(R.id.tv_unchange_num);
        mLlUnchangeNum = layout.findViewById(R.id.ll_unchange_num);
        mLlUnchangeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvUnchangeNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlUnchangeNum");
                    intent.putExtra("topname","整改中");
                    startActivity(intent);
                }
            }
        });
        //待验收
        mTvForAcceptanceNum = layout.findViewById(R.id.tv_for_acceptance_num);
        mLlForAcceptanceNum = layout.findViewById(R.id.ll_for_acceptance_num);
        mLlForAcceptanceNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvForAcceptanceNum.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlForAcceptanceNum");
                    intent.putExtra("topname","待验收");
                    startActivity(intent);
                }
            }
        });
        mTvopenNum_num = layout.findViewById(R.id.tv_openNum_num);
        mLlopenNum_num = layout.findViewById(R.id.ll_openNum_num);
        mLlopenNum_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvopenNum_num.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlopenNum_num");
                    Map<String,String> paramsmap= getRiskParams();
                    String collieryId = paramsmap.get("collieryId");
                    String sname = paramsmap.get("sname");
                    String fname = paramsmap.get("fname");
                    intent.putExtra("topname","管控开始");
                    intent.putExtra("openType","1");
                    intent.putExtra("collieryId",collieryId);
                    intent.putExtra("customParamsOne",sname);
                    intent.putExtra("customParamsTwo",fname);
                    startActivity(intent);
                }
            }
        });
        mTvcloseNum_num = layout.findViewById(R.id.tv_closeNum_num);
        mLlcloseNum_num = layout.findViewById(R.id.ll_closeNum_num);
        mLlcloseNum_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvcloseNum_num.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlcloseNum_num");
                    intent.putExtra("topname","管控结束");
                    Map<String,String> paramsmap= getRiskParams();
                    String collieryId = paramsmap.get("collieryId");
                    String sname = paramsmap.get("sname");
                    String fname = paramsmap.get("fname");
                    intent.putExtra("openType","0");
                    intent.putExtra("collieryId",collieryId);
                    intent.putExtra("customParamsOne",sname);
                    intent.putExtra("customParamsTwo",fname);
                    startActivity(intent);
                }
            }
        });
        mTvonNum_num = layout.findViewById(R.id.tv_onNum_num);
        mLlonNum_num = layout.findViewById(R.id.ll_onNum_num);
        mLlonNum_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTvonNum_num.getText().toString().equals("0")){
                    Intent intent = new Intent(ctx, HomePageTotalDetailActivity.class);
                    intent.putExtra("datatype","mLlonNum_num");
                    intent.putExtra("topname","未管控");
                    Map<String,String> paramsmap= getRiskParams();
                    String collieryId = paramsmap.get("collieryId");
                    String sname = paramsmap.get("sname");
                    String fname = paramsmap.get("fname");
                    intent.putExtra("openType","2");
                    intent.putExtra("collieryId",collieryId);
                    intent.putExtra("customParamsOne",sname);
                    intent.putExtra("customParamsTwo",fname);
                    startActivity(intent);
                }
            }
        });
        mRecyclerView = layout.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
    }


    private void getHiddenStatisticsNum(String employeeId) {
        if (!TextUtils.isEmpty(employeeId)) {
            if (!NetUtil.checkNetWork(getActivity())) {
                String jsondata = Utils.getValue(getActivity(), Constants.HOME_GET_HIDDENUM);
                if("".equals(jsondata)){
                    Utils.showLongToast(getContext(), "网络异常，没有请求到数据");
                }else{
                    resultHiddenStatisticsNum(jsondata);
                }
            }else{
                RequestParams params = new RequestParams();
                params.put("employeeId", employeeId);
                netClient.post(Data.getInstance().getIp()+Constants.HOME_GET_HIDDENUM, params, new BaseJsonRes() {

                    @Override
                    public void onMySuccess(String data) {
                        Log.i(TAG, "主页隐患数据统计返回数据：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            Utils.putValue(getActivity(), Constants.HOME_GET_HIDDENUM, data);
                            resultHiddenStatisticsNum(data);
                        }

                    }

                    @Override
                    public void onMyFailure(String content) {
                        Log.e(TAG, "主页获取隐患数据统计返回错误信息：" + content);
                        Utils.showLongToast(getContext(), content);
                    }
                });
            }
        } else {
            Utils.showLongToast(getActivity(), "请退出重新登录！");
        }
    }

    private void getRiskStatisticsNum() {
        Map<String,String> paramsmap= getRiskParams();
        String collieryId = paramsmap.get("collieryId");
        String sname = paramsmap.get("sname");
        String fname = paramsmap.get("fname");
        if (!TextUtils.isEmpty(collieryId)) {
            if (!NetUtil.checkNetWork(getActivity())) {
                String jsondata = Utils.getValue(getActivity(), Constants.GET_RISK_STATISTICS_NUM);
                if("".equals(jsondata)){
                    Utils.showLongToast(getContext(), "网络异常，没有请求到数据");
                }else{
                    resultHiddenStatisticsNum(jsondata);
                }
            }else{
                Map<String, String> paramsMap = new HashMap<>();
                RequestParams params = new RequestParams();
                paramsMap.put("customParamsOne", sname);
                paramsMap.put("customParamsTwo", fname);
                paramsMap.put("kuangQuId", collieryId);
                String jsonString = JSON.toJSONString(paramsMap);
                params.put("evaluationJson", jsonString);
                netClient.post(Data.getInstance().getIp()+Constants.GET_RISK_STATISTICS_NUM, params, new BaseJsonRes() {

                    @Override
                    public void onMySuccess(String data) {
                        Log.i(TAG, "主页隐患数据统计返回数据：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            Utils.putValue(getActivity(), Constants.GET_RISK_STATISTICS_NUM, data);
                            resultRiskStatisticsNum(data);
                        }

                    }

                    @Override
                    public void onMyFailure(String content) {
                        Log.e(TAG, "主页获取隐患数据统计返回错误信息：" + content);
                        Utils.showLongToast(getContext(), content);
                    }
                });
            }
        } else {
            Utils.showLongToast(getActivity(), "请退出重新登录！");
        }
    }

    private void getEvaluationCount() {
            if (!NetUtil.checkNetWork(getActivity())) {
                String jsondata = Utils.getValue(getActivity(), Constants.GET_EVALUATIONCOUNT);
                if("".equals(jsondata)){
                    Utils.showLongToast(getContext(), "网络异常，没有请求到数据");
                }else{
                    resultEvaluationCountNum(jsondata);
                }
            }else{
                RequestParams params = new RequestParams();
                netClient.post(Data.getInstance().getIp()+Constants.GET_EVALUATIONCOUNT, params, new BaseJsonRes() {

                    @Override
                    public void onMySuccess(String data) {
                        Log.i(TAG, "主页辨识评估统计信息返回数据：" + data);
                        if (!TextUtils.isEmpty(data)) {
                            Utils.putValue(getActivity(), Constants.GET_EVALUATIONCOUNT, data);
                            resultEvaluationCountNum(data);
                        }
                    }

                    @Override
                    public void onMyFailure(String content) {
                        Log.e(TAG, "主页获取辨识评估统计信息返回错误信息：" + content);
                        Utils.showLongToast(getContext(), content);
                    }
                });
            }
    }

    private void getHiddenRecord() {
        if (!NetUtil.checkNetWork(getActivity())) {
            String jsondata = Utils.getValue(getActivity(), Constants.HOME_GET_HIDDENRECORD);
            if("".equals(jsondata)){
                Utils.showLongToast(getContext(), "网络异常，没有请求到数据");
            }else{
                resultHiddenRecord(jsondata);
            }
        }else{
            RequestParams params = new RequestParams();
            params.put("employeeId",UserUtils.getUserID(getActivity()));
            netClient.post(Data.getInstance().getIp()+Constants.HOME_GET_HIDDENRECORD, params, new BaseJsonRes() {

                @Override
                public void onMySuccess(String data) {
                    Log.i(TAG, "主页隐患数据返回数据：" + data);
                    if (!TextUtils.isEmpty(data)) {
                        Utils.putValue(getActivity(), Constants.HOME_GET_HIDDENRECORD, data);
                        resultHiddenRecord(data);
                    }

                }

                @Override
                public void onMyFailure(String content) {
                    Log.e(TAG, "获取隐患数据返回错误信息：" + content);
                    Utils.showLongToast(getContext(), content);
                }
            });
        }
    }

    private void resultHiddenStatisticsNum(String data){
        JSONObject jsonObject = JSON.parseObject(data);
        String notRectify = jsonObject.getString("notRectify");
        String outTimeNumber = jsonObject.getString("outTimeNumber");
        String recheck = jsonObject.getString("recheck");
        String saleNumber = jsonObject.getString("saleNumber");
        mTvUnchangeNum.setText(notRectify);
        mTvWithinTheTimeLimitNum.setText(outTimeNumber);
        mTvForAcceptanceNum.setText(recheck);
        mTvDeleteNum.setText(saleNumber);
    }

    private void resultRiskStatisticsNum(String data){
        String mTvopenNum = "0";
        String mTvcloseNum = "0";
        String mTvonNum = "0";
        List<IdentificCount> tempList = JSONArray.parseArray(data, IdentificCount.class);
        for (int i = 0; i < tempList.size(); i++) {
            String opentype = tempList.get(i).getOpenType();
            if(opentype.equals("0")){
                mTvcloseNum = tempList.get(i).getTotal();
            }else if(opentype.equals("1")){
                mTvopenNum = tempList.get(i).getTotal();
            }else{
                mTvonNum = tempList.get(i).getTotal();
            }
        }
        mTvopenNum_num.setText(mTvopenNum);
        mTvcloseNum_num.setText(mTvcloseNum);
        mTvonNum_num.setText(mTvonNum);
    }

    private void resultEvaluationCountNum(String data){
        JSONObject jsonObject = JSON.parseObject(data);
        String nianDuNum = jsonObject.getString("nianDuNum");
        String zhuanXiangNum = jsonObject.getString("zhuanXiangNum");
        String monthCount = jsonObject.getString("monthCount");
        mTvNianduNum.setText(nianDuNum);
        mTvZhuanxiangNum.setText(zhuanXiangNum);
        mTvDangyueNum.setText(monthCount);
        Integer num = Integer.parseInt(nianDuNum)+Integer.parseInt(zhuanXiangNum);
        mTvRiskStatisticsNum.setText(num.toString());
    }

    private void resultHiddenRecord(String data){
        final List<HomeHiddenRecord> recordList = JSONArray.parseArray(data, HomeHiddenRecord.class);
        mRecyclerView.addItemDecoration(new MyDecoration(ctx, MyDecoration.VERTICAL_LIST, R.color.white, DensityUtil.dip2px(ctx, 0.67f)));
        adapter = new HomeHiddenDangerAdapter(recordList);
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int flag) {
                switch (flag) {
                    case -1:
                        Intent intent = new Intent(getActivity(), HiddenDangerStatisticsEachUnitDetailActivity.class);
                        intent.putExtra("teamGroupCode",recordList.get(position).getTeamGroupId());
                        startActivity(intent);
                        //((MainActivity) ctx).onHomeListItemClicked(recordList.get(position).getTeamGroupCode(),flag);
                        break;
                    case 1:
                        Intent nothandle = new Intent(getActivity(), HiddenDangerStatisticsEachUnitDetailActivity.class);
                        nothandle.putExtra("teamGroupCode",recordList.get(position).getTeamGroupId());
                        nothandle.putExtra("ishandle","1");
                        startActivity(nothandle);
                        //((MainActivity) ctx).onHomeListItemClicked(recordList.get(position).getTeamGroupCode(),flag);
                        break;
                    case 2:
                        Intent handle = new Intent(getActivity(), HiddenDangerStatisticsEachUnitDetailActivity.class);
                        handle.putExtra("teamGroupCode",recordList.get(position).getTeamGroupId());
                        handle.putExtra("ishandle","0");
                        startActivity(handle);
                        //((MainActivity) ctx).onHomeListItemClicked(recordList.get(position).getTeamGroupCode(),flag);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                return false;
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    private Map<String,String> getRiskParams(){
        UserInfo userInfo = UserUtils.getUserModel(getActivity());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String monthStr;
        month = month + 1;
        if (month < 10) {
            monthStr = "0" + month;
        }else{
            monthStr = String.valueOf(month);
        }
        int date = cal.get(Calendar.DATE);
        String sname = year + "-" + monthStr + "-01";
        String fname = year + "-" + monthStr + "-" + date;
        String collieryId = userInfo.getCollieryId();
        Map<String,String> map = new HashMap<>();
        map.put("sname",sname);
        map.put("fname",fname);
        map.put("collieryId",collieryId);
        return map;
    }

}
