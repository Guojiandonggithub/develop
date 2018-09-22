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
        txtTitle.setText("隐患列表");
        String teamGroupCode = getIntent().getStringExtra("teamGroupCode");
        String State = getIntent().getStringExtra("State");
        getHiddenRecord(teamGroupCode,State);
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
        swipeRefreshLayout.setRefreshing(false);
    }

    private void getHiddenRecord(String teamGroupCode,String State) {
        RequestParams params = new RequestParams();
        Map<String,String> paramsMap = new HashMap<String,String>();
        if(!TextUtils.isEmpty(State)){
            paramsMap.put("State",State);
        }
        if(!TextUtils.isEmpty(teamGroupCode)){
            paramsMap.put("teamGroupCode",teamGroupCode);
        }
        Log.i(TAG, "详情参数:"+params);
        paramsMap.put("page",Constants.PAGE);
        paramsMap.put("rows",Constants.ROWS);
        String jsonString = JSON.toJSONString(paramsMap);
        params.put("hiddenDangerRecordJsonData",jsonString);
        Utils.showLongToast(HiddenDangerStatisticsEachUnitDetailActivity.this, "详情参数:"+params);
        netClient.post(Constants.GET_HIDDENDANGERDETAILLIST, params, new BaseJsonRes() {

            @Override
            public void onMySuccess(String data) {
                Log.i(TAG, "统计详情返回数据：" + data);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject returndata  = JSON.parseObject(data);
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
        });
    }

    private void initData(List<HiddenDangerRecord> recordList){
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout.setOnRefreshListener(this);
        HiddenDangerStatisticsEachUnitDetailAdapter  adapter = new HiddenDangerStatisticsEachUnitDetailAdapter(recordList);
        recyclerView.setAdapter(adapter);
    }
}
