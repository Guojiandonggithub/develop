package com.example.administrator.riskprojects.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.riskprojects.Adpter.HiddenDangerStatisticsEachUnitDetailAdapter;
import com.example.administrator.riskprojects.BaseActivity;
import com.example.administrator.riskprojects.R;
import com.example.administrator.riskprojects.bean.HiddenDangerRecord;
import com.example.administrator.riskprojects.bean.HomeHiddenRecord;
import com.example.administrator.riskprojects.net.BaseJsonRes;
import com.example.administrator.riskprojects.net.NetClient;
import com.example.administrator.riskprojects.tools.Constants;
import com.example.administrator.riskprojects.tools.UserUtils;
import com.example.administrator.riskprojects.tools.Utils;
import com.juns.health.net.loopj.android.http.RequestParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//隐患列表
public class HiddenDangerStatisticsEachUnitDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HiddenDangerStatisticsE";
    private TextView txtLeft;
    private ImageView imgLeft;
    private TextView txtTitle;
    private ImageView imgRight;
    private TextView txtRight;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected NetClient netClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_danger_statistics_each_unit_detail);
        netClient = new NetClient(HiddenDangerStatisticsEachUnitDetailActivity.this);
        initView();
        setView();
    }

    private void setView() {
        String teamGroupCode = getIntent().getStringExtra("teamGroupCode");
        String tvStartDate = getIntent().getStringExtra("tvStartDate");
        String tvEndDate = getIntent().getStringExtra("tvEndDate");
        Log.i(TAG, "HiddenDangerStatisticsEachUnitDetailActivity========: "+tvStartDate+"==========="+tvEndDate);
        String State = getIntent().getStringExtra("State");
        String statistics = getIntent().getStringExtra("statistics");
        String getTeamdetaillist = getIntent().getStringExtra("getTeamdetaillist");
        if (TextUtils.isEmpty(statistics)) {
            Log.e(TAG, "详情参数:getHiddenRecord==========" + statistics);
            txtTitle.setText(getResources().getString(R.string.hidden_danger_statistics_of_each_unit));
            getHiddenRecord(Constants.PAGE,teamGroupCode, State,getTeamdetaillist,tvStartDate,tvEndDate);
        } else {
            txtTitle.setText(getResources().getString(R.string.summary_of_hazards));
            String teamGroupName = getIntent().getStringExtra("teamGroupName");
            Log.e(TAG, "详情参数:getNotHandleList==========" + teamGroupName);
            getNotHandleList(Constants.PAGE, teamGroupName,tvStartDate,tvEndDate);
        }
    }

    private void initView() {
        txtLeft = findViewById(R.id.txt_left);
        imgLeft = findViewById(R.id.img_left);
        txtTitle = findViewById(R.id.txt_title);
        imgRight = findViewById(R.id.img_right);
        txtRight = findViewById(R.id.txt_right);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onRefresh() {
        setView();
    }

    //获取未消耗的数据
    private void getNotHandleList(String page, String teamGroupName,String tvStartDate,String tvEndDate) {
        RequestParams params = new RequestParams();
        Map<String, String> paramsMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(teamGroupName)) {
            paramsMap.put("teamGroupName", teamGroupName);
        }
        if(!TextUtils.isEmpty(tvStartDate)){
            params.put("customParamsOne", tvStartDate);//开始时间
        }
        if(!TextUtils.isEmpty(tvEndDate)){
            params.put("customParamsTwo", tvEndDate);//结束时间
        }
        Log.i(TAG, "详情参数:" + params);
        paramsMap.put("page", Constants.PAGE);
        paramsMap.put("rows", "100");
        paramsMap.put("employeeId", UserUtils.getUserID(HiddenDangerStatisticsEachUnitDetailActivity.this));
        netClient.post(Data.getInstance().getIp() + Constants.GET_NOTHANDLELIST, params, new BaseJsonRes() {

            @Override
            public void onStart() {
                super.onStart();
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "获取未消耗的数据返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    //JSONObject returndata  = JSON.parseObject(data);
                    //String rows = returndata.getString("rows");
                    //String page = returndata.getString("page");
                    //String pagesize = returndata.getString("pagesize");
                    List<HiddenDangerRecord> recordList = JSONArray.parseArray(data, HiddenDangerRecord.class);
                    initData(recordList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "获取未消耗的数据返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerStatisticsEachUnitDetailActivity.this, content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }


    //没有页数信息
    private void getHiddenRecord(String page,String teamGroupCode, String State,String getTeamdetaillist,String tvStartDate,String tvEndDate) {
        String ishandle = getIntent().getStringExtra("ishandle");
        RequestParams params = new RequestParams();
        String requesturl=Constants.GET_HIDDENDANGERDETAILLIST;
        Map<String, String> paramsMap = new HashMap<String, String>();
        if (!TextUtils.isEmpty(State)) {
            paramsMap.put("State", State);
        }
        if (!TextUtils.isEmpty(getTeamdetaillist)) {
            requesturl = Constants.GET_TEAMDETAILLIST;
        }
        if (!TextUtils.isEmpty(teamGroupCode)) {
            paramsMap.put("teamGroupId", teamGroupCode);
        }
        if (!TextUtils.isEmpty(ishandle)) {
            paramsMap.put("ishandle", ishandle);
        }
        if(!TextUtils.isEmpty(tvStartDate)){
            params.put("customParamsOne", tvStartDate);//开始时间
        }
        if(!TextUtils.isEmpty(tvEndDate)){
            params.put("customParamsTwo", tvEndDate);//结束时间
        }
        Log.i(TAG, "详情参数:" + params);
        paramsMap.put("page", page);
        paramsMap.put("rows", "100");
        paramsMap.put("employeeId", UserUtils.getUserID(HiddenDangerStatisticsEachUnitDetailActivity.this));
            String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData", jsonString);
        netClient.post(Data.getInstance().getIp() + requesturl, params, new BaseJsonRes() {
                @Override
            public void onStart() {
                super.onStart();
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "统计详情返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata = JSON.parseObject(data);
                    String rows = returndata.getString("rows");
                    String page = returndata.getString("page");
                    String pagesize = returndata.getString("pagesize");
                    List<HiddenDangerRecord> recordList = JSONArray.parseArray(rows, HiddenDangerRecord.class);
                    initData(recordList);
                }

            }

            @Override
            public void onMyFailure(String content) {
                Log.e(TAG, "统计详情返回错误信息：" + content);
                Utils.showLongToast(HiddenDangerStatisticsEachUnitDetailActivity.this, content);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void initData(List<HiddenDangerRecord> recordList) {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(this);
        HiddenDangerStatisticsEachUnitDetailAdapter adapter = new HiddenDangerStatisticsEachUnitDetailAdapter(recordList);
        recyclerView.setAdapter(adapter);
    }
}
